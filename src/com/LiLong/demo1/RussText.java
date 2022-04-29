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
 * ��������
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
    //��������ķ���
    private Tetromino currentOne=Tetromino.rundomOne();
    //������Ҫ����ķ���
    private Tetromino nextOne=Tetromino.rundomOne();
    //������Ϸ��
    private Cell[][] wall=new Cell[18][9];
    //���õ�Ԫ��ֵ
    private static final int SIZE=36;
    //�÷�
    int[] scores={0,1,2,3,4};
    //��ǰ��Ϸ�� ��
    private int totalscores;
    //��������
    int deleteLine=0;
    //״̬
    public static final int GAMING=0;
    public static final int PAUSE=1;
    public static final int GAMEOVER=2;
    public static int gameState=0;
    String[] state= {"P[��ͣ]","C[����]","S[���¿�ʼ]"};
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
       //ƽ��������
        g.translate(19,15);
       //������Ϸ������
        paintWall(g);
        //�������ڽ�����ķ���
        printCurrentOne(g);
        //���ƽ�Ҫ������ķ���
        printNextONe(g);
        //������Ϸ�÷�
        prinTotaLScores(g);
        //��Ϸ״̬
        printState(g);

    }

    public void sortDropAction(){
        //�ж��ܷ�����
        if(canDrop()){
            //��ǰ�ķ�������һ��
            currentOne.moveDrop();
        }else{
            //���ķ���Ƕ�뵽ǽ��
            landToWall();
            //�ж��ܷ�����

            destoryLine();
            //�ж���Ϸ�Ƿ����
            if(isGameOver()){
              gameState  = GAMEOVER;
            }else{
                //����Ϸû�н���ʱ������������µ��ķ���
                currentOne = nextOne;
                nextOne = Tetromino.rundomOne();
            }
        }
    }

    //����˳ʱ����ת
    public void rotateRightAction(){
        currentOne.rotatRight();
        //�ж��Ƿ�Խ������Ƿ��غ�
        if(outOfBounds() || coincide()){
            currentOne.rotatLeft();
        }
    }

    //�ķ���Ƕ�뵽ǽ��
    private void landToWall() {
        Cell[] cells = currentOne.cell;
        for (Cell cell : cells) {
            int row = cell.getRow();
            int col = cell.getCol();
            wall[row][col] = cell;
        }
    }
    //˲������
    public void handDropAction(){
        while(true){
            //�ж��ķ����ܷ�����
            if(canDrop()){
                currentOne.moveDrop();
            }else{
                break;
            }
        }
        //Ƕ�뵽ǽ��
        landToWall();
        //�ж��ܷ�����
        destoryLine();
        //�ж���Ϸ�Ƿ����
        if(isGameOver()){
            gameState = GAMEOVER;
        }else{
            //��Ϸû�н��������������µ��ķ���
            currentOne = nextOne;
            nextOne = Tetromino.rundomOne();
        }
    }
    //���Ƿ���
    public boolean isFullLine(int raw){
        Cell[] cells=wall[raw];
        for(Cell cell:cells){
            if(cell==null){
                return false;
            }
        }
        return true;
    }
    //����
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
        //�����ػ�ȡ����
         totalscores +=scores[line];
        //ͳ��������
        deleteLine+=line;
    }
//�ж��Ƿ�������
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

    //�ж���Ϸ�Ƿ����
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
        g.drawString("�÷֣�"+totalscores,420,190);
        g.drawString("������"+deleteLine,420,330);
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


    //�ж���Ϸ�Ƿ����
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
    //�ж϶�ά�����Ƿ��غ�
    //�жϷ����Ƿ��غ�
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
//    ����һ���ƶ�
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
                        sortDropAction(); //����һ��
                        break;
                    case KeyEvent.VK_LEFT:
                        moveLeftAction(); //����
                        break;
                    case KeyEvent.VK_RIGHT:
                        moveRightAtion(); //����
                        break;
                    case KeyEvent.VK_UP:
                        rotateRightAction(); //˳ʱ����ת
                        break;
                    case KeyEvent.VK_SPACE:
                        handDropAction(); //˲������
                        break;
                    case KeyEvent.VK_P:
                        //�жϵ�ǰ��Ϸ��״̬
                        if (gameState == GAMING) {
                            gameState = PAUSE;
                        }
                        break;

                    case KeyEvent.VK_C:
                        //�ж���Ϸ״̬
                        if (gameState == PAUSE) {
                            gameState = GAMING;
                        }
                        break;
                    case KeyEvent.VK_S:
                        //��ʾ��Ϸ���¿�ʼ
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
        //������˹���鴰������Ϊ����
        this.addKeyListener(l);
        this.requestFocus();

        while(true){
            //�жϣ���ǰ��Ϸ״̬����Ϸ��ʱ��ÿ��0.5������
            if(gameState == GAMING){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //�ж��ܷ�����
                if(canDrop()){
                    currentOne.moveDrop();
                }else{
                    //Ƕ�뵽ǽ��
                    landToWall();
                    //�ж��ܷ�����
                    destoryLine();
                    //�ж���Ϸ�Ƿ����
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
    //������Ϸ������
    public void paintWall(Graphics g) {
        for(int i = 0;i < wall.length;i++){
            for(int j = 0;j < wall[i].length;j++){
                int x = j * SIZE;
                int y = i * SIZE;
                Cell cell = wall[i][j];
                //�жϵ�ǰ��Ԫ���Ƿ���С���飬���û������ƾ��Σ�����С����Ƕ�뵽ǽ��
                if(cell == null){
                    g.drawRect(x, y, SIZE, SIZE);
                }else{
                    g.drawImage(cell.getImage(), x, y, null);
                }
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame=new JFrame("����˹����");
        //������Ϸ����
       RussText panel=new RussText();
       //���Ƕ�봰��
        frame.add(panel);
        frame.setVisible(true);
        frame.setSize(610,750);
        frame.setLocationRelativeTo(null);
        //���ڹرս�ֹͣ
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.start();
    }
}
