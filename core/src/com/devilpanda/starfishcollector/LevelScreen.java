package com.devilpanda.starfishcollector;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.devilpanda.starfishcollector.actors.BaseActor;
import com.devilpanda.starfishcollector.actors.Rock;
import com.devilpanda.starfishcollector.actors.Shark;
import com.devilpanda.starfishcollector.actors.Starfish;
import com.devilpanda.starfishcollector.actors.Turtle;
import com.devilpanda.starfishcollector.actors.Whirpool;

public class LevelScreen extends BaseScreen {

    private Turtle turtle;
    private Shark shark;
    private BaseActor ocean, winMessage, loseMessage;
    private boolean isMessageShowed;
    private boolean win;

    public void initialize() {
        ocean = new BaseActor(0, 0, mainStage);
        ocean.loadTexture("water-border.jpg");
        ocean.setSize(1200, 900);
        BaseActor.setWorldBounds(ocean);

        new Starfish(400, 400, mainStage);
        new Starfish(500, 100, mainStage);
        new Starfish(100, 450, mainStage);
        new Starfish(200, 250, mainStage);

        shark = new Shark(200, 300, mainStage);

        new Rock(200, 150, mainStage);
        new Rock(100, 300, mainStage);
        new Rock(300, 350, mainStage);
        new Rock(450, 200, mainStage);

        turtle = new Turtle(20, 20, mainStage);

        winMessage = new BaseActor(0, 0, uiStage);
        winMessage.loadTexture("you-win.png");
        winMessage.setVisible(false);

        loseMessage = new BaseActor(0, 0, uiStage);
        loseMessage.loadTexture("game-over.png");
        loseMessage.setVisible(false);

        isMessageShowed = false;
        win = false;
    }

    public void update(float dt) {
        for (BaseActor rockActor : BaseActor.getList(mainStage, "com.devilpanda.starfishcollector.actors.Rock")){
            turtle.preventOverlap(rockActor);
        }
        for (BaseActor starfishActor : BaseActor.getList(mainStage, "com.devilpanda.starfishcollector.actors.Starfish")){
            Starfish starfish = (Starfish) starfishActor;
            if (turtle.overlaps(starfish) && !starfish.isCollected()){
                starfish.collect();

                Whirpool whirl = new Whirpool(0, 0, mainStage);
                whirl.centerAtActor(starfish);
                whirl.setOpacity(0.25f);
            }
        }

        if (BaseActor.count(mainStage, "com.devilpanda.starfishcollector.actors.Starfish") == 0 && !win){
            win = true;
            showMessage(winMessage);
        }

        if(turtle.overlaps(shark) && !turtle.isEaten()){
            turtle.eat();
            showMessage(loseMessage);
        }
    }

    public void showMessage(BaseActor message){
        if (!isMessageShowed){
            message.setVisible(true);
            message.centerAtPosition(400, 300);
            message.setOpacity(0);
            message.addAction(Actions.delay(1));
            message.addAction(Actions.after(Actions.fadeIn(1)));
            isMessageShowed = true;
            message.addAction(Actions.after(Actions.delay(3)));
            Action action = new Action() {
                @Override
                public boolean act(float delta) {
                    StarfishGame.setActiveScreen(new LevelTwoScreen());
                    return true;
                }
            };
            message.addAction(Actions.after(action));
        }
    }
}
