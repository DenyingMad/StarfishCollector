package com.devilpanda.starfishcollector;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class StarfishCollectorBeta extends GameBeta {

    private Turtle turtle;
    private ActorBeta ocean, starfish, winMessage;

    public void initialize() {

        ocean = new ActorBeta();
        ocean.setTexture(new Texture(Gdx.files.internal("water.jpg")));
        mainStage.addActor(ocean);

        starfish = new ActorBeta();
        starfish.setTexture(new Texture(Gdx.files.internal("starfish.png")));
        starfish.setPosition(380, 380);
        mainStage.addActor(starfish);

        turtle = new Turtle();
        turtle.setTexture(new Texture(Gdx.files.internal("turtle-1.png")));
        turtle.setPosition(20, 20);
        mainStage.addActor(turtle);


        winMessage = new ActorBeta();
        winMessage.setTexture(new Texture(Gdx.files.internal("you-win.png")));
        winMessage.setPosition(180, 180);
        winMessage.setVisible(false);
        mainStage.addActor(winMessage);

    }

    public void update(float dt) {

        if (turtle.overlaps(starfish)){
            starfish.remove();
            winMessage.setVisible(true);
        }
    }
}
