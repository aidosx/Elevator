package com.ilya.busyElevator.game.Objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ilya.busyElevator.util.Assets;

public class ButtonObj extends AbstractButton {

    private boolean flip;
    private Elevator elevator;

    public ButtonObj(float x, float y,boolean flip){
        init(x, y, flip);
    }

    @Override
    public void init(float x, float y, boolean flip) {
        pressed = false;
        sound = Assets.instance.elevatorMove;
        width = 200;
        height = 120;

        buttonUp = new TextureRegion(com.ilya.busyElevator.util.Assets.instance.button.buttonUp);
        buttonDown = new TextureRegion(com.ilya.busyElevator.util.Assets.instance.button.buttonDown);
        currentReg = new TextureRegion(buttonUp);

        pos = new Vector2(x,y);

        hitbox = new Rectangle();

        this.flip = flip;
        if (flip) {
            buttonUp.flip(false, true);
            buttonDown.flip(false,true);
        }

        command = false;
    }

    @Override
    protected void onClicked() {
        super.onClicked();
        //Проверяем какая кнопка, верхняя или нижняя

            if (flip) {
                    elevator.moveUp();
                } else {
                    elevator.moveDown();
                }
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

    public void render(SpriteBatch batch) {
        batch.draw(currentReg, pos.x, pos.y,width, height);
    }

    @Override
    public void setElevator(Elevator elevator) {
        this.elevator = elevator;
    }

}
