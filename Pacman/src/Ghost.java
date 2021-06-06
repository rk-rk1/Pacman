public class Ghost {

    private int id;

    private int scatterTargeX;
    private int scatterTargetY;

    private int targetX;
    private int targetY;


    private boolean isMovingUpDown;
    private int direction; //-1 or 1

    // 0 0 scatter mod
    // 0 1 frightened mod
    // 1 0 chase mod
    // 1 1 eaten mod
    private boolean inChase_inEaten =false;
    private boolean inFrightened =false;
    //texture variable?

    public void chase(){
        direction=direction*(-1);
    }

    public void scatter(){
        direction=direction*(-1);
        targetX=scatterTargeX;
        targetY=scatterTargetY;
    }

    public void setTargetXY(int x, int y){
        targetX=x;
        targetY=y;
    }

    public void move(){

    }


}
