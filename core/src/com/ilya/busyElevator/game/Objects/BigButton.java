package com.ilya.busyElevator.game.Objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ilya.busyElevator.util.Assets;

public class BigButton extends AbstractButton {

    private Elevator elevator;

    public BigButton(float x, float y, boolean flip){
        init(x,y,flip);
    }

    @Override
    void init(float x, float y, boolean flip) {
        sound = Assets.instance.sound_pop;
        pressed = false;
        width = 150f;
        height = 150f;

        buttonUp = new TextureRegion(com.ilya.busyElevator.util.Assets.instance.bigRedButton.buttonUp);
        buttonDown = new TextureRegion(com.ilya.busyElevator.util.Assets.instance.bigRedButton.buttonDown);
        currentReg = new TextureRegion(buttonUp);

        pos = new Vector2(x,y);

        hitbox = new Rectangle();

        command = false;

    }

    @Override
    protected void onClicked(){
        super.onClicked();
        elevator.grabPeople();
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
        this.elevator = elevator;
    }
}
