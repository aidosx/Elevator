package com.ilya.busyElevator.game.Objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ilya.busyElevator.game.WorldController;
import com.ilya.busyElevator.util.Assets;
import com.ilya.busyElevator.util.Constants;
import com.ilya.busyElevator.util.GamePreferences;

public class ToggleButton  extends AbstractButton {
    private WorldController worldController;

    public ToggleButton(float x, float y, WorldController worldController){
        this.worldController = worldController;
        init(x,y,true);
    }

    void init(float x, float y, boolean flip) {
            pressed = false;
            width = 128;
            height = 128;
            buttonUp = new TextureRegion(Assets.instance.button.slideButtonLeft);
            buttonDown = new TextureRegion(Assets.instance.button.slideButtonRight);
            buttonUp.flip(false,true);
            buttonDown.flip(false,true);
            currentReg = new TextureRegion();
            if (GamePreferences.instance.input) {
                currentReg = buttonUp;
            }
            else currentReg=buttonDown;

            pos = new Vector2(x,y);

            hitbox = new Rectangle();

            hitbox.x = pos.x;
            hitbox.y = pos.y;
            hitbox.width = width;
            hitbox.height = height;

            command = false;

        }

        @Override
        protected void onClicked(){
            if (currentReg==buttonUp) {
                currentReg = buttonDown;
                GamePreferences.instance.input = false;
                Constants.inputButtons = false;

        }
        else{
            currentReg=buttonUp;
            GamePreferences.instance.input = true;
                Constants.inputButtons = true;
        }
            worldController.createGui();
        System.out.println(GamePreferences.instance.input);
    }


    @Override
    public void update(){
        super.update();
    }

    protected void release() {
        super.release();
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

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(currentReg, pos.x, pos.y,width, height);
    }

    @Override
    public void setElevator(Elevator elevator) {

    }

    public TextureRegion getReg(){
        return currentReg;
    }

    public Vector2 getPos(){
        return pos;
    }

    public void setPos(float x,float y){
        this.pos.x = x;
        this.pos.y = y;
        hitbox.x = pos.x;
        hitbox.y = pos.y;
    }

    public boolean getPressed(){
        return pressed;
    }
}
