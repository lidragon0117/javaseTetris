package com.LiLong.demo1;

public class T extends Tetromino{
    public T() {
        cell[0]=new Cell(0,4,RussText.T);
        cell[1]=new Cell(0,3,RussText.T);
        cell[2]=new Cell(0,5,RussText.T);
        cell[3]=new Cell(1,4,RussText.T);
        states=new State[4];
        states[0]=new State(0,0,0,-1,0,1,1,0);
        states[1]=new State(0,0,-1,0,1,0,0,-1);
        states[2]=new State(0,0,0,1,0,-1,-1,0);
        states[3]=new State(0,0,1,0,-1,0,0,1);
    }
}
