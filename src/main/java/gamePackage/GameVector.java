package gamePackage;

public class GameVector {
    public double x, y;

    public GameVector(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void addVector(GameVector vector){
        x += vector.x;
        y += vector.y;
    }
}
