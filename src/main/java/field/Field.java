package field;

import gamepackage.Puck;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * This class creates a field to play on.
 */
public class Field extends JPanel {

    // Define serialization id to avoid serialization related bugs
    public static final long serialVersionUID = 4328743;

    private transient ArrayList<Puck> puck;
    private static Image fieldImage;
    private static Color myColor = new Color(0, 255,0, 0);
    private static ArrayList<Rectangle> r = new ArrayList<Rectangle>();
    private static ArrayList<Rectangle> goals = new ArrayList<>();
    private transient int mode;

    /**
     * Initiates the Drawing of a field.
     * @param p the given puck to draw.
     */
    public Field(ArrayList<Puck> p, int mode) {
        this.puck = p;
        this.mode = mode;
        createField();
        try {
            createBoundingBoxes();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    /**
     * Retrieves the image from the assets folder.
     */
    public final void getImage() {
        fieldImage = new ImageIcon("src/main/java/assets/"+ this.mode + ".png").getImage();
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
     * Reads the given board file to create the necessary bounding boxes.
     * @throws FileNotFoundException When the file given could not be found.
     */
    private final void createBoundingBoxes() throws FileNotFoundException {
        File file = new File("src/main/java/assets/boards/" + mode + ".txt");
        Scanner sc = new Scanner(file);
        double n = sc.nextDouble();
        double m = sc.nextDouble();
        for (int i = 0; i < n; i++) {
            this.r.add(new Rectangle(sc.nextDouble(), sc.nextDouble(),
                    sc.nextDouble(), sc.nextDouble()));
        }
        for (int i = 0; i < m; i++) {
            this.goals.add(new Rectangle(sc.nextDouble(), sc.nextDouble(),
                    sc.nextDouble(), sc.nextDouble()));
        }
        sc.close();
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
            g.fillRect(r.get(i).getX(), r.get(i).getY(),
                    r.get(i).getWidth(), r.get(i).getHeight());
        }
        g.setColor(new Color(255, 0, 0, 127));
        for (int i = 0; i < goals.size(); i++) {
            g.fillRect(goals.get(i).getX(), goals.get(i).getY(),
                    goals.get(i).getWidth(), goals.get(i).getHeight());
        }
        g.setColor(new Color(0, 0,0, 255));
        for(int i = 0; i < puck.size(); i++) {
            puck.get(i).paint(g);
        }
    }

    /**
     * this method makes gets the boxes for collision.
     * @return the bounding boxes.
     */
    public ArrayList<Rectangle> getBoundBoxes() {
        return this.r;
    }

    /**
     * Returns the goals on a given map.
     * @return the given maps goals.
     */
    public ArrayList<Rectangle> getGoals() {
        return this.goals;
    }
}
