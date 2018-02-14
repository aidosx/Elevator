package com.ilya.busyElevator;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.ilya.busyElevator.ad.AdInterface;
import com.ilya.busyElevator.screens.GameScreen;


public class ElevatorMain extends Game {

    private AdInterface adInterface;

    public ElevatorMain(AdInterface adInterface){
        this.adInterface = adInterface;
    //    adInterface.showOrLoadInterstital(true);
    }

	@Override
	public void create () {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        setScreen(new GameScreen(true,this,adInterface));
	}


}
