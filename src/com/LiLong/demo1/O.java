package com.LiLong.demo1;

public class O extends Tetromino{
    public O() {
        cell[0]=new Cell(0,4,RussText.O);
        cell[1]=new Cell(0,5,RussText.O);
        cell[2]=new Cell(1,4,RussText.O);
        cell[3]=new Cell(1,5,RussText.O);
        //共计有零种旋转状态
        states = new State[0];
    }
}
