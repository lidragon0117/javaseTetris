package com.LiLong.demo1;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 这是主类
 */
public class RussText  extends JPanel {
    public static BufferedImage I;
    public static BufferedImage J;
    public static BufferedImage L;
    public static BufferedImage O;
    public static BufferedImage S;
    public static BufferedImage T;
    public static BufferedImage Z;
    public static BufferedImage background;
    private static int FONTSIZE=25;
    //声明下落的方块
    private Tetromino currentOne=Tetromino.rundomOne();
    //声明将要下落的方块
    private Tetromino nextOne=Tetromino.rundomOne();
    //声明游戏区
    private Cell[][] wall=new Cell[18][9];
    //设置单元的值
    private static final int SIZE=36;
    //得分
    int[] scores={0,1,2,3,4};
    //当前游戏分 数
    private int totalscores;
    //消除行数
    int deleteLine=0;
    //状态
    public static final int GAMING=0;
    public static final int PAUSE=1;
    public static final int GAMEOVER=2;
    public static int gameState=0;
    String[] state= {"P[暂停]","C[继续]","S[重新开始]"};
    static {
        try {
            I= ImageIO.read(new File("javaseTetris/images/I.png"));
            J= ImageIO.read(new File("javaseTetris/images/J.png"));
            L= ImageIO.read(new File("javaseTetris/images/L.png"));
            O = ImageIO.read(new File("javaseTetris/images/O.png"));
            S= ImageIO.read(new File("javaseTetris/images/S.png"));
            T= ImageIO.read(new File("javaseTetris/images/T.png"));
            Z= ImageIO.read(new File("javaseTetris/images/Z.png"));
            background= ImageIO.read(new File("javaseTetris/images/background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
       g.drawImage(background,0,0,null);
       //平移坐标轴
        g.translate(19,15);
       //绘制游戏主区域
        paintWall(g);
        //绘制正在降落的四方格
        printCurrentOne(g);
        //绘制将要下落的四方块
        printNextONe(g);
        //绘制游戏得分
        prinTotaLScores(g);
        //游戏状态
        printState(g);

    }

    public void sortDropAction(){
        //判断能否下落
        if(canDrop()){
            //当前四方格下落一格
            currentOne.moveDrop();
        }else{
            //将四方格嵌入到墙中
            landToWall();
            //判断能否消行

            destoryLine();
            //判断游戏是否结束
            if(isGameOver()){
              gameState  = GAMEOVER;
            }else{
                //当游戏没有结束时，则继续生成新的四方格
                currentOne = nextOne;
                nextOne = Tetromino.rundomOne();
            }
        }
    }

    //创建顺时针旋转
    public void rotateRightAction(){
        currentOne.rotatRight();
        //判断是否越界或者是否重合
        if(outOfBounds() || coincide()){
            currentOne.rotatLeft();
        }
    }

    //四方格嵌入到墙中
    private void landToWall() {
        Cell[] cells = currentOne.cell;
        for (Cell cell : cells) {
            int row = cell.getRow();
            int col = cell.getCol();
            wall[row][col] = cell;
        }
    }
    //瞬间下落
    public void handDropAction(){
        while(true){
            //判断四方格能否下落
            if(canDrop()){
                currentOne.moveDrop();
            }else{
                break;
            }
        }
        //嵌入到墙中
        landToWall();
        //判断能否消行
        destoryLine();
        //判断游戏是否结束
        if(isGameOver()){
            gameState = GAMEOVER;
        }else{
            //游戏没有结束，继续生成新的四方格
            currentOne = nextOne;
            nextOne = Tetromino.rundomOne();
        }
    }
    //行是否满
    public boolean isFullLine(int raw){
        Cell[] cells=wall[raw];
        for(Cell cell:cells){
            if(cell==null){
                return false;
            }
        }
        return true;
    }
    //消行
    public void destoryLine(){
        int line=0;
        Cell[] cells=currentOne.cell;
        for(Cell cell:cells){
           int row=cell.getRow();
           if(isFullLine(row)){
               line++;
               for(int i=row;i>0;i--){
                   System.arraycopy(wall[i-1],0,wall[i],0,wall[0].length);
               }
               wall[0]=new Cell[9];
           }
        }
        //分数池获取分数
         totalscores +=scores[line];
        //统计总行数
        deleteLine+=line;
    }
//判断是否能下落
    public boolean canDrop(){
        Cell[] cells=currentOne.cell;
        for(Cell cell:cells){
            int row=cell.getRow();
            int col=cell.getCol();
            if(row==wall.length-1){
                return  false;
            }else if(wall[row+1][col]!=null){
                return false;
            }
        }
        return true;
    }

    //判断游戏是否结束
    public boolean isGameOver(){
        Cell[] cells=nextOne.cell;
        for(Cell cell:cells){
            int row=cell.getRow();
            int col=cell.getCol();
            if(wall[row][col]!=null){
                return true;
            }
        }
        return false;
    }

    private void printState(Graphics g) {
       if(gameState==GAMING){
          g.drawString(state[gameState],420,510);
       }else if(gameState==PAUSE){
           g.drawString(state[gameState],420,500);
       }else if(gameState==GAMEOVER){
           g.drawString(state[gameState],420,510);
           g.setColor(Color.red);
           g.drawString("GAMEOVER!",170,250);

       }
    }

    private void prinTotaLScores(Graphics g) {
        g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,FONTSIZE));
        g.drawString("得分："+totalscores,420,190);
        g.drawString("行数："+deleteLine,420,330);
    }

    private void printNextONe(Graphics g) {
        Cell[] cells=nextOne.cell;
        for(Cell cell:cells){
            int x=cell.getCol()*SIZE+280;
            int y=cell.getRow()*SIZE+13;
            g.drawImage(cell.getImage(), x,y,null);
        }
    }

    private void printCurrentOne(Graphics g) {
        Cell[] cells=currentOne.cell;
        for (Cell cell:cells){
            int x=cell.getCol()*SIZE;
            int y=cell.getRow()*SIZE;
            g.drawImage(cell.getImage(),x,y,null);
        }
    }


    //判断游戏是否出界
    public boolean outOfBounds(){
        Cell[] cells = currentOne.cell;
        for (Cell cell : cells) {
            int col = cell.getCol();
            int row = cell.getRow();
            if(row < 0 || row > wall.length - 1 || col < 0 || col > wall[0].length - 1){
                return true;
            }
        }
        return false;
    }
    //判断二维数组是否重合
    //判断方块是否重合
    public boolean coincide(){
        Cell[] cells = currentOne.cell;
        for (Cell cell : cells) {
            int row = cell.getRow();
            int col = cell.getCol();
            if(wall[row][col] != null){
                return true;
            }
        }
        return false;
    }
//    按键一次移动
    public void moveLeftAction(){
        currentOne.moveLeft();
        if(outOfBounds()||coincide()){
            currentOne.moveRight();
        }
    }
    public void moveRightAtion(){
        currentOne.moveRight();
        if(outOfBounds()||coincide()){
            currentOne.moveLeft();
        }
    }


    public void start() {
        gameState = GAMING;
        KeyListener l = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                switch (code) {
                    case KeyEvent.VK_DOWN:
                        sortDropAction(); //下落一格
                        break;
                    case KeyEvent.VK_LEFT:
                        moveLeftAction(); //左移
                        break;
                    case KeyEvent.VK_RIGHT:
                        moveRightAtion(); //右移
                        break;
                    case KeyEvent.VK_UP:
                        rotateRightAction(); //顺时针旋转
                        break;
                    case KeyEvent.VK_SPACE:
                        handDropAction(); //瞬间下落
                        break;
                    case KeyEvent.VK_P:
                        //判断当前游戏的状态
                        if (gameState == GAMING) {
                            gameState = PAUSE;
                        }
                        break;

                    case KeyEvent.VK_C:
                        //判断游戏状态
                        if (gameState == PAUSE) {
                            gameState = GAMING;
                        }
                        break;
                    case KeyEvent.VK_S:
                        //表示游戏重新开始
                        gameState = GAMING;
                        wall = new Cell[18][9];
                        currentOne = Tetromino.rundomOne();
                        nextOne = Tetromino.rundomOne();
                        totalscores = 0;
                        deleteLine = 0;
                        break;
                }

            }
        };
        //将俄罗斯方块窗口设置为焦点
        this.addKeyListener(l);
        this.requestFocus();

