package com.ilya.busyElevator.game.miscObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Background extends AbstractMiscObjects  {

    TextureRegion textureRegion;
    private Vector2 pos;
    private Vector2 dimension;

    public Background(){
        init();
    }

    private void init() {
        textureRegion = new TextureRegion(com.ilya.busyElevator.util.Assets.instance.bg.bg);
        pos = new Vector2(0,960-700);
        dimension = new Vector2(640,700);
    }

    public void render(SpriteBatch batch){
        batch.draw(textureRegion,pos.x,pos.y,dimension.x,dimension.y);
    }


}
