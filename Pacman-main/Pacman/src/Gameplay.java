import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.lang.Math;

//import java.util.Timer;
public class Gameplay  extends JPanel implements KeyListener, ActionListener{
    private final int gridSize=20;
    // all things that move on he board, player is index 0
    private final int[] movXGrid ={14, 1};
    private final int[] movYGrid ={23, 3};
    private final int[] movX={gridSize* movXGrid[0], gridSize* movXGrid[1]};
    private final int[] movY={gridSize* movYGrid[0], gridSize* movYGrid[1]};
    
    private Image upP,downP,rightP, leftP;
    private Image upRedGhost,downRedGhost,rightRedGhost, leftRedGhost;
    private Image upYellowGhost,downYellowGhost,rightYellowGhost, leftYellowGhost;
    private Image upBlueGhost,downBlueGhost,rightBlueGhost, leftBlueGhost;
    private Image upPurpleGhost,downPurpleGhost,rightPurpleGhost, leftPurpleGhost;

    private Ghost ghost1 =new Ghost(-3,-3, true, 1);
    // t/1 - nadolu
    // t/-1 -nagore
    private int score=0;

    private final int delay=10;

    //key being pressed
    private boolean rightK=false;
    private boolean leftK=false;
    private boolean upK=false;
    private boolean downK=false;

    private boolean playerMovementIsCalled=false; //to stop repeated calling of "playerMovement()"

