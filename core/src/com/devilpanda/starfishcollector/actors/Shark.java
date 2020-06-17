package com.devilpanda.starfishcollector.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Shark extends BaseActor {
    public Shark(float x, float y, Stage stage) {
        super(x, y, stage);

        loadTexture("sharky.png");
        setBoundaryPolygon(8);
    }
}
