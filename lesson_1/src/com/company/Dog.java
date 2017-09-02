package com.company;

public class Dog extends Animal{
    public Dog(String name, int maxRunDistance, int maxJumpHeight, int maxSwimDistance){
        super(name, maxRunDistance, maxJumpHeight, maxSwimDistance);
        this.type = "Dog";
    }
}
