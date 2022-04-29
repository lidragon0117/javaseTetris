package com.LiLong.demo1;

public class Z extends Tetromino{
    public Z() {
        cell[0]=new Cell(1,4,RussText.Z);
        cell[1]=new Cell(0,3,RussText.Z);
        cell[2]=new Cell(0,4,RussText.Z);
        cell[3]=new Cell(1,5,RussText.Z);
        //共计有两种旋转状态
        states = new State[2];
        states[0] = new State(0, 0, -1, -1, -1, 0, 0, 1);
        states[1] = new State(0, 0, -1, 1, 0, 1, 1, 0);
    }
}
