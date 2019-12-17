package gamepackage;

import field.Frame;
import javax.swing.JFrame;


public class Game extends JFrame {

    private static final long serialVersionUID = 4714318125998709253L;
    public static Puck puck;
    public static Frame frame;

    /**
     * Game Class main method.
     *
     * @param args The command line arguments.
     * @throws InterruptedException Checks if thread has been interrupted.
     */
    public static void main(String[] args) throws InterruptedException {

        GUI gui = new GUI();
        gui.titleScreen();

        puck = frame.getPuck();


        while (true) {
            puck.move(frame);
            Thread.sleep(10);
        }
    }


}

