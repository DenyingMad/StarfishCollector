package com.devilpanda.starfishcollector;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public abstract class BaseGame extends Game {
    private static BaseGame game;

    public BaseGame(){
        game = this;
    }

    public static void setActiveScreen(BaseScreen s){
        game.setScreen(s);
    }
}
