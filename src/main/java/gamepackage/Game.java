package gamepackage;

import java.awt.FlowLayout;
import java.awt.Graphics;
import javax.swing.JFrame;


public class Game extends JFrame {

    public static Puck puck;
    public static GameFrame frame;

    /**
     * Game Class main method.
     * @param args The command line arguments.
     * @throws InterruptedException Checks if thread has been interrupted.
     */
    public static void main(String[] args) throws InterruptedException {
        frame = new GameFrame();
        frame.setTitle("Game Window");
        frame.setSize(500, 800);

        //Determine the starting position of the Puck.
        GameVector position = new GameVector((frame.getSize().getWidth()) / 2 + 1,
                (frame.getSize().getHeight() / 2) + 1);
        GameVector velocity = new GameVector(10.0, 10.0);
        puck = new Puck(position, velocity);
        puck.setLayout(new FlowLayout());

        //Initialize the frame.
        frame.setContentPane(puck);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        while (true) {
            puck.move(frame);
            Thread.sleep(10);
        }
    }

    /**
     * Creates the main frame where the game will take place.
     */
    public static class GameFrame extends JFrame {
        @Override
        public void paint(Graphics g) {
            super.paint(g);

            puck.paint(g);
            g.fillOval((int) puck.position.getX(), (int) puck.position.getY(), 50, 50);
        }
    }
}

