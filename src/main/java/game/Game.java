package game;

import basis.Puck;
import basis.Scores;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class Game extends JFrame {

    private static final long serialVersionUID = 5985568796687L;

    public static ArrayList<Puck> puck;
    public static Frame frame;
    public static JFrame loginScreenT;
    public static JTextField username;
    public static JTextField password;
    public static JButton button;
    public static boolean login = false;
    public static Scores score;

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
    public static void runGame(int mode) throws InterruptedException {
        try {
            frame = new Frame(mode);
            frame.setVisible(true);
            frame.setResizable(false);
            score = frame.getScore();

            client = MatchSocketHandler.initialize(serverUrl, frame);

            puck = frame.getPucks();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }

        while (true) {
            for (Puck value : puck) {
                value.move(frame, score);
            }
            Thread.sleep(10);
        }
    }
}

