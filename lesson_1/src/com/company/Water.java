package com.company;

public class Water extends Obstacle {
    int length;
    public Water(int length){
        this.length = length;
    }
    @Override
    public void doIt(Animal a){
        a.swim(this.length);
    }
}