        while(true){
            //判断，当前游戏状态在游戏中时，每隔0.5秒下落
            if(gameState == GAMING){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //判断能否下落
                if(canDrop()){
                    currentOne.moveDrop();
                }else{
                    //嵌入到墙中
                    landToWall();
                    //判断能否消行
                    destoryLine();
                    //判断游戏是否结束
                    if(isGameOver()){
                        gameState = GAMEOVER;
                    }else{
                        currentOne = nextOne;
                        nextOne = Tetromino.rundomOne();
                        nextOne=Tetromino.rundomOne();
                    }
                }
            }
            repaint();
        }
    }
    //绘制游戏主区域
    public void paintWall(Graphics g) {
        for(int i = 0;i < wall.length;i++){
            for(int j = 0;j < wall[i].length;j++){
                int x = j * SIZE;
                int y = i * SIZE;
                Cell cell = wall[i][j];
                //判断当前单元格是否有小方块，如果没有则绘制矩形，否则将小方块嵌入到墙中
                if(cell == null){
                    g.drawRect(x, y, SIZE, SIZE);
                }else{
                    g.drawImage(cell.getImage(), x, y, null);
                }
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame=new JFrame("俄罗斯方块");
        //创建游戏界面
       RussText panel=new RussText();
       //面板嵌入窗口
        frame.add(panel);
        frame.setVisible(true);
        frame.setSize(610,750);
        frame.setLocationRelativeTo(null);
        //窗口关闭将停止
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.start();
    }
}
