package gamepackage;

/**
 * A class which defines a vector.
 */
public class GameVector {

    private transient double xcord;
    private transient double ycord;

    /**
     * Initializes the Game vector.
     * @param x The x-coordinate of the vector
     * @param y The y-coordinate of the vector
     */
    public GameVector(double x, double y) {
        this.xcord = x;
        this.ycord = y;
    }

    /**
     * Gets the x-coordinate of the vector.
     * @return The x-coordinate of the vector
     */
    public double getX() {
        return xcord;
    }

    /**
     * Gets the y-coordinate of the vector.
     * @return The y-coordinate of the vector
     */
    public double getY() {
        return ycord;
    }

    /**
     * Sets the x-coordinate of the vector.
     * @param x The x-coordinate to be set
     */
    public void setX(double x) {
        this.xcord = x;
    }

    /**
     * Sets the y-coordinate of the vector.
     * @param y The y-coordinate to be set
     */
    public void setY(double y) {
        this.ycord = y;
    }

    /**
     * Adds another GameVector to this GameVector.
     * @param vector the vector to be added to this GameVector
     */
    public void addVector(GameVector vector) {
        this.xcord += vector.getX();
        this.ycord += vector.getY();
    }
}
