package com.ilya.busyElevator.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.ilya.busyElevator.util.Assets;
import com.ilya.busyElevator.util.Constants;

public class People {

    public Vector2 pos;
    protected TextureRegion body;
    private TextureRegion face;
    private TextureRegion emotion;
    private Vector2 dimension;
    private int destination;
    private int floorImIn;
    private TextureRegion bubbleRegion;
    private float animY;
    private float deltaTime;
    float x,y;
    private float stateTime;
    private Vector2 bubblePos;
    private Vector2 bubbleDimension;

    private Vector2 fontPos;
    private BitmapFont font;

    protected STATE state;
    protected STATE previousState;

    private float waitTime;

    private int keyFrame = 0;

    private TextureRegion[] shoesReg;
    private Animation shoesAnim;

    public enum STATE{
        POKER_FACE,CLOSE_EYES,WHISTLE,WEIRD,ANGRY,IN_ELEVATOR,WALKING,GAME_OVER
    }



    public People(float x, float y){
        init(x, y);
    }

    private void init(float x, float y) {
        this.x = x;
        this.y = y;
        emotion = new TextureRegion();
        createBody();
        dimension = new Vector2(42,24);
        pos = new Vector2(x,y);
        bubblePos = new Vector2(pos.x,pos.y+45);
        bubbleDimension = new Vector2(dimension.x,dimension.y+10);
        fontPos = new Vector2(bubblePos.x+bubbleDimension.x/2-5,bubblePos.y+bubbleDimension.y/2+7);
        font = Assets.instance.font;
        createBubble();
        animY = 0;
        stateTime = 0;
        state = STATE.POKER_FACE;
        shoesReg = new TextureRegion[3];
        shoesReg[0] = Assets.instance.guy.shoes;
        shoesReg[1] = Assets.instance.guy.shoes1;
        shoesReg[2] = Assets.instance.guy.shoes2;

        shoesAnim = new Animation(25,shoesReg);
        shoesAnim.setPlayMode(Animation.PlayMode.LOOP);

    }

    public void renderAnimation(SpriteBatch batch){
        batch.draw(shoesAnim.getKeyFrame(deltaTime),pos.x,pos.y,0.2f,0.2f);
    }

    public void render(SpriteBatch batch) {
        batch.draw(body, pos.x, pos.y, dimension.x, dimension.y);
        batch.draw(face, pos.x, pos.y + dimension.y, dimension.x, dimension.y);
        batch.draw(emotion,pos.x, pos.y + dimension.y, dimension.x, dimension.y);
        if (state==STATE.WALKING){
            renderAnimation(batch);
        }
    }

    public void renderBubble(SpriteBatch batch){
        batch.draw(bubbleRegion, bubblePos.x, bubblePos.y, bubbleDimension.x, bubbleDimension.y);
        font.draw(batch,""+destination,fontPos.x,fontPos.y+5);
    }

    private void createBody(){
        body = new TextureRegion(Assets.instance.costumes.random());
        face = new TextureRegion(Assets.instance.faces.random());
        emotion = Assets.instance.emotion.emotions_pokerface;
    }

    private void createBubble() {
        bubbleRegion = new TextureRegion(com.ilya.busyElevator.util.Assets.instance.misc.bubble);
    }

    public void setFloorImIn(int floorImIn){
        this.floorImIn = floorImIn;
    }

    protected void genDestination(){
        destination = MathUtils.random(1,5);
        if (destination == floorImIn) genDestination();
    }

    private void bubbleAnim(){
        deltaTime++;
        if (deltaTime>=60) animY = 5f;
        if (deltaTime>=150) animY = -5f;
        if (deltaTime>=300) deltaTime = 0;
    }

    private void whistle() {
        stateTime++;
        emotion = Assets.instance.emotion.emotions_whistle1;

        if (stateTime > 50f) emotion = com.ilya.busyElevator.util.Assets.instance.emotion.emotions_whistle2;

        if (stateTime > 100f) {
            emotion = com.ilya.busyElevator.util.Assets.instance.emotion.emotions_pokerface;
            state = STATE.POKER_FACE;
            stateTime = 0;
        }
    }

    private void closeEyes(){
       emotion = com.ilya.busyElevator.util.Assets.instance.emotion.emotions_closedeyes;
        stateTime++;
        if (stateTime>10f) {
            emotion = com.ilya.busyElevator.util.Assets.instance.emotion.emotions_pokerface;
            stateTime = 0;
            state = STATE.POKER_FACE;
            }
    }

    private void goesWeird() {
        emotion = com.ilya.busyElevator.util.Assets.instance.emotion.emotions_weird;
    }

    private void goesAngry() {
        emotion = com.ilya.busyElevator.util.Assets.instance.emotion.emotions_angry;
    }

    private void stateAnim(){
        switch (state){
            case POKER_FACE:{
                break;
            }
            case CLOSE_EYES: {
                closeEyes();
                break;
            }
            case WHISTLE: {
                whistle();
                break;
            }
            case WEIRD: {
                goesWeird();
                break;
            }
            case ANGRY: {
                goesAngry();
                break;
            }
            case IN_ELEVATOR: {
                emotion = com.ilya.busyElevator.util.Assets.instance.emotion.emotions_pokerface;
                waitTime=0;
                waitTimeStateDecide();
                break;
            }
        }
    }

    private void stateDecide(){
        switch (MathUtils.random(1,3)) {
            case 1: {
                emotion = com.ilya.busyElevator.util.Assets.instance.emotion.emotions_pokerface;
                state = STATE.POKER_FACE;
                break;
            }
            case 2:{
                if (MathUtils.random(0, 100) == 5) {
                    state = STATE.CLOSE_EYES;
                }
                break;
            }
            case 3: {
                if (MathUtils.random(0, 100) == 5) {
                    state = STATE.WHISTLE;
                }
                break;
            }
        }
    }

    public void update(){
        animY = 0;
        bubbleAnim();
        stateAnim();
        bubblePos.set(pos.x,pos.y+50 + animY);
        fontPos.set(bubblePos.x+bubbleDimension.x/2-5,bubblePos.y+bubbleDimension.y/2+7);
        waitTimeStateDecide();
    }

    public void waitTimeStateDecide() {
        if (state==STATE.POKER_FACE)
            stateDecide();
        if (Constants.GAME_STATE== Constants.GAME_STATE.IN_GAME) {
            waitTime += Gdx.graphics.getDeltaTime();
            if (waitTime >= 8f) state = STATE.WEIRD;
            if (waitTime >= 13f) state = STATE.ANGRY;
            if (waitTime >= 16f) {
                state = STATE.GAME_OVER;
            }
        }
    }
    public int getDestination(){
        return destination;
    }

    public STATE getState() {
        return state;
    }


    public Vector2 getDimension(){
        return dimension;
    }

    public void dispose(){
        font.dispose();
    }

}
