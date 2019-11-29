package gamepackage;

import field.Field;
import field.Rectangle;

import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * Class which defines a Puck.
 */
public class Puck extends JPanel {
    private static final long serialVersionUID = 5985568796987L;

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
    public void move(field.Frame frame) {
        //Set new position according to velocity.
        position.addVector(velocity);

        if (frame != null) {
            wallCollision(frame);

            frame.repaint();
        }

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
     * Sets the velocity of the puck.
     * @param velocity The new velocity of the puck
     */
    public void setVelocity(GameVector velocity) {
        this.velocity = velocity;
    }

    /**
     * Handles the collision with a wall.
     * @param frame The frame where the game takes place
     */
    private void wallCollision(field.Frame frame) {
        ArrayList<Rectangle> boxes =  frame.getBoundingBoxes();
        if (position.getY() < (boxes.get(0).getY() + boxes.get(0).getHeight())) {
            position.setY(boxes.get(0).getY() + boxes.get(0).getHeight());
            velocity.setY(velocity.getY() * -1);
        } else if (position.getX() < (boxes.get(3).getX() + boxes.get(3).getWidth())) {
            position.setX(boxes.get(3).getX() + boxes.get(3).getWidth());
            velocity.setX(velocity.getX() * -1);
        } else if (position.getY() > (boxes.get(2).getY() - boxes.get(2).getHeight() - 36)) {
            position.setY(boxes.get(2).getY() - boxes.get(2).getHeight() - 36);
            velocity.setY(velocity.getY() * -1);
        } else if (position.getX() > (boxes.get(1).getX() - boxes.get(1).getWidth() - 28)) {
            position.setX(boxes.get(1).getX() - boxes.get(1).getWidth() - 28);
            velocity.setX(velocity.getX() * -1);
        } else {
            velocity.setX(velocity.getX() * 0.992);
            velocity.setY(velocity.getY() * 0.992);
        }
    }
}
