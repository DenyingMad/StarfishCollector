package com.devilpanda.starfishcollector.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Whirpool extends BaseActor {

    public Whirpool(float x, float y, Stage stage) {
        super(x, y, stage);
        loadAnimationFromSheet("whirlpool.png", 2, 5, 0.1f, false);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (isAnimationFinished())
            remove();
    }
}
