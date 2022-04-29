package com.LiLong.demo1;

public class S extends Tetromino{
    public S() {
        cell[0]=new Cell(0,4,RussText.S);
        cell[1]=new Cell(0,5,RussText.S);
        cell[2]=new Cell(1,3,RussText.S);
        cell[3]=new Cell(1,4,RussText.S);
        states=new State[2];
        states[0]=new State(0,0,0,1,1,-1,1,0);
        states[1]=new State(0,0,1,0,-1,-1,0,-1);
    }
}
