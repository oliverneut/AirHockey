package gamepackage;

import field.Frame;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import org.eclipse.jetty.websocket.client.WebSocketClient;

public class Game extends JFrame {

    private static final long serialVersionUID = 5985568796687L;

    public static ArrayList<Puck> puck;
    public static Frame frame;
    public static JFrame loginScreenT;
    public static JTextField username;
    public static JTextField password;
    public static JButton button;
    public static boolean login = false;

    public static String serverUrl = "ws://localhost:6969/match";

    public static WebSocketClient client;

    /**
     * Game Class main method.
     *
     * @param args The command line arguments.
     * @throws InterruptedException Checks if thread has been interrupted.
     */
    public static void main(String[] args) throws InterruptedException {

        try {
            frame = new Frame(1);
            frame.setVisible(true);
            frame.setResizable(false);

            client = MatchSocketHandler.initialize(serverUrl, frame);

            puck = frame.getPucks();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }

        while (true) {
            for (int i = 0; i < puck.size(); i++) {
                puck.get(i).move(frame);
            }
            Thread.sleep(10);
        }
    }
}

