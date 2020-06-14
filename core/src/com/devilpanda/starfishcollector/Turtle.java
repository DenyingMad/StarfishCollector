package com.devilpanda.starfishcollector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Turtle extends ActorBeta {

    public Turtle() {
        super();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            this.moveBy(-1, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            this.moveBy(1, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            this.moveBy(0, 1);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            this.moveBy(0, -1);
    }
}
