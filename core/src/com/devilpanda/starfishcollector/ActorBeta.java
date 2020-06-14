package com.devilpanda.starfishcollector;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorBeta extends Actor {
    private TextureRegion textureRegion;
    private Rectangle rectangle;

    public ActorBeta() {
        super();
        this.textureRegion = new TextureRegion();
        this.rectangle = new Rectangle();
    }

    public void setTexture(Texture texture){
        textureRegion.setRegion(texture);
        setSize(texture.getWidth(), texture.getHeight());
        rectangle.setSize(texture.getWidth(), texture.getHeight());
    }

    public Rectangle getRectangle(){
        rectangle.setPosition(getX(), getY());
        return rectangle;
    }

    public boolean overlaps(ActorBeta actor){
        return this.getRectangle().overlaps(actor.getRectangle());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        Color c = getColor();
        batch.setColor(c.r, c.g, c.b, c.a);
        if (isVisible()){
            batch.draw(textureRegion,
                    getX(), getY(),
                    getOriginX(), getOriginY(),
                    getWidth(), getHeight(),
                    getScaleX(), getScaleY(), getRotation());
        }
    }
}
