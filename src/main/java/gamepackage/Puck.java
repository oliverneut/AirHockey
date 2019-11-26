package gamepackage;

import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * Class which defines a Puck.
 */
public class Puck extends JPanel {
    protected transient GameVector position;
    protected transient GameVector velocity;

    /**
     * Initializes the puck for the game.
     * @param position The starting position of the puck
     * @param velocity The starting velocity of the puck
     */
    public Puck(GameVector position, GameVector velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillOval((int) this.position.getX(), (int) this.position.getY(), 50, 50);
    }

    /**
     * Moves the puck.
     * @param frame The frame where the game takes place
     */
    public void move(JFrame frame) {
        //Set new position according to velocity.
        position.addVector(velocity);

        wallCollision(frame);

        repaint();

    }

    /**
     * Gets the position of the puck.
     * @return The position of the puck
     */
    public GameVector getPosition() {
        return position;
    }

    /**
     * Sets the position of the puck.
     * @param position The new position of the puck
     */
    public void setPosition(GameVector position) {
        this.position = position;
    }

    /**
     * Gets the velocity of the puck.
     * @return The velocity of the puck
     */
    public GameVector getVelocity() {
        return velocity;
    }

    /**
     * Handles the collision with a wall.
     * @param frame The frame where the game takes place
     */
    private void wallCollision(JFrame frame) {
        if (position.getY() < 0) {
            position.setY(0);
            velocity.setY(velocity.getY() * -1);
        } else if (position.getX() < 0) {
            position.setX(0);
            velocity.setX(velocity.getX() * -1);
        } else if (position.getY() > frame.getHeight() - 80) {
            position.setY(frame.getHeight() - 80);
            velocity.setY(velocity.getY() * -1);
        } else if (position.getX() > frame.getWidth() - 60) {
            position.setX(frame.getWidth() - 60);
            velocity.setX(velocity.getX() * -1);
        } else {
            velocity.setX(velocity.getX() * 0.992);
            velocity.setY(velocity.getY() * 0.992);
        }
    }
}
