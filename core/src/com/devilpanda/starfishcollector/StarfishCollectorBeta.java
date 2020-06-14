package com.devilpanda.starfishcollector;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class StarfishCollectorBeta extends ApplicationAdapter {

    private Turtle turtle;
    private ActorBeta ocean, starfish, winMessage;
    private Stage mainStage;
    private boolean win;

    @Override
    public void create() {
        super.create();

        mainStage = new Stage();

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

        win = false;
    }

    @Override
    public void render() {
        super.render();
        // User input
        mainStage.act(1/60f);

        if (turtle.overlaps(starfish)){
            starfish.remove();
            winMessage.setVisible(true);
        }


        // render
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mainStage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        mainStage.dispose();
    }
}
