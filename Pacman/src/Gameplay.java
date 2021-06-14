import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.lang.Math;

public class Gameplay  extends JPanel implements KeyListener, ActionListener{
    private boolean play = true;
    private final int gridSize=20;
    // all things that move on he board, player is index 0
    private int[] movXGrid ={14, 12, 13, 14, 15};
    private int[] movYGrid ={23, 11, 11, 11, 11};
    private int[] movX={gridSize* movXGrid[0], gridSize* movXGrid[1],gridSize* movXGrid[2],gridSize* movXGrid[3],gridSize* movXGrid[4]};
    private int[] movY={gridSize* movYGrid[0], gridSize* movYGrid[1], gridSize* movYGrid[2],gridSize* movYGrid[3],gridSize* movYGrid[4]};
    private int lives = 1;
    private int score=0;
    private int dotsTaken = 0; //250 total

    private Image upPacman,downPacman,rightPacman, leftPacman, stalePacman;
    private Image upRedGhost,downRedGhost,rightRedGhost, leftRedGhost;
    private Image upOrangeGhost,downOrangeGhost,rightOrangeGhost, leftOrangeGhost;
    private Image upBlueGhost,downBlueGhost,rightBlueGhost, leftBlueGhost;
    private Image upPinkGhost,downPinkGhost,rightPinkGhost, leftPinkGhost;
    private Image scaredGhost, eatenGhost;

    private Ghost ghostRed =new Ghost(25,-3, true, 1);
    private Ghost ghostBlue =new Ghost(-3,-3, false, 1);
    private Ghost ghostOrange =new Ghost(-3,40, true, 1);
    private Ghost ghostPink =new Ghost(25,40, false, 1);


