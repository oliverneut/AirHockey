package Field;

/**
 * This class contains a collision box for cleaner code in "Field.java".
 */
public class Rectangle {
    private int x;
    private int y;
    private int height;
    private int width;

    /**
     * Creates the to be used rectangle.
     * @param x the x coordinate of the box.
     * @param y the y coordinate of a box.
     * @param height the height of the box.
     * @param width the width of the box.
     */
    public Rectangle(int x, int y, int height, int width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    /**
     * Fetches this rectangles x coordinate.
     * @return the x coordinate.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Fetches this rectangles y coordinate.
     * @return the y coordinate.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Fetches this rectangles height.
     * @return the height.
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Fetches this rectangles width.
     * @return the width.
     */
    public int getWidth() {
        return this.width;
    }
}