    private final Timer timer= new Timer();
    private final int[][] map= {
//           0   2   4   6   8  10   12  14  16  17 18   19  20  22
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}, //0
            {1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1}, //1
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
            {1,0,0,0,0,0,0,2,2,2,1,2,2,2,2,2,2,1,2,2,2,0,0,0,0,0,0,1}, //14
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
            {1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1}, //29
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}  //30
    };

    private void ghost1(){
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ghost1.setTargetXY(movXGrid[0], movYGrid[0]);
                double up= 60, down= 60, right = 60, left= 60;
                //up
                if (isPassable(movXGrid[1],movYGrid[1]-1) && (!ghost1.isMovingUpDown() || ghost1.getDirection()==-1)){
                    // System.out.println("up");
                    up = Math.sqrt((double) Math.pow(movXGrid[0]-movXGrid[1],2)+ Math.pow(movYGrid[0]-movYGrid[1]-1,2));
                }
                //down
                if (isPassable(movXGrid[1],movYGrid[1]+1) && (!ghost1.isMovingUpDown() || ghost1.getDirection()==1)){
                    // System.out.println("d");
                    down = Math.sqrt((double) Math.pow(movXGrid[0]-movXGrid[1],2)+ Math.pow(movYGrid[0]-movYGrid[1]+1,2));
                }
                //left
                if (isPassable(movXGrid[1]-1,movYGrid[1]) && (ghost1.isMovingUpDown() || ghost1.getDirection()==-1)){
                    // System.out.println("left");
                    left = Math.sqrt((double) Math.pow(movXGrid[0]-movXGrid[1]-1,2)+ Math.pow(movYGrid[0]-movYGrid[1],2));
                }
                //right
                if (isPassable(movXGrid[1]+1,movYGrid[1]) && (ghost1.isMovingUpDown() || ghost1.getDirection()==+1)){
                    // System.out.println("right");
                    right = Math.sqrt((double) Math.pow(movXGrid[0]-movXGrid[1]+1,2)+ Math.pow(movYGrid[0]-movYGrid[1],2));
                }

                System.out.println(movXGrid[1]+" "+  movYGrid[1]);
                System.out.println(up+ " "+ down+ " "+right+ " "+left);
                // System.out.println(up);

                if (up<=down && up<=left && up<=right){
                    ghost1.setDirection(-1);
                    ghost1.setMovingUpDown(true);
                }
                else if (down<=up && down<=left && down<=right){
                    ghost1.setDirection(1);
                    ghost1.setMovingUpDown(true);
                }
                else if (right<=down && right<=left && right<=up){
                    ghost1.setDirection(1);
                    ghost1.setMovingUpDown(false);
                }
                else if (left<=down && left<=right && left<=up){
                    ghost1.setDirection(-1);
                    ghost1.setMovingUpDown(false);
                }

                animation(1, ghost1.isMovingUpDown(), ghost1.getDirection());
            }
        },delay*22,delay*22);
    }

    public Gameplay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        automaticActions();
        ghost1();
    }

    public void paint(Graphics obj) {
        obj.setColor(Color.black);
        obj.fillRect(0, 0, this.getWidth(), this.getHeight());

        obj.setColor(Color.white);
        obj.setFont(new Font("serif", Font.BOLD, 25));

        obj.setColor(Color.white);
        for (int i =0; i<map.length; i++){
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j]==0){
                    obj.fillRect(j*gridSize +gridSize*2/5, i*gridSize+gridSize*2/5, gridSize/5, gridSize/5);
                }
                else if(map[i][j]==3){     /////pacman stop at door///
                    obj.fillRect(j*gridSize, i*gridSize+gridSize*2/5, gridSize, gridSize/5);
                }
                //
                else if (map[i][j]==1){
                    obj.fillRect(j*gridSize, i*gridSize, gridSize, gridSize);
                }
            }
        }
        obj.setColor(Color.red);
        obj.fillRect(movX[1], movY[1], gridSize, gridSize);//ghost

        obj.setColor(Color.magenta);
        obj.drawString(String.valueOf(score), 30,637);
        obj.fillRect(movX[0], movY[0], gridSize, gridSize);//pacman

        obj.dispose();
    }

    public void automaticActions(){ //rename
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        },8,8);

    }

    @Override
    public void actionPerformed(ActionEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {
        /*
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightK=false;
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftK=false;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            upK=false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            downK=false;
        }*/
    }

    @Override
    public void keyPressed (KeyEvent e) {
        if(!playerMovementIsCalled) {
            playerMovement();
            playerMovementIsCalled=true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && isPassable(movXGrid[0]+1, movYGrid[0])) {
            rightK=true;
            leftK=false;
            upK=false;
            downK=false;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT && isPassable(movXGrid[0]-1, movYGrid[0])) {
            leftK=true;
            rightK=false;
            upK=false;
            downK=false;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP && isPassable(movXGrid[0], movYGrid[0]-1)) {
            upK=true;
            leftK=false;
            rightK=false;
            downK=false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN && isPassable(movXGrid[0], movYGrid[0]+1)) {
            downK=true;
            leftK=false;
            upK=false;
            rightK=false;
        }
    }

    public boolean isPassable(int x, int y){
        return map[y][x] != 1 && map[y][x] != 3;
    }

    public void playerMovement(){
        int wide = this.getWidth();
        int hight= this.getHeight();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //new stuff
                if (map[movYGrid[0]][movXGrid[0]] == 0){
                    map[movYGrid[0]][movXGrid[0]] = 2;
                    score+=10;
                }

                if(rightK && movX[0] < wide-gridSize*2 && isPassable(movXGrid[0]+1, movYGrid[0]))
                    moveRight();
                else if(leftK && movX[0] >= 20 && isPassable(movXGrid[0]-1, movYGrid[0]))
                    moveLeft();
                else if(upK && movY[0] >= 20 && isPassable(movXGrid[0], movYGrid[0]-1))
                    moveUp();
                else if(downK && movY[0] < hight-gridSize*2 && isPassable(movXGrid[0], movYGrid[0]+1))
                    moveDown();
                //System.out.println(movXGrid[0]+ "  "+ movYGrid[0]);

            }
        },0, delay*20+delay/2);
    }

    // entity - pointing to the entity index in mov arrays,
    // isUpDown - if true than its moving up or down(y) if false move left or right(x),
    // direction can be 1 or -1 changing Y or X by this much
    public void animation(int entity, boolean isUpDown, int direction){
        if(!(direction == 1 || direction == -1)) throw new IllegalArgumentException("direction must be 1 or -1");
        if(isUpDown)
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
        else
            timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                movX[entity]+=direction;
                if(movX[entity]%10==0&&movX[0]%20!=0){
                    movXGrid[entity]+=direction;
                }
                if (movX[entity]%20==0){
                    cancel();
                }

            }
        },0,delay);
    }

    public void moveUp() {animation(0, true, -1);}
    public void moveDown() {animation(0, true, 1);}
    public void moveRight() {animation(0, false, 1);}
    public void moveLeft() {animation(0, false, -1);}

    public void loadImages(){
        upP = new ImageIcon("src").getImage();
        downP = new ImageIcon("src").getImage();
        rightP = new ImageIcon("src").getImage();
        leftP = new ImageIcon("src").getImage();
        upRedGhost = new ImageIcon("src").getImage();
        downRedGhost = new ImageIcon("src").getImage();
        rightRedGhost = new ImageIcon("src").getImage();
        leftRedGhost = new ImageIcon("src").getImage();
        upYellowGhost = new ImageIcon("src").getImage();
        downYellowGhost = new ImageIcon("src").getImage();
        rightYellowGhost = new ImageIcon("src").getImage();
        leftYellowGhost = new ImageIcon("src").getImage();
        upBlueGhost = new ImageIcon("src").getImage();
        downBlueGhost = new ImageIcon("src").getImage();
        rightBlueGhost = new ImageIcon("src").getImage();
        leftBlueGhost = new ImageIcon("src").getImage();
        upPurpleGhost = new ImageIcon("src").getImage();
        downPurpleGhost = new ImageIcon("src").getImage();
        rightPurpleGhost = new ImageIcon("src").getImage();
        leftPurpleGhost = new ImageIcon("src").getImage();
    }
}
