package field;

import gamepackage.GameVector;
import gamepackage.Puck;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 * This class creates the frame to draw everything in.
 */
public class Frame extends JFrame {

    // Define serialization id to avoid serialization related bugs
    public static final long serialVersionUID = 4328743;
    private transient Puck puck;
    private transient int width = 320;
    private transient int height = 640;
    private transient Field field;

    /**
     * This method creates a new frame and initiates the necessary methods to draw everything.
     */
    public Frame() {
        createPuck();
        createNewFrame();
    }

    /**
     * This method creates the frame for the window and then draws everything in.
     */
    private void createNewFrame() {
        setSize(this.width, this.height);
        this.field = new Field(this.size(), this.puck);
        add(field);
        setTitle("Board One");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void createPuck() {
        GameVector position = new GameVector((this.width) / 2 + 1,
                (this.height / 2) + 1);
        GameVector velocity = new GameVector(10.0, 10.0);
        this.puck = new Puck(position, velocity);
        //this.puck.setLayout(new FlowLayout());

        //board = new Board(puck);
    }

    /**
     * Method returns the puck.
     *
     * @return a getter for the made puck.
     */
    public Puck getPuck() {
        return this.puck;
    }

    /**
     * return the bounding boxes for the collisions.
     *
     * @return an array of collision boxes.
     */
    public ArrayList<Rectangle> getBoundingBoxes() {
        return this.field.getBoundBoxes();
    }
}
