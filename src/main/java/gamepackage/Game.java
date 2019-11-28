package gamepackage;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import field.Frame;


public class Game extends JFrame {

    public static Puck puck;
    public static Frame frame;
    public static Board board;
    public static JFrame loginScreen;
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
     * Creates the loginScreen where the user has to fill in his credentials
     */
    public static void loginScreen(){
        loginScreen = new JFrame();
        loginScreen.setTitle("Login Window");
        loginScreen.setSize(500, 800);
        loginScreen.getContentPane().setLayout(new FlowLayout());

        JLabel userName = new JLabel("Username : ");
        JLabel passWord = new JLabel("Password : ");

        username = new JTextField(30);
        password = new JTextField(30);
        button = new JButton("Start Game");

        loginScreen.getContentPane().add(userName);
        loginScreen.getContentPane().add(username);

        loginScreen.getContentPane().add(passWord);
        loginScreen.getContentPane().add(password);
        loginScreen.getContentPane().add(button);
        loginScreen.setVisible(true);
    }




    /**
     * Keeps checking if the button is pressed, until it is pressed.
     */
    public static void checkButton(){
        while(!login){
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    login = true;
                }
            });
        }
        loginScreen.setVisible(false);
    }
}

