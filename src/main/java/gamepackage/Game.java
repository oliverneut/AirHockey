package gamepackage;

import java.awt.FlowLayout;
import java.awt.Graphics;
import javax.swing.JFrame;

import field.Frame;


public class Game extends JFrame {

    public static Puck puck;
    public static Frame frame;
    public static Board board;
    private static final long serialVersionUID = 4714318125998709253L;

    /**
     * Game Class main method.
     * @param args The command line arguments.
     * @throws InterruptedException Checks if thread has been interrupted.
     */
    public static void main(String[] args) throws InterruptedException {
        frame = new Frame();
        frame.setVisible(true);
        frame.setResizable(false);

        puck = frame.getPuck();


        while (true) {
            puck.move(frame);
            Thread.sleep(10);
        }
    }

    /**
     * Creates the main frame where the game will take place.
     */
    public static class GameFrame extends JFrame {
        private static final long serialVersionUID = 4714318109253L;

        @Override
        public void paint(Graphics g) {
            super.paint(g);

            puck.paint(g);
            g.fillOval((int) puck.position.getX(), (int) puck.position.getY(), 50, 50);
        }

        /**
         * Gets the game board.
         * @return the game board.
         */
        public Board getBoard() {
            return (Board) getContentPane();
        }
    }
}

