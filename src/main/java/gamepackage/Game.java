package gamepackage;

import field.Frame;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;



public class Game extends JFrame {

    public static ArrayList<Puck> puck;
    public static Frame frame;
    public static Board board;
    public static JFrame loginScreenT;
    public static JTextField username;
    public static JTextField password;
    public static JButton button;
    public static boolean login = false;
    private static final long serialVersionUID = 4714318125998709253L;

    /**
     * Game Class main method.
     * @param args The command line arguments.
     * @throws InterruptedException Checks if thread has been interrupted.
     */
    public static void main(String[] args) throws InterruptedException {
        //Creates a simple login screen
        loginScreen();

        //Checks if the play button is pressed, only then it can move on to the game screen
        checkButton();

        frame = new Frame(1);
        frame.setVisible(true);
        frame.setResizable(false);

        puck = frame.getPucks();


        while (true) {
            for(int i = 0; i < puck.size(); i++) {
                puck.get(i).move(frame);
            }
            Thread.sleep(10);
        }
    }

    /**
     * Creates the loginScreen where the user has to fill in his credentials.
     */
    public static void loginScreen() {
        loginScreenT = new JFrame();
        loginScreenT.setTitle("Login Window");
        loginScreenT.setSize(500, 800);
        loginScreenT.getContentPane().setLayout(new FlowLayout());

        JLabel userName = new JLabel("Username : ");
        JLabel passWord = new JLabel("Password : ");

        username = new JTextField(30);
        password = new JTextField(30);
        button = new JButton("Start Game");

        loginScreenT.getContentPane().add(userName);
        loginScreenT.getContentPane().add(username);

        loginScreenT.getContentPane().add(passWord);
        loginScreenT.getContentPane().add(password);
        loginScreenT.getContentPane().add(button);
        loginScreenT.setVisible(true);
    }




    /**
     * Keeps checking if the button is pressed, until it is pressed.
     */
    public static void checkButton() {
        while (!login) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    login = true;
                }
            });
        }
        loginScreenT.setVisible(false);
    }
}

