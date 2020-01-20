package game;

import basis.Puck;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import basis.ScoreCount;
import org.eclipse.jetty.websocket.client.WebSocketClient;

@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class Game extends JFrame {

    private static final long serialVersionUID = 5985568796687L;

    public static ArrayList<Puck> puck;
    public static Frame frame;

    public static String serverUrl = "ws://localhost:6969/match";

    public static WebSocketClient client;

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

            client = MatchSocketHandler.initialize(serverUrl, frame);

            puck = frame.getPucks();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }

        while (true) {
            int score = 4;
            for (Puck value : puck) {
                value.move(frame);
                if(ScoreCount.getInstance().getPlayer1() > score) {
                    ScoreCount.getInstance().winOne();
                    frame.repaint();
                    Thread.sleep(5000);
                    frame.setVisible(false);
                    ScoreCount.getInstance().resetScore();
                    return frame;
                } else if(ScoreCount.getInstance().getPlayer2() > score) {
                    ScoreCount.getInstance().winTwo();
                    Thread.sleep(5000);
                    frame.setVisible(false);
                    ScoreCount.getInstance().resetScore();
                    return frame;
                }
            }
            Thread.sleep(10);
        }
    }
}
