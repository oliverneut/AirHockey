package field;

import javax.swing.JFrame;

/**
 * This class creates the frame to draw everything in.
 */
public class Frame extends JFrame{

    // Define serialization id to avoid serialization related bugs
    public static final long serialVersionUID = 4328743;

    /**
     * This method creates a new frame and initiates the necessary methods to draw everything.
     */
    public Frame() {
        createNewFrame();
    }

    /**
     * This method creates the frame for the window and then draws everything in.
     */
    private void createNewFrame() {
        setSize(320, 640);
        add(new Field(this.size()));
        setTitle("Board One");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
