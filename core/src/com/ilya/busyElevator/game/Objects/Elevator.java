package com.ilya.busyElevator.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.ilya.busyElevator.game.WorldController;
import com.ilya.busyElevator.game.miscObjects.ElevatorParticle;

public class Elevator {

    private TextureRegion textureRegion;
    protected Vector2 pos;
    protected Vector2 dimension;
    //Какие места заняты, а какие нет
    private Vector2 pos1;
    private Vector2 pos2;
    private Vector2 pos3;
    private Vector2 pos4;
    private Array<PlaceInElevator> posList;
    private boolean shake = false;
    float y = 140;
    int count;
    float cdForElevator = 0;
    float countForErrors = 0;
    //Чтобы пропускать всего лишь одну команду
    public boolean jobIsDone;
    private ElevatorParticle pe;
    private boolean showParticle;

    protected Floor floorImIn;
    private WorldController worldController;

    private PlaceInElevator availablePos;

    public class PlaceInElevator{
        boolean full;
        Vector2 pos;
        People people;

        public PlaceInElevator(Vector2 elevatorPos){
            full = false;
            pos = new Vector2();
            pos.set(elevatorPos);
        }

        public void setPeople(People people){
            this.people = people;
            people.state = People.STATE.IN_ELEVATOR;
            people.pos.set(this.pos);
        }
        public void update(Vector2 pos){
            this.pos.set(pos);
            if (full)
                people.pos.set(this.pos);
        }
    }


    public Elevator(WorldController worldController){
        this.worldController = worldController;
        init();
    }

    private void init() {
        textureRegion = new TextureRegion();
        textureRegion = com.ilya.busyElevator.util.Assets.instance.elevator.elevator;
        pos = new Vector2(120,260);
        dimension = new Vector2(100,140);
        createSpaceForPeople();
        jobIsDone = false;
        count = 0;
        floorImIn = WorldController.listOfFloors.get(count);
        pe = new ElevatorParticle();
    }

    private void createSpaceForPeople(){
        posList = new Array<PlaceInElevator>();
        pos1 = new Vector2(pos.x+10,pos.y+10);
        pos2 = new Vector2(pos.x+40,pos.y+10);
        pos3 = new Vector2(pos.x+0.04f,pos.y+0.35f);
        pos4 = new Vector2(pos.x+0.25f,pos.y+0.35f);
        posList.add(new PlaceInElevator(pos1));
        posList.add(new PlaceInElevator(pos2));
        posList.add(new PlaceInElevator(pos3));
        posList.add(new PlaceInElevator(pos4));

    }

    protected void moveUp(){
        if (jobIsDone) return;
        if (pos.y+y>=960) return;
        pos.y+=y;
        count++;
        floorImIn = com.ilya.busyElevator.game.WorldController.listOfFloors.get(count);
        jobIsDone = true;
    }

    protected void moveDown(){
        if (jobIsDone) return;
        if (pos.y-y<260) return;
        pos.y-=y;
        count--;
        floorImIn = com.ilya.busyElevator.game.WorldController.listOfFloors.get(count);
        jobIsDone = true;
    }

    public void render(SpriteBatch batch){
        //Отвечает за тряску
        if (shake) {
            shake();
        }
        //Отвечает за черноту в лифте
        if (cdForElevator>0)
            batch.setColor(0,0,0,0.5f);
        //Отвечает за лифт
        batch.draw(textureRegion, pos.x, pos.y, dimension.x, dimension.y);

        //Отвечает за частицы
        if (showParticle) {
            if(!pe.started)
                pe.start(pos.x, pos.y-20);
            pe.render(batch);
            if (pe.finish()){
                System.out.println(pe.finish());
                showParticle = false;
                pe.started = false;
            }
        }
        batch.setColor(1,1,1,1);
    }

    protected void grabPeople(){
        if (jobIsDone) return;

        if (releasePeople()) {
            jobIsDone = true;
        } else
        if (!checkForFull()&&floorImIn.capacity.size!=0) {

            for (PlaceInElevator p : posList) {
                if (!p.full) {
                    availablePos = p;
                    break;
                }
            }
            availablePos.setPeople(floorImIn.capacity.first());

            availablePos.full = true;

            jobIsDone = true;

        }
        else{
            if (!jobIsDone) {
                shake = true;
                jobIsDone=true;
            }
        }
    }


    private boolean checkForFull() {
        for (PlaceInElevator p : posList)
            if (!p.full) return false;
        return true;
    }

    private boolean releasePeople(){
            for (PlaceInElevator p: posList){
                if (p.full){
                    if(p.people.getDestination() == floorImIn.name){
                        worldController.searchForPeople(p.people);
                        p.people = null;
                        p.full = false;
                        worldController.score++;
                        worldController.tempScore++;
                        //Со временем ошибки уменьшаются, чтобы спамящий игрок все равно получил наказание, но чтобы
                        //спокойный игрок мог совершить еще парочку, как то так епта
                        countForErrors--;
                        countForErrors=MathUtils.clamp(countForErrors,0,3);
                        System.out.println(countForErrors);
                        releasePeople();
                        return true;
                    }
                }
            }
        return false;
    }

    private void shake(){
        showParticle = true;
        pos.x-=10;
        if (pos.x<=100|pos.x>120)
            pos.x+=25;
        if (pos.x==125){
            pos.set(120,pos.y);
            shake = false;
            countForErrors++;
        }

    }


    public void update(){
        if (countForErrors>=3){
            cdForElevator+= Gdx.graphics.getDeltaTime();
            jobIsDone = true;
            if (cdForElevator>1.4){
                cdForElevator=0;
                countForErrors = 0;
            }
        }
        pos1.set(pos.x+10,pos.y+10);
        pos2.set(pos.x+50,pos.y+10);
        pos3.set(pos.x+10,pos.y+70);
        pos4.set(pos.x+50,pos.y+70);
        posList.get(0).update(pos1);
        posList.get(1).update(pos2);
        posList.get(2).update(pos3);
        posList.get(3).update(pos4);

    }


}
