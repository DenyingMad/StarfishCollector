package com.devilpanda.starfishcollector.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

public class BaseActor extends Actor {


    // Animation variables
    private Animation<TextureRegion> animation;
    private float elapsedTime;
    private boolean animationPaused;

    // Physics variables
    private Vector2 velocityVec;
    private Vector2 accelerationVec;
    private float acceleration;
    private float deceleration;
    private float maxSpeed;

    // Collision
    private Polygon boundaryPolygon;
    private static Rectangle worldBounds;

    public BaseActor(float x, float y, Stage stage){
        super();

        animation = null;
        elapsedTime = 0;
        animationPaused = false;
        velocityVec = new Vector2(0, 0);
        accelerationVec = new Vector2(0, 0);
        acceleration = 0;
        maxSpeed = 1000;
        deceleration = 0;

        setPosition(x, y);
        stage.addActor(this);
    }

    // Collision
    public void setBoundaryRectangle(){
        float w = getWidth();
        float h = getHeight();
        float[] vertices = {0, 0, w, 0, w, h, 0, h};
        boundaryPolygon = new Polygon(vertices);
    }

    public void setBoundaryPolygon(int numSides){
        float w = getWidth();
        float h = getHeight();
        float[] vertices = new float[2 * numSides];

        for (int i = 0; i < numSides; i++){
            float angle = i * 6.28f / numSides;
            // x
            vertices[2*i] = w / 2 * MathUtils.cos(angle) + w / 2;
            // y
            vertices[2*i+1] = h / 2 * MathUtils.sin(angle) + h / 2;
        }

        boundaryPolygon = new Polygon(vertices);
    }

    public Polygon getBoundaryPolygon(){
        boundaryPolygon.setPosition(getX(), getY());
        boundaryPolygon.setOrigin(getOriginX(), getOriginY());
        boundaryPolygon.setRotation(getRotation());
        boundaryPolygon.setScale(getScaleX(), getScaleY());
        return boundaryPolygon;
    }

    // Overlapping
    public boolean overlaps(BaseActor other){
        Polygon polygon1 = this.getBoundaryPolygon();
        Polygon polygon2 = other.getBoundaryPolygon();

        if (!polygon1.getBoundingRectangle().overlaps(polygon2.getBoundingRectangle())){
            return false;
        }
        return Intersector.overlapConvexPolygons(polygon1, polygon2);
    }


    // Physics
    public void applyPhysics(float delta){
        velocityVec.add(accelerationVec.x * delta, accelerationVec.y * delta);

        float speed = getSpeed();

        if (accelerationVec.len() == 0)
            speed -= deceleration * delta;

        speed = MathUtils.clamp(speed, 0, maxSpeed);

        setSpeed(speed);

        moveBy(velocityVec.x * delta, velocityVec.y * delta);

        accelerationVec.set(0, 0);
    }

    public void accelerateAtAngle(float angle){
        accelerationVec.add(new Vector2(acceleration, 0).setAngle(angle));
    }

    public boolean isMoving(){
        return (getSpeed() > 0);
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setAcceleration(float acceleration){
        this.acceleration = acceleration;
    }

    public void setDeceleration(float deceleration) {
        this.deceleration = deceleration;
    }

    public float getMotionAngle(){
        return velocityVec.angle();
    }

    public float getSpeed(){
        return velocityVec.len();
    }

    public void setSpeed(float speed){
        if (velocityVec.len() == 0){
            velocityVec.set(speed, 0);
        } else{
            velocityVec.setLength(speed);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!animationPaused)
            elapsedTime += delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        Color c = getColor();
        batch.setColor(c);

        if (animation != null && isVisible()){
            batch.draw(animation.getKeyFrame(elapsedTime),
                    getX(), getY(),
                    getOriginX(), getOriginY(),
                    getWidth(), getHeight(),
                    getScaleX(), getScaleY(), getRotation());
        }
    }

    // Animations
    public void setAnimation(Animation<TextureRegion> animation){
        this.animation = animation;
        TextureRegion tr = animation.getKeyFrame(0);
        float w = tr.getRegionWidth();
        float h = tr.getRegionHeight();
        setSize(w, h);
        setOrigin(w / 2, h /2 );

        if (boundaryPolygon == null){
            setBoundaryRectangle();
        }
    }

    public Animation<TextureRegion> loadAnimationFromFiles(String[] files, float frameDuration, boolean loop){

        Array<TextureRegion> textureArray = new Array<>();
        for (String file : files) {
            Texture texture = new Texture(Gdx.files.internal(file));
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            textureArray.add(new TextureRegion(texture));
        }

        Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration, textureArray);

        if (loop){
            anim.setPlayMode(Animation.PlayMode.LOOP);
        } else {
            anim.setPlayMode(Animation.PlayMode.NORMAL);
        }
        if (animation == null)
            setAnimation(anim);
        return anim;
    }

