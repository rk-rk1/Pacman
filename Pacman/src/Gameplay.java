import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import java.util.Timer;
import java.util.TimerTask;

//import java.util.Timer;
public class Gameplay  extends JPanel implements KeyListener, ActionListener{
    private final int gridSize=20;
    // all things that move on he board, player is index 0
    private final int[] movXGrid ={12};
    private final int[] movYGrid ={10};
    private final int[] movX={gridSize* movXGrid[0]};
    private final int[] movY={gridSize* movYGrid[0]};

    private final int delay=10;

    //key being pressed
    private boolean rightK=false;
    private boolean leftK=false;
    private boolean upK=false;
    private boolean downK=false;

    private boolean playerMovementIsCalled=false; //to stop repeated calling of "playerMovement()"

    private final Timer timer= new Timer();
    private int movingball; // remove at some point
    private final int[][] map= {
//           0   2   4   6   8  10   12  14
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}, //0
            {1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1}, //1
            {1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1}, //2
            {1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1}, //3
            {1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1}, //4
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1}, //5
            {1,0,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,0,1}, //6
            {1,0,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,0,1}, //7
            {1,0,0,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,0,0,1}, //8
            {1,1,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,1,1}, //9
            {1,1,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,1,1}, //10
            {1,1,1,1,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1,1,1,1,1}, //11
            {1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1}, //12
            {1,1,1,1,1,1,0,1,1,0,1,0,0,0,0,0,0,1,0,1,1,0,1,1,1,1,1,1}, //13
            {1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1}, //14
            {1,1,1,1,1,1,0,1,1,0,1,0,0,0,0,0,0,1,0,1,1,0,1,1,1,1,1,1}, //15
            {1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1}, //16
            {1,1,1,1,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1,1,1,1,1}, //17
            {1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1}, //18
            {1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1}, //19
            {1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1}, //20
            {1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1}, //21
            {1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1}, //22
            {1,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,1}, //23
            {1,1,1,0,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,0,1,1,1}, //24
            {1,1,1,0,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,0,1,1,1}, //25
            {1,0,0,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,0,0,1}, //26
            {1,0,1,1,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,1,1,0,1}, //27
            {1,0,1,1,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,1,1,0,1}, //28
            {1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1}, //29
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
    };

    public Gameplay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        automaticActions();
    }

    public void paint(Graphics obj) {
        obj.setColor(Color.black);
        obj.fillRect(0, 0, this.getWidth(), this.getHeight());

        obj.setColor(Color.white);
        obj.setFont(new Font("serif", Font.BOLD, 25));

        obj.setColor(Color.white);
        for (int i =0; i<map.length; i++){
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j]==1){
                    obj.fillRect(j*gridSize, i*gridSize, gridSize, gridSize);
                }
            }

        }

        //Играч
        obj.setColor(Color.magenta);
        obj.fillRect(movX[0], movY[0], gridSize, gridSize);
        obj.fillOval(movingball, 300, 20, 20);

        obj.dispose();
    }

    public void automaticActions(){ //rename
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                movingball++;
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
        }
    }

    @Override
    public void keyPressed (KeyEvent e) {
        if(!playerMovementIsCalled) {
            playerMovement();
            playerMovementIsCalled=true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightK=true;
            leftK=false;
            upK=false;
            downK=false;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftK=true;
            rightK=false;
            upK=false;
            downK=false;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            upK=true;
            leftK=false;
            rightK=false;
            downK=false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            downK=true;
            leftK=false;
            upK=false;
            rightK=false;
        }
    }

    public boolean isPassable(int x, int y){
        return map[y][x] != 1;
    }

    public void playerMovement(){
        int wide = this.getWidth();
        int hight= this.getHeight();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(rightK && movX[0] < wide-gridSize*2 && isPassable(movXGrid[0]+1, movYGrid[0]))
                    moveRight();
                else if(leftK && movX[0] >= 20 && isPassable(movXGrid[0]-1, movYGrid[0]))
                    moveLeft();
                else if(upK && movY[0] >= 20 && isPassable(movXGrid[0], movYGrid[0]-1))
                    moveUp();
                else if(downK && movY[0] < hight-gridSize*2 && isPassable(movXGrid[0], movYGrid[0]+1))
                    moveDown();
                System.out.println(movXGrid[0]+ "  "+ movYGrid[0]);
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
                movY[0]+=direction;
                if(movY[entity]%10==0&&movY[0]%20!=0){
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
                movX[0]+=direction;
                if(movX[entity]%10==0&&movX[0]%20!=0){
                    movXGrid[entity]+=direction;
                }
                if (movX[entity]%20==0){
                    cancel();
                }

            }
        },0,delay);
    }

    public void moveUp() {
        animation(0, true, -1);
        //movY[0]-=gridSize;
    }
    public void moveDown() {
        animation(0, true, 1);
    }

    public void moveRight() {
        animation(0, false, 1);
    }
    public void moveLeft() {
        animation(0, false, -1);
    }
}
