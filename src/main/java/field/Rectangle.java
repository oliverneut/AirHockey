package field;

/**
 * This class contains a collision box for cleaner code in "Field.java".
 */
public class Rectangle {
    private transient int x;
    private transient int y;
    private transient int height;
    private transient int width;

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

    public boolean Intersects(Rectangle r) {
        //this rectangles coordinates
        int xmin1 = this.x;
        int xmax1 = this.x + this.width;
        int ymin1 = this.y;
        int ymax1 = this.y + this.height;

        //the other rectangles coordinates
        int xmin2 = r.getX();
        int xmax2 = r.getX() + r.getWidth();
        int ymin2 = r.getY();
        int ymax2 = r.getY() + r.getHeight();

        boolean xAxis = xmax1 >= xmin2 && xmax2 >= xmin1;
        boolean yAxis = ymax1 >= ymin2 && ymax2 >= ymin1;

        return xAxis && yAxis;
    }
}
