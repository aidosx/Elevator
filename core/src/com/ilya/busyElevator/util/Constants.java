package com.ilya.busyElevator.util;

public class Constants {

    public static final float VIEWPORT_WIDTH = 640;

    public static final float VIEWPORT_HEIGHT =960;

    //Gui width
    public static final float VIEWPORT_GUI_WIDTH = 480;

    //Gui height
    public static final float VIEWPORT_GUI_HEIGHT = 800;

    //Texture Atlas
    public static final String TEXTURE_ATLAS = "images/elevator.pack.atlas";

    public static final String TEXTURE_ATLAS_FOR_FACE = "images/elevatorFaces.pack.atlas";

    //Show Debug Hitboxes For Gui
    public static final boolean SHOW_HITBOXES = false;

    public static final float CAMERA_MOVE_SPEED = 1f;

    public static float TIMER;

    public static final String PREFERENCES = "Elevator.prefs";

    public static final String VERSION = "BETA";

    public static GAME_STATE GAME_STATE;

    public static boolean NEW_GAME;

    public static int tempScore;

    public static boolean inputButtons = true;

    public static boolean adReady;

    public static boolean showAd;

    public enum GAME_STATE{
        NEW_GAME,IN_MENU,ELEVATOR_CHOOSING,GAME_OVER,IN_GAME;
    }



}
