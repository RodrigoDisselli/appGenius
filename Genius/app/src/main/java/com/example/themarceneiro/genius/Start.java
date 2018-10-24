package com.example.themarceneiro.genius;

public class Start {
    private static int state = 0;

    public int changeState(){
        return state = 1;
    }

    public int getState(){
        return state;
    }
}