    public Animation<TextureRegion> loadAnimationFromSheet(String file, int rows, int cols, float frameDuration, boolean loop){
        Texture texture = new Texture(Gdx.files.internal(file), true);
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        int frameWidth = texture.getWidth() / cols;
        int frameHeight = texture.getHeight() / rows;

        TextureRegion[][] temp = TextureRegion.split(texture, frameWidth, frameHeight);

        Array<TextureRegion> textureArray = new Array<>();

        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                textureArray.add(temp[r][c]);

        Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration, textureArray);
        if (loop)
            anim.setPlayMode(Animation.PlayMode.LOOP);
        else
            anim.setPlayMode(Animation.PlayMode.NORMAL);

        if (animation == null)
            setAnimation(anim);

        return anim;
    }

    public Animation<TextureRegion> loadTexture(String fileName){
        String[] fileNames = new String[1];
        fileNames[0] = fileName;
        return loadAnimationFromFiles(fileNames, 1, true);
    }

    public void setAnimationPaused(boolean pause){
        animationPaused = pause;
    }

    public boolean isAnimationFinished(){
        return animation.isAnimationFinished(elapsedTime);
    }

    public void centerAtPosition(float x, float y){
        setPosition(x - getWidth() / 2, y - getHeight() / 2);
    }

    public void centerAtActor(BaseActor actor){
        centerAtPosition(actor.getX() + actor.getWidth() / 2, actor.getY() + actor.getHeight() / 2);
    }

    public void setOpacity(float opacity){
        this.getColor().a = opacity;
    }

    public Vector2 preventOverlap(BaseActor actor){
        Polygon polygon1 = this.getBoundaryPolygon();
        Polygon polygon2 = actor.getBoundaryPolygon();

        if (!polygon1.getBoundingRectangle().overlaps(polygon2.getBoundingRectangle())){
            return null;
        }

        Intersector.MinimumTranslationVector mtv = new Intersector.MinimumTranslationVector();
        boolean polygonOverlap = Intersector.overlapConvexPolygons(polygon1, polygon2, mtv);

        if (!polygonOverlap){
            return null;
        }

        this.moveBy(mtv.normal.x * mtv.depth, mtv.normal.y * mtv.depth);
        return mtv.normal;
    }

    public static ArrayList<BaseActor> getList(Stage stage, String className){
        ArrayList<BaseActor> list = new ArrayList<>();
        Class theClass = null;
        try {
            theClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        for (Actor a : stage.getActors()){
            if (theClass.isInstance(a))
                list.add((BaseActor) a);
        }
        return list;
    }

    public static int count(Stage stage, String className){
        return getList(stage, className).size();
    }

    public static void setWorldBounds(float width, float height){
        worldBounds = new Rectangle(0, 0, width, height);
    }

    public static void setWorldBounds(BaseActor baseActor){
        setWorldBounds(baseActor.getWidth(), baseActor.getHeight());
    }

    public void boundToWorld() {
        if (getX() < 0)
            setX(0);
        else if (getX() + getWidth() > worldBounds.width)
            setX(worldBounds.width - getWidth());
        if (getY() < 0)
            setY(0);
        else if (getY() + getHeight() > worldBounds.height)
            setY(worldBounds.height - getHeight());
    }

    public void alignCamera(){
        Camera camera = this.getStage().getCamera();
        Viewport viewport = this.getStage().getViewport();

        camera.position.set(this.getX() + this.getOriginX(), this.getY() + this.getOriginY(), 0);

        camera.position.x = MathUtils.clamp(camera.position.x, camera.viewportWidth / 2, worldBounds.width - camera.viewportWidth / 2);
        camera.position.y = MathUtils.clamp(camera.position.y, camera.viewportHeight / 2, worldBounds.height - camera.viewportHeight / 2);

        camera.update();
    }
}
