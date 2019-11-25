package gamePackage;
import javax.swing.*;
import java.awt.*;

public class Game extends JFrame{

    public static Puck puck;

    public static void main(String[] args) throws InterruptedException{
        JFrame frame = new GameFrame();
        frame.setTitle("Game Window");
        frame.setSize(500, 800);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        GameVector position = new GameVector((frame.getSize().getWidth()) / 2 + 1, (frame.getSize().getWidth() / 2) + 1);
        GameVector zeroVector = new GameVector(0, 0);

        puck = new Puck(position, zeroVector, zeroVector);
        panel.add(puck);

        frame.setContentPane(panel);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        while(true){
            puck.repaint();
            Thread.sleep(10);
        }
    }

    static class GameFrame extends JFrame {
        @Override
        public void paint(Graphics g){
            super.paint(g);

            puck.paint(g);
            g.fillOval((int) puck.position.getX(), (int) puck.position.getY(), 50, 50);
        }
    }
}

