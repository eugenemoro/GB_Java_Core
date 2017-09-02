package com.company;

public class Cross extends Obstacle {
    int length;
    public Cross(int length){
        this.length = length;
    }
    @Override
    public void doIt(Animal a){
        a.run(this.length);
    }
}
