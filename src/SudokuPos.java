

public class SudokuPos{
    private int y;
    private int x;

    public SudokuPos(){
        setPos(0, 0);
    }

    public SudokuPos(int x, int y){
        setPos(x, y);
    }

    public void setPos(int x, int y){
        this.y = y;
        this.x = x;
    }

    public int getY(){
        return y;
    }

    public int getX(){
        return x;
    }
}
