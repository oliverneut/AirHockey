package gamepackage;

/**
 * A class which defines a vector.
 */
public class GameVector {

    private double x, y;

    /**
     * Initializes the Game vector.
     * @param x The x-coordinate of the vector
     * @param y The y-coordinate of the vector
     */
    public GameVector(double x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x-coordinate of the vector.
     * @return The x-coordinate of the vector
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the vector.
     * @return The y-coordinate of the vector
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the x-coordinate of the vector.
     * @param x The x-coordinate to be set
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Sets the y-coordinate of the vector.
     * @param y The y-coordinate to be set
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Adds another GameVector to this GameVector.
     * @param vector the vector to be added to this GameVector
     */
    public void addVector(GameVector vector){
        this.x += vector.getX();
        this.y += vector.getY();
    }
}
