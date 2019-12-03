package field;

/**
 * This class contains a collision box for cleaner code in "Field.java".
 */
public class Rectangle {
    private transient int xcord;
    private transient int ycord;
    private transient int height;
    private transient int width;

    /**
     * Creates the to be used rectangle.
     * @param xcord the x coordinate of the box.
     * @param ycord the y coordinate of a box.
     * @param height the height of the box.
     * @param width the width of the box.
     */
    public Rectangle(int xcord, int ycord, int height, int width) {
        this.xcord = xcord;
        this.ycord = ycord;
        this.height = height;
        this.width = width;
    }

    /**
     * Fetches this rectangles x coordinate.
     * @return the x coordinate.
     */
    public int getXcord() {
        return this.xcord;
    }

    /**
     * Fetches this rectangles y coordinate.
     * @return the y coordinate.
     */
    public int getYcord() {
        return this.ycord;
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
