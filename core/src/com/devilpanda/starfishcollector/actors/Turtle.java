package com.devilpanda.starfishcollector.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Turtle extends BaseActor {
    private boolean isEaten;

    public Turtle(float x, float y, Stage stage) {
        super(x, y, stage);

        String[] fileNames = {"turtle-1.png", "turtle-2.png", "turtle-3.png",
                "turtle-4.png", "turtle-5.png", "turtle-6.png"};

        loadAnimationFromFiles(fileNames, 0.1f, true);
        setBoundaryPolygon(8);

        setAcceleration(250);
        setMaxSpeed(100);
        setDeceleration(100);

        isEaten = false;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!isEaten) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
                accelerateAtAngle(180);
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
                accelerateAtAngle(0);
            if (Gdx.input.isKeyPressed(Input.Keys.UP))
                accelerateAtAngle(90);
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
                accelerateAtAngle(270);

            applyPhysics(delta);

            setAnimationPaused(!isMoving());

            if (getSpeed() > 0) {
                setRotation(getMotionAngle());
            }
            boundToWorld();
            alignCamera();
        }
    }

    public void eat(){
        isEaten = true;
        addAction(Actions.fadeOut(1));
        addAction(Actions.after(Actions.removeActor()));
    }

    public boolean isEaten(){
        return isEaten;
    }
}
