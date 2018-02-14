package com.ilya.busyElevator.game.Objects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public abstract class AbstractButton {

    protected boolean pressed;

    public Sound sound;

    private boolean playSound = true;

    //Служит для пропуска всего 1 команды
    public boolean command;

    protected TextureRegion buttonUp;
    protected TextureRegion buttonDown;

    protected TextureRegion currentReg;

    protected Vector2 pos;

    protected Rectangle hitbox;

    public float width;
    public float height;

    abstract void init(float x,float y,boolean flip);

    protected void onClicked(){
        currentReg = buttonDown;
        if (playSound) {
            sound.play();
            playSound=false;
        }

    }

    public void update(){
        if (pressed){
            onClicked();
        }
        else release();
    }

    protected void release() {
        currentReg = buttonUp;
        playSound = true;
    }

    public void setHitbox(Rectangle hitbox){
        this.hitbox = hitbox;
    }

    public Rectangle getHitbox(){
        return hitbox;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public abstract void render(SpriteBatch batch);

    public abstract void setElevator(Elevator elevator);

    public void setPos(float x, float y){}

    public Vector2 getPos(){
        return pos;
    }

    public void touch(Vector3 coords){
        if (coords.x>=this.getHitbox().x){
            if (coords.x<=this.getHitbox().x+this.getHitbox().width){
                if (coords.y>=this.getHitbox().y){
                    if (coords.y<=this.getHitbox().y+this.getHitbox().height)
                        this.setPressed(true);

                }
            }
        }
    }
    public boolean getPressed(){
        return pressed;
    }

}
