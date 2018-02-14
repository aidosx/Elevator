package com.ilya.busyElevator.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.ilya.busyElevator.game.Objects.AbstractButton;
import com.ilya.busyElevator.game.Objects.BigButton;
import com.ilya.busyElevator.game.Objects.ButtonObj;
import com.ilya.busyElevator.game.Objects.Elevator;
import com.ilya.busyElevator.game.Objects.Floor;
import com.ilya.busyElevator.game.Objects.MenuButton;
import com.ilya.busyElevator.game.Objects.People;
import com.ilya.busyElevator.game.miscObjects.AbstractMiscObjects;
import com.ilya.busyElevator.game.miscObjects.Background;
import com.ilya.busyElevator.util.Constants;
import com.ilya.busyElevator.util.GamePreferences;

public class WorldController extends InputAdapter {

    private AbstractButton button1;
    private AbstractButton button2;
    private AbstractButton bigButton;
    private AbstractButton menuButton;
    public Array<AbstractButton> listOfButtons;
    public Array<AbstractMiscObjects> listOfMisc;
    public static Array<People> listOfPeople;
    public static Array<Floor> listOfFloors;
    private Vector3 coords;
    public static final Rectangle r1 = new Rectangle();
    public static final Rectangle r2 = new Rectangle();
    public static final Rectangle r3 = new Rectangle();
    public static final Rectangle touchUp = new Rectangle(0,0,320,480);
    public static final Rectangle touchDown = new Rectangle(0,480,320,960);
    public static final Rectangle bigTouch = new Rectangle(320,0,640,960);
    private AbstractMiscObjects bg;
    protected Elevator elevator;
    private boolean desktopInput = false;
    private float timer;
    public boolean gameOver;
    public People zoomInToThisPerson;
    public int score;
    public float tempScore = 0;

    public WorldController() {
        Gdx.input.setInputProcessor(this);
        coords = new Vector3();
        Constants.TIMER = 1.2f;
        Constants.tempScore = 10;
        init();
    }

    public void init() {
        listOfPeople = new Array<People>();
        listOfFloors = new Array<Floor>();
        createScene();
        createGui();
    }

    private void toggleDesktopInput(){
        if (Gdx.input.isKeyPressed(Input.Keys.T)){
            desktopInput = true;
        }

    }


