package field;

import gamepackage.Puck;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * This class creates a field to play on.
 */
public class Field extends JPanel {

    // Define serialization id to avoid serialization related bugs
    public static final long serialVersionUID = 4328743;

    private transient Puck puck;
    private static Image fieldImage;
    private static Color myColor = new Color(0, 255,0, 127);
    private static ArrayList<Rectangle> r = new ArrayList<Rectangle>();

    /**
     * Initiates the Drawing of a field.
     * @param d the dimenions of a given field.
     */
    public Field(Dimension d, Puck p) {
        this.puck = p;
        createField();
        createRectangle(d);
    }

    /**
     * Retrieves the image from the assets folder.
     */
    public final void getImage() {
        fieldImage = new ImageIcon("src/main/java/assets/airHockey.png").getImage();
    }

    /**
     * Sets the image and its preferred size.
     */
    private final void createField() {
        getImage();
        int w = fieldImage.getWidth(this);
        int h = fieldImage.getHeight(this);
        setPreferredSize(new Dimension(h, w));
    }

    /**
     * Creates the bounding boxes for collision.
     * @param d the dimensions of the containing frame.
     */
    private final void createRectangle(Dimension d) {
        this.r.add(new Rectangle(0, 0, 11, d.width));
        this.r.add(new Rectangle(d.width - 28, 0, d.height, 13));
        this.r.add(new Rectangle(0, d.height - 51, 13, d.width));
        this.r.add(new Rectangle(0, 0, d.height, 9));
    }

    /**
     * Overrides the draw method and draws the desired objects.
     * @param g the object that actually draws, given by the library.
     */
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(fieldImage, 0, 0, null);
        g.setColor(myColor);
        for (int i = 0; i < r.size(); i++) {
            g.fillRect(r.get(i).getX(), r.get(i).getY(), r.get(i).getWidth(), r.get(i).getHeight());
        }
        g.setColor(new Color(0, 0,0, 255));
        puck.paint(g);
    }

    /**
     * this method makes gets the boxes for collision.
     * @return the bounding boxes.
     */
    public ArrayList<Rectangle> getBoundBoxes() {
        return this.r;
    }
}
