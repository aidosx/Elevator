package com.ilya.busyElevator.game.miscObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.ilya.busyElevator.util.Assets;

public class PreviewElevator extends AbstractMiscObjects {



    TextureRegion textureRegion;
    private Vector2 pos;
    private Vector2 dimension;
    private boolean moveDown;


    public PreviewElevator(int count){
        if (Assets.instance.previewElevators.get(count)!=null) {
            textureRegion = Assets.instance.previewElevators.get(count);
            pos = new Vector2();
            dimension = new Vector2();
            dimension.set(200, 280);
            moveDown = false;
        } else return;
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
        if (pos.y>=510)
            moveDown = true;
        if (pos.y<=500)
            moveDown = false;
    }

    public void setPos(float x,float y){
        pos.x = x;
        pos.y = y;
    }

    public Vector2 getDimension(){
        return dimension;
    }
}
