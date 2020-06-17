package com.devilpanda.starfishcollector.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Rock extends BaseActor {
    public Rock(float x, float y, Stage stage) {
        super(x, y, stage);
        loadTexture("rock.png");
        setBoundaryPolygon(8);
    }
}
