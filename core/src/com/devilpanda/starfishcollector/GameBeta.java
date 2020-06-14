package com.devilpanda.starfishcollector;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class GameBeta extends ApplicationAdapter {

    protected Stage mainStage;
    @Override
    public void create() {
        mainStage = new Stage();
        initialize();
    }

    public abstract void initialize();

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        mainStage.act(dt);

        update(dt);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mainStage.draw();
    }

    public abstract void update(float dt);
}
