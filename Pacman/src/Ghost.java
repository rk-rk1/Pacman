public class Ghost {

    private int id;

    private int scatterTargetX;
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


    public boolean isMovingUpDown() { return isMovingUpDown; }
    public void setMovingUpDown(boolean movingUpDown) { isMovingUpDown = movingUpDown; }

    public int getDirection() { return direction; }
    public void setDirection(int direction) {
        if(direction==1||direction==-1)
        this.direction = direction;
    }

    public boolean isInChase_inEaten() { return inChase_inEaten; }
    public boolean isInFrightened() { return inFrightened; }

    public void chase(){
        inChase_inEaten=true;
        inFrightened=false;
        direction=direction*(-1);
    }

    public void scatter(){
        inChase_inEaten=false;
        inFrightened=false;
        direction=direction*(-1);
        setTargetXY(scatterTargetX, scatterTargetY);
    }

    public void frightened(){
        inChase_inEaten=false;
        inFrightened=true;
        direction=direction*(-1);
    }

    public void eaten() {
        inChase_inEaten=false;
        setTargetXY(14,11);
    }

    public void setTargetXY(int x, int y){
        targetX=x;
        targetY=y;
    }

    public int getTargetX() { return targetX; }
    public int getTargetY() { return targetY; }

    public Ghost(int scatterTargetX, int scatterTargetY, boolean isMovingUpDown, int direction){

        this.scatterTargetX=scatterTargetX;
        this.scatterTargetY=scatterTargetY;
        this.isMovingUpDown=isMovingUpDown;
        this.direction=direction;
    }

}