    public void inputHandle(){
        if (Gdx.input.isKeyPressed(Input.Keys.UP)){
            listOfButtons.get(0).setPressed(true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            listOfButtons.get(1).setPressed(true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            listOfButtons.get(2).setPressed(true);
        } else {
            for (com.ilya.busyElevator.game.Objects.AbstractButton b : listOfButtons){
                b.setPressed(false);
            }
            elevator.jobIsDone = false;
        }

    }


    private void initRectForGui(){
        r1.x = button1.getPos().x;
        r1.y = button1.getPos().y;
        r1.width = button1.width;
        r1.height = button1.height;
        r2.x = button2.getPos().x;
        r2.y = button2.getPos().y;
        r2.width = button2.width;
        r2.height = button2.height;
        r3.x = bigButton.getPos().x;
        r3.y = bigButton.getPos().y;
        r3.width = bigButton.getHitbox().width;
        r3.height = bigButton.getHitbox().height;
    }

    public void createGui(){
        if (GamePreferences.instance.input) {
            button1 = new ButtonObj(30, 700, true);
            button2 = new ButtonObj(30, 835, false);
            bigButton = new BigButton(450, 750, false);
            button1.setHitbox(new Rectangle(button1.getPos().x, button1.getPos().y, button1.width, button1.height));
            button2.setHitbox(new Rectangle(button2.getPos().x, button2.getPos().y, button2.width, button2.height));
            bigButton.setHitbox(new Rectangle(450, 820 / 2 + 710 / 2 - 20, 150, 150));
        } else
        {
            button1 = new ButtonObj(touchUp.x, touchUp.y, true);
            button2 = new ButtonObj(touchDown.x, touchDown.y, false);
            bigButton = new BigButton(bigTouch.x, bigTouch.y, false);
            button1.setHitbox(new Rectangle(button1.getPos().x, button1.getPos().y, touchUp.width, touchUp.height));
            button2.setHitbox(new Rectangle(button2.getPos().x, button2.getPos().y, touchDown.width, touchDown.height));
            bigButton.setHitbox(new Rectangle(bigButton.getPos().x,bigButton.getPos().y,bigTouch.width,bigTouch.height));
        }
        menuButton = new MenuButton(500,980);
        listOfButtons = new Array<AbstractButton>();
        listOfButtons.add(button1);
        listOfButtons.add(button2);
        listOfButtons.add(bigButton);
        listOfButtons.add(menuButton);
        for (AbstractButton b: listOfButtons){
            b.setElevator(elevator);
        }
        initRectForGui();

    }

    private void createScene(){
        listOfMisc = new Array<AbstractMiscObjects>();
        bg = new Background();
        listOfMisc.add(bg);
        for (int i = 1;i<6;i++){
            Floor floor = new Floor();
            floor.genFloor(i);
            listOfFloors.add(floor);
        }
        for (Floor f : listOfFloors)
            listOfPeople.addAll(f.getCapacity());
        elevator = new Elevator(this);
    }

    @Override
    public boolean touchDown (int screenX, int screenY, int pointer, int button) {
            int x = Gdx.input.getX(pointer);
            int y = Gdx.input.getY(pointer);
            coords.set(x, y, 0);
            WorldRenderer.cameraGUI.unproject(coords);
            for (AbstractButton b : listOfButtons) {
                b.touch(coords);
            }
        return false;
    }

    @Override
    public boolean touchUp (int screenX, int screenY, int pointer, int button) {
//            coords.set(screenX, screenY, 0);
//            WorldRenderer.cameraGUI.unproject(coords);
//            for (AbstractButton b : listOfButtons) {
//                if (screenX<b.getHitbox().x||screenX>b.getHitbox().x+b.getHitbox().width){
//                    if (screenY<b.getHitbox().y||screenY>b.getHitbox().y+b.getHitbox().y+b.getHitbox().height){
//                        b.setPressed(false);
//                        elevator.jobIsDone = false;
//                    }
//                }
//            }
        for (AbstractButton b : listOfButtons){
            b.setPressed(false);
        }
        elevator.jobIsDone = false;
        return false;
    }


    public void update(float delta) {
        if (!gameOver) {
            increaseDifficulty(delta);
            toggleDesktopInput();
            if (desktopInput)
                inputHandle();
            for (com.ilya.busyElevator.game.Objects.AbstractButton b : listOfButtons) {
                b.update();
            }
            elevator.update();
            for (Floor f : listOfFloors) {
                f.update(delta);
            }
            updatePeopleBehavior();
        }
        updateAdButton();
    }

    public void updatePeopleBehavior() {
        for (People p : listOfPeople) {
            p.update();
            if (p.getState() == People.STATE.GAME_OVER) {
                gameOver();
                zoomInToThisPerson = p;
            }
        }
    }

    public void updateAdButton(){
        listOfButtons.get(listOfButtons.size-1).update();
    }


    public void gameOver()
    {
        gameOver = true;
        GamePreferences.instance.save(score);
        Constants.showAd = true;

    }

    private void increaseDifficulty(float delta){
        timer -= delta;
        if (tempScore>=Constants.tempScore) {
            if (Constants.TIMER > 0.6f) {
                if (MathUtils.ceil(Constants.TIMER*10) == 9||MathUtils.ceil(Constants.TIMER*10)==7||MathUtils.ceil(Constants.TIMER*10)==6)
                    Constants.tempScore+=15;

                Constants.TIMER -= 0.1f;

            } else Constants.TIMER=0.6f;
            tempScore = 0;
        }
        if (timer<=0){
            genNewPeople();
            timer = Constants.TIMER;
        }
    }

    private void genNewPeople(){
        int check = 0;
        for (Floor f : listOfFloors) {
            if (f.isFull()) check++;
        }
        if (check == listOfFloors.size) return;
        check = MathUtils.random(0,listOfFloors.size-1);
        if (!listOfFloors.get(check).isFull()){
            listOfFloors.get(check).addHuman();
        } else genNewPeople();

    }


    public void searchForPeople(People obj){
        for (int i = 0;i<listOfPeople.size;i++){
            if (obj == listOfPeople.get(i)) {
                listOfPeople.removeIndex(i);
            }
        }
    }


}


