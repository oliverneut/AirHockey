package field;

/**
 * This class contains a collision box for cleaner code in "Field.java".
 */
public class Rectangle {
    private transient double xpos;
    private transient double ypos;
    private transient double height;
    private transient double width;

    /**
     * Creates the to be used rectangle.
     *
     * @param x      the x coordinate of the box.
     * @param y      the y coordinate of a box.
     * @param height the height of the box.
     * @param width  the width of the box.
     */
    public Rectangle(double x, double y, double height, double width) {
        this.xpos = x;
        this.ypos = y;
        this.height = height;
        this.width = width;
    }

    /**
     * Fetches this rectangles x coordinate.
     *
     * @return the x coordinate.
     */
    public int getX() {
        return (int) this.xpos;
    }

    /**
     * Fetches this rectangles y coordinate.
     *
     * @return the y coordinate.
     */
    public int getY() {
        return (int) this.ypos;
    }

    /**
     * Fetches this rectangles height.
     *
     * @return the height.
     */
    public int getHeight() {
        return (int) this.height;
    }

    /**
     * Fetches this rectangles width.
     *
     * @return the width.
     */
    public int getWidth() {
        return (int) this.width;
    }

    /**
     * Calculates weather or not 2 boxes intersect.
     *
     * @param r the box to check with.
     * @return if the boxes intersect or not.
     */
    public boolean intersects(Rectangle r) {
        //this rectangles coordinates
        double xmin1 = this.xpos;
        double xmax1 = this.xpos + this.width;
        double ymin1 = this.ypos;
        double ymax1 = this.ypos + this.height;

        //the other rectangles coordinates
        double xmin2 = r.getX();
        double xmax2 = r.getX() + r.getWidth();
        double ymin2 = r.getY();
        double ymax2 = r.getY() + r.getHeight();

        boolean xaxis = xmax1 >= xmin2 && xmax2 >= xmin1;
        boolean yaxis = ymax1 >= ymin2 && ymax2 >= ymin1;

        return xaxis && yaxis;
    }
}
