package Field;

import javax.swing.JFrame;

/**
 * This class creates the frame to draw everything in.
 */
public class Frame extends JFrame{

    /**
     * This method creates a new frame and initiates the necessary methods to draw everything.
     */
    public Frame() {
        createFrame();
    }

    /**
     * This method creates the frame for the window and then draws everything in.
     */
    private void createFrame() {
        setSize(320, 640);
        add(new Field(this.size()));
        setTitle("Board One");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
