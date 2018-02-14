package com.ilya.busyElevator.game.miscObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.ilya.busyElevator.util.Assets;

public class EndGameBanner extends AbstractMiscObjects {
    private Vector2 pos;
    private Vector2 dimension;
    TextureRegion reg;
    public EndGameBanner(){
        pos = new Vector2(70,-1400);
        dimension = new Vector2(500,700);
        reg = new TextureRegion(Assets.instance.misc.miscendGameBanner);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(reg,pos.x,pos.y,dimension.x,dimension.y);
    }

    public void setPos(float x,float y){
        pos.x = x;
        pos.y = y;
    }

    public Vector2 getPos(){
        return pos;
    }

    public Vector2 getDimension() {
        return dimension;
    }
}
