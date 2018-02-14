package com.ilya.busyElevator.game.Objects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.ilya.busyElevator.game.WorldController;

public class Floor {


    protected Array<People> capacity;
    private boolean full;
    private int MAX = 6;
    protected int name;

    protected Array<Vector2> placeList;
    float x = 250;
    float y;

    public Floor(){
        capacity = new Array<People>();
        full = false;
        placeList = new Array<Vector2>();
    }

    private void generatePeople(float x, float y,int value){
        for (int i = 0;i!=1;i++){
            //Создаем человека и ставим на позицию
            addHuman(x, y);
            //Указываем человеку на каком он этаже
            capacity.get(i).setFloorImIn(value);
            //Вызываем у человека желание пойти на другой этаж и воспользоваться лифтом
            capacity.get(i).genDestination();
            //Увeличиваем расстояние
            checkForFull();
            x-=64;
        }
        //Создаем вектора для легкого перемещения обьектов
        genVectors();

    }

    private void addHuman(float x, float y){
        if (!full) {
            capacity.add(new People(x,y));
        }
        checkForFull();
    }

    public void addHuman(){
        checkForFull();
        if (this.full) return;
        addHuman(x,y);
        capacity.get(capacity.size-1).setFloorImIn(this.name);
        capacity.get(capacity.size - 1).genDestination();
        capacity.get(capacity.size - 1).pos.set(placeList.get(capacity.size - 1).x + 300, placeList.get(capacity.size-1).y);
        WorldController.listOfPeople.add(capacity.get(capacity.size - 1));
    }

    private void genVectors(){
        float x = 250;
        for (int i = 0;i<MAX;i++) {
            placeList.add(new Vector2(x, y));
            x+=64;
        }
    }

    public void genFloor(int value){
        switch (value) {
            case 1: {
                y = 265;
                break;
            }
            case 2: {
                y = 404;
                break;
            }
            case 3: {
                y = 544;
                break;
            }
            case 4: {
                y = 680;
                break;
            }
            case 5: {
                y = 817;
                break;
            }
        }
        name = value;
        generatePeople(x,y,value);
    }

    public Array<People> getCapacity() {
        return capacity;
    }

    public void update(float delta){
        if (capacity.size == 0) return;
        if (capacity.get(0).state== People.STATE.IN_ELEVATOR) {
            capacity.removeIndex(0);
            checkForFull();
//             addHumanToAnotherFloor();
        }
        for (int i = 0;i<capacity.size;i++) {
            if (capacity.get(i).pos.x > placeList.get(i).x) {
                capacity.get(i).pos.lerp(placeList.get(i),0.5f*delta);   //capacity.get(i).pos.x -= 100 * delta;
                if (capacity.get(i).pos.x<=placeList.get(i).x)
                    capacity.get(i).pos.set(placeList.get(i));
            }
        }
    }



    private void addHumanToAnotherFloor(){
        int name = MathUtils.random(0,4);
        if (name+1!=this.name) {
            if (!WorldController.listOfFloors.get(name).full)
                WorldController.listOfFloors.get(name).addHuman();
        }
        else addHumanToAnotherFloor();
    }


    public int getName() {
        return name;
    }

    private void checkForFull(){
        if (capacity.size==MAX) full = true;
        else full = false;
    }

    public boolean isFull() {
        return full;
    }

}
