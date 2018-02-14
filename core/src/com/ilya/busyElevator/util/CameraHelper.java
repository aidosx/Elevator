package com.ilya.busyElevator.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class CameraHelper {

    OrthographicCamera camera;
    private final float MAX_ZOOM_IN = 0.25f;
    private Vector2 position;
    private float zoom;
    private float amount = 0.1f;
    private Vector2 target;

    public CameraHelper(OrthographicCamera camera){
        this.camera = camera;
        target = new Vector2();
        position = new Vector2();
    }

    public void addZoom ()
    {
        if (zoom<MAX_ZOOM_IN) return;
        zoom-=0.01f;
    }

    public void addPos(){
        if (target.x>position.x) position.x+=amount;
        if (target.x<position.x) position.x-=amount;
        if (target.y>position.y) position.y-=amount;
        if (target.y<position.y) position.y+=amount;
    }

    public void applyToCamera(){
        camera.position.set(position,0);
        camera.zoom = zoom;
        camera.update();
    }

    public void setPos(float x ,float y){
        position.x +=x;
        position.y +=y;
    }

    public void setTarget(Vector2 target) {
        this.target = target;
    }
}
