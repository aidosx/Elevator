package com.ilya.busyElevator.game.miscObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class ElevatorParticle extends AbstractMiscObjects implements Disposable {

    ParticleEffect pe;

    public boolean started;

    public ElevatorParticle(){
        pe = new ParticleEffect();
        pe.load(Gdx.files.internal("particle/elevatorParticle"),Gdx.files.internal("particle"));
        pe.scaleEffect(0.5f);
        started = false;
    }

    @Override
    public void render(SpriteBatch batch) {
        pe.update(Gdx.graphics.getDeltaTime());
        pe.draw(batch);
    }

    public boolean finish(){
       return pe.isComplete();
    }

    @Override
    public void dispose() {
        pe.dispose();
    }

    public void start(float x, float y) {
        pe.getEmitters().first().setPosition(x,y);
        pe.start();
        started = true;
    }

    public void setScalingBackToNormal(){
    }
}
