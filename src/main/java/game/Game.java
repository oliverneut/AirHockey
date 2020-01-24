package game;

import basis.Puck;
import basis.ScoreCount;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class Game extends JFrame {

    private static final long serialVersionUID = 5985568796687L;

    public static ArrayList<Puck> puck;
    public static Frame frame;

    public static MatchSocketHandler wsHandler;

    /**
     * Game Class main method.
     *
     * @param args The command line arguments.
     * @throws InterruptedException Checks if thread has been interrupted.
     */
    public static void main(String[] args) throws InterruptedException {

        runGame(1);
    }

    /**
     * This method allows the game to be run externally from the method as well.
     * @param mode dictates what game mode will be used.
     * @throws InterruptedException Checks if thread has been interrupted.
     */
    public static Frame runGame(int mode) throws InterruptedException {
        try {
            frame = new Frame(mode);
            frame.setVisible(true);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            wsHandler = MatchSocketHandler.initialize(frame);

            puck = frame.getPucks();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }

        Boolean cont = true;
        while (cont) {
            cont = movePuck();
            Thread.sleep(10);
        }
        return frame;
    }

    /**
     * Moves the puck for the runGame method.
     * @return a boolean if the game should be over.
     * @throws InterruptedException when the thread was interrupted.
     */
    public static boolean movePuck() throws InterruptedException {
        int score = 0;
        for (Puck value : puck) {
            value.move(frame);
            if (ScoreCount.getInstance().getPlayer1() > score) {
                ScoreCount.getInstance().winOne();
                frame.repaint();
                Thread.sleep(5000);
                frame.setVisible(false);
                ScoreCount.getInstance().resetScore();
                return false;
            } else if (ScoreCount.getInstance().getPlayer2() > score) {
                ScoreCount.getInstance().winTwo();
                Thread.sleep(5000);
                frame.setVisible(false);
                ScoreCount.getInstance().resetScore();
                return false;
            }
        }
        return true;
    }
}
