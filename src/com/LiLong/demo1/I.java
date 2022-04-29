package com.LiLong.demo1;

public class I extends Tetromino{
    public I() {
        cell[0]=new Cell(0,4,RussText.I);
        cell[1]=new Cell(0,3,RussText.I);
        cell[2]=new Cell(0,5,RussText.I);
        cell[3]=new Cell(0,6,RussText.I);
        states=new State[2];
        //初始化相对位置
        states[0]=new State(0,0,0,-1,0,1,0,2);
        states[1]=new State(0,0,-1,0,1,0,2,0);
    }
}
