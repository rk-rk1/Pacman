import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay  extends JPanel implements KeyListener, ActionListener{

    private final int gridSize=20;
    private int playerX=200;
    private int playerY=200;
    private int playerXgrid=10;
    private int playerYgrid=10;
    private Timer timer;
    private int movingball;
    private int[][] map= {
            {1,1,1,1,1,1,1,1,1},
            {1,0,0,0,1,0,0,0,1},
            {1,0,1,0,1,0,1,0,1}
    };

    public Gameplay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(4, this);
        timer.start();
        timer = new Timer()
        repaint();
    }

    public void paint(Graphics obj) {

        obj.setColor(Color.black);
        obj.fillRect(0, 0, this.getWidth(), this.getHeight());

        //Рамка

        //Точки
        obj.setColor(Color.white);
        obj.setFont(new Font("serif", Font.BOLD, 25));

        //Играч
        obj.setColor(Color.magenta);
        obj.fillRect(playerX, playerY, gridSize, gridSize);
        obj.fillOval(movingball, 300, 20, 20);

        obj.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            timer.start();
            movingball++;
            repaint();

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //Граници
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                moveRight();
                if (playerX >this.getWidth()-gridSize)
                    playerX=this.getWidth()-gridSize;


        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                moveLeft();
                if (playerX <0)
                    playerX=0;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
                moveUp();
                if (playerY <0)
                    playerY=0;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                moveDown();
                if (playerY > this.getHeight()-gridSize-1)
                    playerY = this.getHeight()-gridSize;
        }
        repaint();
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void moveUp() {
        //play = true;
        playerY-=gridSize;
    }
    public void moveDown() {
        //play = true;
        playerY+=gridSize;
    }

    public void moveRight() {
        //play = true;
        playerX+=gridSize;
    }
    public void moveLeft() {
        //play = true;
        playerX-=gridSize;
    }
}
