package com.LiLong.demo1;

import java.awt.image.BufferedImage;
import java.util.Objects;

public class Cell {
    /**
     * 小方块类
     * 1.行，列
     */
    private int  row;
    private int col;
    private BufferedImage image;
    public Cell(){

    }

    public Cell(int row, int col, BufferedImage image) {
        this.row = row;
        this.col = col;
        this.image = image;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
    @Override
    public String toString() {
        return "Cell{" +
                "row=" + row +
                ", col=" + col +
                ", image=" + image +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return row == cell.row && col == cell.col && Objects.equals(image, cell.image);
    }
    //左移
    public void left(){
        col--;
    }
    //右移
    public void right(){
        col++;
    }
    //下移
    public void drop(){
        row++;
    }


}
