package gamepackage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GUI {

    public static JFrame frame = new JFrame("Air hockey");
    private static JTextField userName;
    private static JPasswordField passWord;

    public GUI(){
        createFrame();
    }

    public static void createFrame(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);

    }

    /**
     * Creates the titlescreen which has a login button
     */
    public static void titleScreen(){
        JPanel panelTop = new JPanel();
        JPanel panelCenter = new JPanel();
        JPanel panelBottom = new JPanel();
        JLabel label = new JLabel("AIR HOCKEY");
        label.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
        JLabel icon = new JLabel("");
        try {
            BufferedImage bi = ImageIO.read(new File("src/main/java/assets/airhockeyicon.png"));
            icon.setIcon(new ImageIcon(bi));
        }catch(IOException e){
            e.printStackTrace();
        }

        JButton loginButton = new JButton("login");
        JButton registerButton = new JButton("register");


        panelTop.add(label);
        panelCenter.add(icon);
        panelBottom.add(loginButton);
        panelBottom.add(registerButton);


        panelTop.setBackground(Color.white);
        panelCenter.setBackground(Color.white);
        panelBottom.setBackground(Color.white);

        frame.getContentPane().add(BorderLayout.NORTH, panelTop);
        frame.getContentPane().add(BorderLayout.CENTER, panelCenter);
        frame.getContentPane().add(BorderLayout.SOUTH, panelBottom);

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearScreen();
                loginScreen();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearScreen();
                registerScreen();
            }
        });

    }

    public static void clearScreen(){
        frame.getContentPane().removeAll();
        frame.repaint();
    }

    public static void loginScreen(){

        JPanel mainPanel = new JPanel();
        JPanel titlePanel = new JPanel();
        JPanel userNamePanel = new JPanel();
        JPanel passWordPanel = new JPanel();
        JPanel buttonPanel = new JPanel();


        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        userNamePanel.setLayout(new BoxLayout(userNamePanel, BoxLayout.X_AXIS));
        passWordPanel.setLayout(new BoxLayout(passWordPanel, BoxLayout.X_AXIS));

        JLabel login = new JLabel("LOGIN");
        login.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
        JLabel u = new JLabel("Username : ");
        JLabel p = new JLabel("Password : ");
        JButton loginButton = new JButton("login");

        userName = new JTextField(20);
        passWord = new JPasswordField(20);

        userName.setMaximumSize(new Dimension(200,20));
        passWord.setMaximumSize(new Dimension(200,20));
        userNamePanel.setMaximumSize(new Dimension(300,20));
        passWordPanel.setMaximumSize(new Dimension(300,20));
        titlePanel.setMaximumSize(new Dimension(400,300));
        titlePanel.add(login);
        userNamePanel.add(u);
        passWordPanel.add(p);
        userNamePanel.add(userName);
        passWordPanel.add(passWord);
        buttonPanel.add(loginButton);

        mainPanel.add(titlePanel);
        mainPanel.add(userNamePanel);
        mainPanel.add(passWordPanel);
        mainPanel.add(buttonPanel);

        frame.getContentPane().add(mainPanel);

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(checkCredentials(getUserName(), getPassWord())){
                    clearScreen();
                    menu();
                }
                else{
                    JDialog errorMessage = new JDialog(frame,"Error");
                    errorMessage.add(new JLabel("You have entered the wrong credentials"));
                    errorMessage.setSize(new Dimension(250,100));
                    errorMessage.setVisible(true);
                    errorMessage.setLocationRelativeTo(null);
                }
            }
        });

    }

    public static void registerScreen(){
        JPanel mainPanel = new JPanel();
        JPanel b = new JPanel();
        JPanel c = new JPanel();
        JPanel d = new JPanel();

        b.setLayout(new BoxLayout(b, BoxLayout.X_AXIS));
        c.setLayout(new BoxLayout(c, BoxLayout.X_AXIS));
        d.setLayout(new BoxLayout(d, BoxLayout.X_AXIS));

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JLabel u = new JLabel("User name : ");
        JLabel p = new JLabel("Password : ");
        userName = new JTextField();
        passWord = new JPasswordField();
        JButton registerButton = new JButton("register");

        userName.setMaximumSize(new Dimension(200,20));
        passWord.setMaximumSize(new Dimension(200,20));

        b.setMaximumSize(new Dimension(400,200));
        c.setMaximumSize(new Dimension(400,200));
        d.setMaximumSize(new Dimension(400,200));

        b.add(u);
        b.add(userName);
        c.add(p);
        c.add(passWord);
        d.add(registerButton);
        mainPanel.add(b);
        mainPanel.add(c);
        mainPanel.add(d);

        frame.getContentPane().add(mainPanel);

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearScreen();
                menu();
            }
        });

    }

    public static void menu(){
        JPanel mainPanel = new JPanel();

        JButton friendList = new JButton("FRIENDLIST");
        JButton leaderBoard = new JButton("LEADERBOARD");
        JButton play = new JButton("PLAY GAME");
        JButton logout = new JButton("LOG OUT");

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        mainPanel.add(friendList);
        mainPanel.add(leaderBoard);
        mainPanel.add(play);
        mainPanel.add(logout);

        frame.add(mainPanel);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        friendList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearScreen();
            }
        });

        leaderBoard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearScreen();
            }
        });

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearScreen();
            }
        });

        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearScreen();
                titleScreen();
            }
        });





    }

    public static boolean checkCredentials(String userName, String passWord){
        if(userName.equals("user") && passWord.equals("user")){
            return true;
        }
        return false;
    }


    public static String getUserName(){
        return userName.getText();
    }

    public static String getPassWord(){
        return passWord.getText();
    }

}
