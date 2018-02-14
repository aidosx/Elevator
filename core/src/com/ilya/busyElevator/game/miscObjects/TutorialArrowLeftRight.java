package com.ilya.busyElevator.game.miscObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.ilya.busyElevator.util.Assets;

public class TutorialArrowLeftRight extends AbstractMiscObjects
{


    TextureRegion textureRegion;
    private Vector2 pos;
    private Vector2 dimension;
    private boolean moveLeft;


    public TutorialArrowLeftRight(float x,float y){
        textureRegion = Assets.instance.misc.tutorialArrowLeftRight;
        pos = new Vector2();
        dimension = new Vector2();
        pos.set(x,y);
        dimension.set(64,64);
        moveLeft = true;
    }
    @Override
    public void render(SpriteBatch batch) {
        batch.draw(textureRegion,pos.x,pos.y,dimension.x,dimension.y);
        update();
    }

    public void update(){
        if (moveLeft)
            pos.x-=3;
        else pos.x+=3;
        if (pos.x>=420)
            moveLeft = true;
        if (pos.x<=140)
            moveLeft = false;
    }
}