    private final int delay=7;
    //1 - up
    //2 - right
    //3 - down
    //4 - left
    private int newDir = 0;
    private boolean rightK=false;
    private boolean leftK=false;
    private boolean upK=false;
    private boolean downK=false;
    Random a = new Random();
    private boolean frightenedTime = false;
    private final Timer timer= new Timer();
    private final int[][] map= {
//           0   2   4   6   8  10   12  14  16  17 18   19  20  22
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}, //0
            {1,4,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,4,1}, //1
            {1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1}, //2
            {1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1}, //3
            {1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1}, //4
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1}, //5
            {1,0,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,0,1}, //6
            {1,0,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,0,1}, //7
            {1,0,0,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,0,0,1}, //8
            {1,1,1,1,1,1,0,1,1,1,1,1,2,1,1,2,1,1,1,1,1,0,1,1,1,1,1,1}, //9
            {1,1,1,1,1,1,0,1,1,1,1,1,2,1,1,2,1,1,1,1,1,0,1,1,1,1,1,1}, //10
            {1,1,1,1,1,1,0,1,1,2,2,2,2,2,2,2,2,2,2,1,1,0,1,1,1,1,1,1}, //11
            {1,1,1,1,1,1,0,1,1,2,1,1,1,3,3,1,1,1,2,1,1,0,1,1,1,1,1,1}, //12 dor
            {1,1,1,1,1,1,0,1,1,2,1,2,2,2,2,2,2,1,2,1,1,0,1,1,1,1,1,1}, //13
            {1,2,0,0,0,0,0,2,2,2,1,2,2,2,2,2,2,1,2,2,2,0,0,0,0,0,2,1}, //14
            {1,1,1,1,1,1,0,1,1,2,1,2,2,2,2,2,2,1,2,1,1,0,1,1,1,1,1,1}, //15
            {1,1,1,1,1,1,0,1,1,2,1,1,1,1,1,1,1,1,2,1,1,0,1,1,1,1,1,1}, //16
            {1,1,1,1,1,1,0,1,1,2,2,2,2,2,2,2,2,2,2,1,1,0,1,1,1,1,1,1}, //17
            {1,1,1,1,1,1,0,1,1,2,1,1,1,1,1,1,1,1,2,1,1,0,1,1,1,1,1,1}, //18
            {1,1,1,1,1,1,0,1,1,2,1,1,1,1,1,1,1,1,2,1,1,0,1,1,1,1,1,1}, //19
            {1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1}, //20
            {1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1}, //21
            {1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1}, //22
            {1,0,0,0,1,1,0,0,0,0,0,0,0,2,2,0,0,0,0,0,0,0,1,1,0,0,0,1}, //23
            {1,1,1,0,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,0,1,1,1}, //24
            {1,1,1,0,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,0,1,1,1}, //25
            {1,0,0,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,0,0,1}, //26
            {1,0,1,1,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,1,1,0,1}, //27
            {1,0,1,1,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,1,1,0,1}, //28
            {1,4,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,4,1}, //29
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}  //30
    };

    private void ghostRed(){
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!ghostRed.isInChase_inEaten()&&ghostRed.isInFrightened()){
                    ghostRed.setTargetXY(a.nextInt(100)-40,a.nextInt(100)-35 );
                }
                else if(ghostRed.isInChase_inEaten()&&ghostRed.isInFrightened()){
                        ghostRed.setTargetXY(11,14);
                }
                else if(ghostRed.isInChase_inEaten()&&!ghostRed.isInFrightened()){
                    ghostRed.setTargetXY(movXGrid[0], movYGrid[0]);
                }


                double up= 300, down= 300, right = 300, left= 300;
                //up
                if (isPassable(movXGrid[1],movYGrid[1]-1) && (!ghostRed.isMovingUpDown() || ghostRed.getDirection()==-1)){
                    up = Math.sqrt((double) Math.pow(ghostRed.getTargetX()-movXGrid[1],2)+ Math.pow(ghostRed.getTargetY()-movYGrid[1]+1,2));
                }
                //down
                if (isPassable(movXGrid[1],movYGrid[1]+1) && (!ghostRed.isMovingUpDown() || ghostRed.getDirection()==1)){
                    down = Math.sqrt((double) Math.pow(ghostRed.getTargetX()-movXGrid[1],2)+ Math.pow(ghostRed.getTargetY()-movYGrid[1]-1,2));
                }
                //left
                if (isPassable(movXGrid[1]-1,movYGrid[1]) && (ghostRed.isMovingUpDown() || ghostRed.getDirection()==-1)){
                    left = Math.sqrt((double) Math.pow(ghostRed.getTargetX()-movXGrid[1]+1,2)+ Math.pow(ghostRed.getTargetY()-movYGrid[1],2));
                }
                //right
                if (isPassable(movXGrid[1]+1,movYGrid[1]) && (ghostRed.isMovingUpDown() || ghostRed.getDirection()==+1)){
                    right = Math.sqrt((double) Math.pow(ghostRed.getTargetX()-movXGrid[1]-1,2)+ Math.pow(ghostRed.getTargetY()-movYGrid[1],2));
                }


                if (up<=down && up<=left && up<=right){
                    ghostRed.setDirection(-1);
                    ghostRed.setMovingUpDown(true);
                }
                else if (down<=up && down<=left && down<=right){
                    ghostRed.setDirection(1);
                    ghostRed.setMovingUpDown(true);
                }
                else if (right<=down && right<=left && right<=up){
                    ghostRed.setDirection(1);
                    ghostRed.setMovingUpDown(false);
                }
                else if (left<=down && left<=right && left<=up){
                    ghostRed.setDirection(-1);
                    ghostRed.setMovingUpDown(false);
                }

                animation(1, ghostRed.isMovingUpDown(), ghostRed.getDirection());
            }
        },delay*22,delay*26);
    }

    private void ghostBlue(){
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!ghostBlue.isInChase_inEaten()&&ghostBlue.isInFrightened()){
                    ghostBlue.setTargetXY(a.nextInt(100)-40,a.nextInt(100)-35 );
                }
                else if(ghostBlue.isInChase_inEaten()&&ghostBlue.isInFrightened()){
                    ghostBlue.setTargetXY(11,14);
                }
                else if(ghostBlue.isInChase_inEaten()&&!ghostBlue.isInFrightened()){
                    if (rightK)
                        ghostBlue.setTargetXY(movXGrid[0] + 4, movYGrid[0]);
                    else if (leftK)
                        ghostBlue.setTargetXY(movXGrid[0] - 4, movYGrid[0]);
                    else if (upK)
                        ghostBlue.setTargetXY(movXGrid[0], movYGrid[0] - 4);
                    else if (downK)
                        ghostBlue.setTargetXY(movXGrid[0], movYGrid[0] + 4);
                }

                double up= 300, down= 300, right = 300, left= 300;
                //up
                if (isPassable(movXGrid[2],movYGrid[2]-1) && (!ghostBlue.isMovingUpDown() || ghostBlue.getDirection()==-1)){
                    up = Math.sqrt((double) Math.pow(ghostBlue.getTargetX()-movXGrid[2],2)+ Math.pow(ghostBlue.getTargetY()-movYGrid[2]+1,2));
                }
                //down
                if (isPassable(movXGrid[2],movYGrid[2]+1) && (!ghostBlue.isMovingUpDown() || ghostBlue.getDirection()==1)){
                    down = Math.sqrt((double) Math.pow(ghostBlue.getTargetX()-movXGrid[2],2)+ Math.pow(ghostBlue.getTargetY()-movYGrid[2]-1,2));
                }
                //left
                if (isPassable(movXGrid[2]-1,movYGrid[2]) && (ghostBlue.isMovingUpDown() || ghostBlue.getDirection()==-1)){
                    left = Math.sqrt((double) Math.pow(ghostBlue.getTargetX()-movXGrid[2]+1,2)+ Math.pow(ghostBlue.getTargetY()-movYGrid[2],2));
                }
                //right
                if (isPassable(movXGrid[2]+1,movYGrid[2]) && (ghostBlue.isMovingUpDown() || ghostBlue.getDirection()==+1)){
                    right = Math.sqrt((double) Math.pow(ghostBlue.getTargetX()-movXGrid[2]-1,2)+ Math.pow(ghostBlue.getTargetY()-movYGrid[2],2));
                }

                if (up<=down && up<=left && up<=right){
                    ghostBlue.setDirection(-1);
                    ghostBlue.setMovingUpDown(true);
                }
                else if (down<=up && down<=left && down<=right){
                    ghostBlue.setDirection(1);
                    ghostBlue.setMovingUpDown(true);
                }
                else if (right<=down && right<=left && right<=up){
                    ghostBlue.setDirection(1);
                    ghostBlue.setMovingUpDown(false);
                }
                else if (left<=down && left<=right && left<=up){
                    ghostBlue.setDirection(-1);
                    ghostBlue.setMovingUpDown(false);
                }

                animation(2, ghostBlue.isMovingUpDown(), ghostBlue.getDirection());
            }
        },delay*22,delay*26);
    }

    private void ghostOrange(){
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!ghostOrange.isInChase_inEaten()&&ghostOrange.isInFrightened()){
                    ghostOrange.setTargetXY(a.nextInt(100)-40,a.nextInt(100)-35 );
                }
                else if(ghostOrange.isInChase_inEaten()&&ghostOrange.isInFrightened()){
                    ghostOrange.setTargetXY(11,14);
                }
                else if(ghostOrange.isInChase_inEaten()&&!ghostOrange.isInFrightened()){
                    ghostOrange.setTargetXY(movXGrid[0], movYGrid[0]);
                }
                double up= 300, down= 300, right = 300, left= 300;
                //up
                if (isPassable(movXGrid[3],movYGrid[3]-1) && (!ghostOrange.isMovingUpDown() || ghostOrange.getDirection()==-1)){
                    up = Math.sqrt((double) Math.pow(movXGrid[0]-movXGrid[3],2)+ Math.pow(movYGrid[0]-movYGrid[3]+1,2));
                }
                //down
                if (isPassable(movXGrid[3],movYGrid[3]+1) && (!ghostOrange.isMovingUpDown() || ghostOrange.getDirection()==1)){
                    down = Math.sqrt((double) Math.pow(movXGrid[0]-movXGrid[3],2)+ Math.pow(movYGrid[0]-movYGrid[3]-1,2));
                }
                //left
                if (isPassable(movXGrid[3]-1,movYGrid[3]) && (ghostOrange.isMovingUpDown() || ghostOrange.getDirection()==-1)){
                    left = Math.sqrt((double) Math.pow(movXGrid[0]-movXGrid[3]+1,2)+ Math.pow(movYGrid[0]-movYGrid[3],2));
                }
                //right
                if (isPassable(movXGrid[3]+1,movYGrid[3]) && (ghostOrange.isMovingUpDown() || ghostOrange.getDirection()==+1)){
                    right = Math.sqrt((double) Math.pow(movXGrid[0]-movXGrid[3]-1,2)+ Math.pow(movYGrid[0]-movYGrid[3],2));
                }


                if (up<=down && up<=left && up<=right){
                    ghostOrange.setDirection(-1);
                    ghostOrange.setMovingUpDown(true);
                }
                else if (down<=up && down<=left && down<=right){
                    ghostOrange.setDirection(1);
                    ghostOrange.setMovingUpDown(true);
                }
                else if (right<=down && right<=left && right<=up){
                    ghostOrange.setDirection(1);
                    ghostOrange.setMovingUpDown(false);
                }
                else if (left<=down && left<=right && left<=up){
                    ghostOrange.setDirection(-1);
                    ghostOrange.setMovingUpDown(false);
                }

                animation(3, ghostOrange.isMovingUpDown(), ghostOrange.getDirection());
            }
        },delay*22,delay*27);
    }

    private void ghostPink(){
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!ghostPink.isInChase_inEaten()&&ghostPink.isInFrightened()){
                    ghostPink.setTargetXY(a.nextInt(100)-40,a.nextInt(100)-35 );
                }
                else if(ghostPink.isInChase_inEaten()&&ghostPink.isInFrightened()){
                    ghostPink.setTargetXY(11,14);
                }
                else if(ghostPink.isInChase_inEaten()&&!ghostPink.isInFrightened()){
                    if (rightK)
                        ghostPink.setTargetXY(2 * (movXGrid[0] + 2) - movXGrid[1], 2 * movYGrid[0] - movYGrid[1]);
                    else if (leftK)
                        ghostPink.setTargetXY(2 * (movXGrid[0] - 2) - movXGrid[1], 2 * movYGrid[0] - movYGrid[1]);
                    else if (upK)
                        ghostPink.setTargetXY(2 * movXGrid[0] - movXGrid[1], 2 * (movYGrid[0] - 2) - movYGrid[1]);
                    else if (downK)
                        ghostPink.setTargetXY(2 * movXGrid[0] - movXGrid[1], 2 * (movYGrid[0] + 2) - movYGrid[1]);
                }
                double up= 300, down= 300, right = 300, left= 300;
                //up
                if (isPassable(movXGrid[4],movYGrid[4]-1) && (!ghostPink.isMovingUpDown() || ghostPink.getDirection()==-1)){
                    up = Math.sqrt((double) Math.pow(ghostPink.getTargetX()-movXGrid[4],2)+ Math.pow(ghostPink.getTargetY()-movYGrid[4]+1,2));
                }
                //down
                if (isPassable(movXGrid[4],movYGrid[4]+1) && (!ghostPink.isMovingUpDown() || ghostPink.getDirection()==1)){
                    down = Math.sqrt((double) Math.pow(ghostPink.getTargetX()-movXGrid[4],2)+ Math.pow(ghostPink.getTargetY()-movYGrid[4]-1,2));
                }
                //left
                if (isPassable(movXGrid[4]-1,movYGrid[4]) && (ghostPink.isMovingUpDown() || ghostPink.getDirection()==-1)){
                    left = Math.sqrt((double) Math.pow(ghostPink.getTargetX()-movXGrid[4]+1,2)+ Math.pow(ghostPink.getTargetY()-movYGrid[4],2));
                }
                //right
                if (isPassable(movXGrid[4]+1,movYGrid[4]) && (ghostPink.isMovingUpDown() || ghostPink.getDirection()==+1)){
                    right = Math.sqrt((double) Math.pow(ghostPink.getTargetX()-movXGrid[4]-1,2)+ Math.pow(ghostPink.getTargetY()-movYGrid[4],2));
                }

                if (up<=down && up<=left && up<=right){
                    ghostPink.setDirection(-1);
                    ghostPink.setMovingUpDown(true);
                }
                else if (down<=up && down<=left && down<=right){
                    ghostPink.setDirection(1);
                    ghostPink.setMovingUpDown(true);
                }
                else if (right<=down && right<=left && right<=up){
                    ghostPink.setDirection(1);
                    ghostPink.setMovingUpDown(false);
                }
                else if (left<=down && left<=right && left<=up){
                    ghostPink.setDirection(-1);
                    ghostPink.setMovingUpDown(false);
                }

                animation(4, ghostPink.isMovingUpDown(), ghostPink.getDirection());
            }
        },delay*22,delay*26);
    }

    public Gameplay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        automaticActions();
        loadImages();
        ghostRed();
        ghostBlue();
        ghostOrange();
        ghostPink();
    }

    public void paint(Graphics obj) {
        obj.setColor(Color.gray);
        obj.fillRect(0, 0, this.getWidth(), this.getHeight());

        obj.setColor(Color.black);
        obj.setFont(new Font("serif", Font.BOLD, 25));

        obj.setColor(Color.black);
        for (int i =0; i<map.length; i++){
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j]==0){
                    obj.fillRect(j*gridSize +gridSize*2/5, i*gridSize+gridSize*2/5, gridSize/5, gridSize/5);
                }
                else if(map[i][j]==3){     /////pacman stop at door///
                    obj.fillRect(j*gridSize, i*gridSize+gridSize*2/5, gridSize, gridSize/5);
                }
                else if (map[i][j]==1){
                    obj.fillRect(j*gridSize, i*gridSize, gridSize, gridSize);
                }else if (map[i][j]==4){
                    obj.fillRect(j*gridSize +gridSize*2/5, i*gridSize+gridSize*2/5, gridSize/2, gridSize/2);
                }
            }
        }

        if(!play){
            obj.setColor(Color.gray);
            obj.fillRect(0, 0, this.getWidth(), this.getHeight());
            for (int i =0; i<map.length; i++){
                for (int j = 0; j < map[0].length; j++) {
                    if (map[i][j]==0){
                        obj.fillRect(j*gridSize +gridSize*2/5, i*gridSize+gridSize*2/5, gridSize/5, gridSize/5);
                    }
                    else if(map[i][j]==3){     /////pacman stop at door///
                        obj.fillRect(j*gridSize, i*gridSize+gridSize*2/5, gridSize, gridSize/5);
                    }
                    else if (map[i][j]==1){
                        obj.fillRect(j*gridSize, i*gridSize, gridSize, gridSize);
                    }else if (map[i][j]==4){
                        obj.fillRect(j*gridSize +gridSize*2/5, i*gridSize+gridSize*2/5, gridSize/2, gridSize/2);
                    }
                }
            }
            obj.setColor(Color.white);
            obj.drawString(String.valueOf("End"), 250,350);
            obj.drawString(String.valueOf(score), 250,400);
        }


        if(play){
            if(ghostRed.getDirection()==1 && ghostRed.isMovingUpDown())
                obj.drawImage(downRedGhost, movX[1], movY[1], this);
            if(ghostRed.getDirection()==-1 && ghostRed.isMovingUpDown())
                obj.drawImage(upRedGhost, movX[1], movY[1], this);
            if(ghostRed.getDirection()==1 && !ghostRed.isMovingUpDown())
                obj.drawImage(rightRedGhost, movX[1], movY[1], this);
            if(ghostRed.getDirection()==-1 && !ghostRed.isMovingUpDown())
                obj.drawImage(leftRedGhost, movX[1], movY[1], this);
            if(frightenedTime)
                obj.drawImage(scaredGhost, movX[1], movY[1], this);

            if(ghostBlue.getDirection()==1 && ghostBlue.isMovingUpDown())
                obj.drawImage(downBlueGhost, movX[2], movY[2], this);
            if(ghostBlue.getDirection()==-1 && ghostBlue.isMovingUpDown())
                obj.drawImage(upBlueGhost, movX[2], movY[2], this);
            if(ghostBlue.getDirection()==1 && !ghostBlue.isMovingUpDown())
                obj.drawImage(rightBlueGhost, movX[2], movY[2], this);
            if(ghostBlue.getDirection()==-1 && !ghostBlue.isMovingUpDown())
                obj.drawImage(leftBlueGhost, movX[2], movY[2], this);
            if(frightenedTime)
                obj.drawImage(scaredGhost, movX[2], movY[2], this);

            if(ghostOrange.getDirection()==1 && ghostOrange.isMovingUpDown())
                obj.drawImage(downOrangeGhost, movX[3], movY[3], this);
            if(ghostOrange.getDirection()==-1 && ghostOrange.isMovingUpDown())
                obj.drawImage(upOrangeGhost, movX[3], movY[3], this);
            if(ghostOrange.getDirection()==1 && !ghostOrange.isMovingUpDown())
                obj.drawImage(rightOrangeGhost, movX[3], movY[3], this);
            if(ghostOrange.getDirection()==-1 && !ghostOrange.isMovingUpDown())
                obj.drawImage(leftOrangeGhost, movX[3], movY[3], this);
            if(frightenedTime)
                obj.drawImage(scaredGhost, movX[3], movY[3], this);

            if(ghostPink.getDirection()==1 && ghostPink.isMovingUpDown())
                obj.drawImage(downPinkGhost, movX[4], movY[4], this);
            if(ghostPink.getDirection()==-1 && ghostPink.isMovingUpDown())
                obj.drawImage(upPinkGhost, movX[4], movY[4], this);
            if(ghostPink.getDirection()==1 && !ghostPink.isMovingUpDown())
                obj.drawImage(rightPinkGhost, movX[4], movY[4], this);
            if(ghostPink.getDirection()==-1 && !ghostPink.isMovingUpDown())
                obj.drawImage(leftPinkGhost, movX[4], movY[4], this);
            if(frightenedTime)
                obj.drawImage(scaredGhost, movX[4], movY[4], this);
            // t/1 - nadolu
            // t/-1 -nagore
            // f/1  -nadqsno
            // f/-1 -nalqvo


            if(0==newDir)
                obj.drawImage(stalePacman, movX[0]+2, movY[0]+2, this);
            if(upK)
                obj.drawImage(upPacman, movX[0]+2, movY[0]+2, this);
            if(downK)
                obj.drawImage(downPacman, movX[0]+2, movY[0]+2, this);
            if(rightK)
                obj.drawImage(rightPacman, movX[0]+2, movY[0]+2, this);
            if(leftK)
                obj.drawImage(leftPacman, movX[0]+2, movY[0]+2, this);

            obj.drawString(String.valueOf(score), 30,637);
        }
        // obj.drawString(String.valueOf(lives), 30,637);
        obj.dispose();
    }


    public boolean isPassable(int x, int y){return map[y][x] != 1 && map[y][x] != 3;}

    public void callMovement(){
        if(1 == newDir){if(isPassable(movXGrid[0], movYGrid[0]-1)){upK=true; rightK=false; leftK=false; downK=false;}}
        if(2 == newDir){if(isPassable(movXGrid[0]+1, movYGrid[0])){rightK=true; leftK=false; upK=false; downK=false;}}
        if(3 == newDir){if(isPassable(movXGrid[0], movYGrid[0]+1)){downK=true; rightK=false; leftK=false; upK=false;}}
        if(4 == newDir){if(isPassable(movXGrid[0]-1, movYGrid[0])){leftK=true; rightK=false; upK=false; downK=false;}}

        if(rightK){if(isPassable(movXGrid[0]+1, movYGrid[0])){moveRight();}}
        if(leftK){if(isPassable(movXGrid[0]-1, movYGrid[0])){moveLeft();}}
        if (downK){if(isPassable(movXGrid[0], movYGrid[0]+1)){moveDown();}}
        if (upK){if(isPassable(movXGrid[0], movYGrid[0]-1)){moveUp();}}

        if (map[movYGrid[0]][movXGrid[0]] == 0){
            map[movYGrid[0]][movXGrid[0]] = 2;
            if(play)
                score+=10;
            dotsTaken++;
        }
        if (map[movYGrid[0]][movXGrid[0]] == 4){
            map[movYGrid[0]][movXGrid[0]] = 2;
            frightenedTime = true;
            ghostRed.frightened();
            ghostBlue.frightened();
            ghostOrange.frightened();
            ghostPink.frightened();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    frightenedTime = false;
                    ghostRed.chase();
                    ghostBlue.chase();
                    ghostOrange.chase();
                    ghostPink.chase();
                }
            },1000*15);

            if(play)
                score+=10;
            dotsTaken++;
        }

        if(30>=movX[0] && 280==movY[0] && leftK){movXGrid[0]=25;movX[0]=500;}
        if(510<=movX[0] && 280==movY[0] && rightK){movXGrid[0]=2;movX[0]=40;}
        if(40>movX[1] && 280==movY[1]){movXGrid[1]=25;movX[1]=500;}
        if(40>movX[2] && 280==movY[2]){movXGrid[2]=25;movX[2]=500;}
        if(40>movX[3] && 280==movY[3]){movXGrid[3]=25;movX[3]=500;}
        if(40>movX[4] && 280==movY[4]){movXGrid[4]=25;movX[4]=500;}
        if(500<movX[1] && 280==movY[1]){movXGrid[1]=2;movX[1]=40;}
        if(500<movX[2] && 280==movY[2]){movXGrid[2]=2;movX[2]=40;}
        if(500<movX[3] && 280==movY[3]){movXGrid[3]=2;movX[3]=40;}
        if(500<movX[4] && 280==movY[4]){movXGrid[4]=2;movX[4]=40;}


        if(dotsTaken==250)
            play=false;
        if(lives == 0)
            play=false;
    }


    public void automaticActions(){ //rename
        final int[] i = {0};
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (i[0] %20==0){
                    callMovement();
                    i[0] = 0;
                }
                i[0]++;
                if(movXGrid[0]==movXGrid[1] && movYGrid[0]==movYGrid[1]&&!frightenedTime){lives--;}
                if(movXGrid[0]==movXGrid[2] && movYGrid[0]==movYGrid[2]&&!frightenedTime){lives--;}
                if(movXGrid[0]==movXGrid[3] && movYGrid[0]==movYGrid[3]&&!frightenedTime){lives--;}
                if(movXGrid[0]==movXGrid[4] && movYGrid[0]==movYGrid[4]&&!frightenedTime){lives--;}

                if(movXGrid[0]==movXGrid[1] && movYGrid[0]==movYGrid[1]&&frightenedTime){ghostRed.eaten();}
                if(movXGrid[0]==movXGrid[2] && movYGrid[0]==movYGrid[2]&&frightenedTime){ghostBlue.eaten();}
                if(movXGrid[0]==movXGrid[3] && movYGrid[0]==movYGrid[3]&&frightenedTime){ghostOrange.eaten();}
                if(movXGrid[0]==movXGrid[4] && movYGrid[0]==movYGrid[4]&&frightenedTime){ghostPink.eaten();}


               // if(lives == 0)
                    //play=false;
                repaint();
            }
        },delay,delay);
    }

    @Override
    public void actionPerformed(ActionEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyPressed (KeyEvent e) {//OK
        if (e.getKeyCode() == KeyEvent.VK_UP){newDir = 1;}
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {newDir = 2;}
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {newDir = 3;}
        if (e.getKeyCode() == KeyEvent.VK_LEFT){newDir = 4;}
    }



    public void animation(int entity, boolean isUpDown, int direction){
        if(!(direction == 1 || direction == -1)) throw new IllegalArgumentException("direction must be 1 or -1");
        if(isUpDown){
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    movY[entity]+=direction;
                    if(movY[entity]%10==0&&movY[entity]%20!=0){
                        movYGrid[entity]+=direction;
                    }
                    if (movY[entity]%20==0){
                        cancel();
                    }
                }
            },0,delay);
        }else{
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    movX[entity]+=direction;
                    if(movX[entity]%10==0&&movX[entity]%20!=0){
                        movXGrid[entity]+=direction;
                    }
                    if (movX[entity]%20==0){
                        cancel();
                    }
                }
            },0,delay);
        }
    }
    public void moveUp() {animation(0, true, -1);}
    public void moveDown() {animation(0, true, 1);}
    public void moveRight() {animation(0, false, 1);}
    public void moveLeft() {animation(0, false, -1);}

    public void loadImages(){
        upPacman = new ImageIcon("sprites//PacmanUp.png").getImage();
        downPacman = new ImageIcon("sprites//PacmanDown.png").getImage();
        rightPacman = new ImageIcon("sprites//PacmanRight.png").getImage();
        leftPacman = new ImageIcon("sprites//PacmanLeft.png").getImage();
        stalePacman = new ImageIcon("sprites//PacmanStale.png").getImage();
        upRedGhost = new ImageIcon("sprites//RedUp.png").getImage();
        downRedGhost = new ImageIcon("sprites//RedDown.png").getImage();
        rightRedGhost = new ImageIcon("sprites//RedRight.png").getImage();
        leftRedGhost = new ImageIcon("sprites//RedLeft.png").getImage();
        upOrangeGhost = new ImageIcon("sprites//OrangeUp.png").getImage();
        downOrangeGhost = new ImageIcon("sprites//OrangeDown.png").getImage();
        rightOrangeGhost = new ImageIcon("sprites//OrangeRight.png").getImage();
        leftOrangeGhost = new ImageIcon("sprites//OrangeDown.png").getImage();
        upBlueGhost = new ImageIcon("sprites//BlueUp.png").getImage();
        downBlueGhost = new ImageIcon("sprites//BlueDown.png").getImage();
        rightBlueGhost = new ImageIcon("sprites//BlueRight.png").getImage();
        leftBlueGhost = new ImageIcon("sprites//BlueLeft.png").getImage();
        upPinkGhost = new ImageIcon("sprites//PinkUp.png").getImage();
        downPinkGhost = new ImageIcon("sprites//PinkDown.png").getImage();
        rightPinkGhost = new ImageIcon("sprites//PinkRight.png").getImage();
        leftPinkGhost = new ImageIcon("sprites//PinkDown.png").getImage();
        scaredGhost = new ImageIcon("sprites//Scared.png").getImage();
        eatenGhost = new ImageIcon("sprites//Eaten.png").getImage();
    }
}