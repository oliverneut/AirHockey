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
     * @param width  the width of the box.
     */

    public Rectangle(int xcord, int ycord, int height, int width) {
        this.xcord = xcord;
        this.ycord = ycord;
        this.height = height;
        this.width = width;
    }

    /**
     * Fetches this rectangles x coordinate.
     *
     * @return the x coordinate.
     */
    public int getXcord() {
        return (int) this.xcord;
    }

    /**
     * Fetches this rectangles y coordinate.
     *
     * @return the y coordinate.
     */
    public int getYcord() {
        return (int) this.ycord;
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
        double xmin1 = this.xcord;
        double xmax1 = this.xcord + this.width;
        double ymin1 = this.ycord;
        double ymax1 = this.ycord + this.height;

        //the other rectangles coordinates
        double xmin2 = r.getXcord();
        double xmax2 = r.getXcord() + r.getWidth();
        double ymin2 = r.getYcord();
        double ymax2 = r.getYcord() + r.getHeight();

        boolean xaxis = xmax1 >= xmin2 && xmax2 >= xmin1;
        boolean yaxis = ymax1 >= ymin2 && ymax2 >= ymin1;

        return xaxis && yaxis;
    }
}
