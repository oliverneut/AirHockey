package gamePackage;
import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {

    public static Puck puck;
    public static GameFrame frame;

    public static void main(String[] args) throws InterruptedException {
        frame = new GameFrame();
        frame.setTitle("Game Window");
        frame.setSize(500, 800);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        GameVector position = new GameVector((frame.getSize().getWidth() / 2) + 1, (frame.getSize().getWidth() / 2) + 1);
        GameVector zeroVector = new GameVector(0, 0);
        puck = new Puck(position, zeroVector, zeroVector);
        panel.add(puck);

        frame.setContentPane(panel);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        while(true) {
            Game.move(new GameVector(10.0, 10.0));
            frame.repaint();
            Thread.sleep(50);
        }
    }

    public static void move(GameVector vector) {

        puck.acceleration.addVector(vector);
        puck.velocity.addVector(puck.acceleration);
        puck.position.addVector(puck.velocity);

        puck.acceleration.setX(0);
        puck.acceleration.setY(0);

        if(puck.position.getY() < 0){
            puck.position.setY(0);
            puck.velocity.setY(puck.velocity.getY() * -1);
        }

        if(puck.position.getX() < 0){
            puck.position.setX(0);
            puck.velocity.setX(puck.velocity.getX() * -1);
        }
        if(puck.position.getY() > frame.getHeight() - 50){
            puck.position.setY(frame.getHeight() - 50);
            puck.velocity.setY(puck.velocity.getY() * -1);
        }
        if(puck.position.getX() > frame.getWidth() - 50){
            puck.position.setX(frame.getWidth() - 50);
        }
    }

    static class GameFrame extends JFrame {
        @Override
        public void paint(Graphics g) {
            super.paint(g);

            puck.paint(g);
            g.fillOval((int) puck.position.getX(), (int) puck.position.getY(), 50, 50);
        }
    }
}

