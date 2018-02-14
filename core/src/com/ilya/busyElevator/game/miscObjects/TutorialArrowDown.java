package com.ilya.busyElevator.game.miscObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.ilya.busyElevator.util.Assets;

public class TutorialArrowDown extends AbstractMiscObjects {

    TextureRegion textureRegion;
    private Vector2 pos;
    private Vector2 dimension;
    private boolean moveDown;


    public TutorialArrowDown(float x,float y){
        textureRegion = Assets.instance.misc.tutorialArrowDown;
        pos = new Vector2();
        dimension = new Vector2();
        pos.set(x,y);
        dimension.set(64,64);
        moveDown = true;
    }
    @Override
    public void render(SpriteBatch batch) {
        batch.draw(textureRegion,pos.x,pos.y,dimension.x,dimension.y);
        update();
    }

    public void update(){
        if (moveDown)
            pos.y-=3;
        else pos.y+=3;
        if (pos.y>=380)
            moveDown = true;
        if (pos.y<=100)
            moveDown = false;
    }
}
