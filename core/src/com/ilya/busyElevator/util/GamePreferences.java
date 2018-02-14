package com.ilya.busyElevator.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GamePreferences {

    public static final String TAG = GamePreferences.class.getName();

    public static final GamePreferences instance = new GamePreferences();

    private Preferences prefs;
    public int score;

    public boolean input;

    private GamePreferences(){
        prefs = Gdx.app.getPreferences(Constants.PREFERENCES);

    }

    public void load(){
        score = prefs.getInteger("score",0);
        input = prefs.getBoolean("input",true);
    }

    public void save(int score){
        if (score>this.score) {
            prefs.putInteger("score", score);
        }
        prefs.putBoolean("input",input);
        prefs.flush();
    }

}
