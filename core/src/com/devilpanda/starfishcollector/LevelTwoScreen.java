package com.devilpanda.starfishcollector;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.devilpanda.starfishcollector.actors.BaseActor;
import com.devilpanda.starfishcollector.actors.Rock;
import com.devilpanda.starfishcollector.actors.Starfish;
import com.devilpanda.starfishcollector.actors.Turtle;
import com.devilpanda.starfishcollector.actors.Whirpool;

public class LevelTwoScreen extends BaseScreen {

    private Turtle turtle;
    private BaseActor ocean, winMessage, loseMessage;
    private boolean win;
    private boolean isMessageShowed;

    @Override
    public void initialize() {

        ocean = new BaseActor(0, 0, mainStage);
        ocean.loadTexture("water-border.jpg");
        ocean.setSize(1400, 1100);
        BaseActor.setWorldBounds(ocean);

        turtle = new Turtle(30, 30, mainStage);

        new Rock(200, 200, mainStage);
        new Rock(400, 200, mainStage);
        new Rock(800, 200, mainStage);
        new Rock(1000, 200, mainStage);
        new Rock(200, 500, mainStage);
        new Rock(400, 550, mainStage);
        new Rock(750, 500, mainStage);
        new Rock(200, 600, mainStage);

        new Starfish(300, 300, mainStage);
        new Starfish(1000, 700, mainStage);
        new Starfish(500, 100, mainStage);
        new Starfish(200, 600, mainStage);
        new Starfish(700, 900, mainStage);

        win = false;
        isMessageShowed = false;
    }

    @Override
    public void update(float delta) {
        for (BaseActor rockActor : BaseActor.getList(mainStage, "com.devilpanda.starfishcollector.actors.Rock")){
            turtle.preventOverlap(rockActor);
        }
        for (BaseActor starfishActor : BaseActor.getList(mainStage, "com.devilpanda.starfishcollector.actors.Starfish")){
            Starfish starfish = (Starfish) starfishActor;
            if (starfish.overlaps(turtle) && !starfish.isCollected()){
                starfish.collect();
                Whirpool whirpool = new Whirpool(0,0,mainStage);
                whirpool.centerAtActor(starfish);
                whirpool.setOpacity(0.25f);
            }
        }
        if (BaseActor.count(mainStage, "com.devilpanda.starfishcollector.actors.Starfish") == 0 && !win){
            win = true;
            winMessage = new BaseActor(0, 0, uiStage);
            winMessage.loadTexture("you-win.png");
            showMessage(winMessage);
        }
    }

    private void showMessage(BaseActor message){
        if (!isMessageShowed){
            message.setVisible(true);
            message.centerAtPosition(400, 300);
            message.setOpacity(0);
            message.addAction(Actions.fadeIn(1));
            isMessageShowed = true;
            message.addAction(Actions.after(Actions.delay(3)));
            Action goHome = new Action() {
                @Override
                public boolean act(float delta) {
                    StarfishGame.setActiveScreen(new MenuScreen());
                    return true;
                }
            };
            message.addAction(Actions.after(goHome));
        }
    }
}
