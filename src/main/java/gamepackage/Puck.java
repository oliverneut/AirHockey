package gamepackage;

import field.Frame;
import field.Rectangle;

import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;


/**
 * Class which defines a Puck.
 */
public class Puck extends JPanel {
    private static final long serialVersionUID = 5985568796987L;

    protected transient GameVector position;
    protected transient GameVector velocity;
    private transient int multiplier;
    private transient int size;

    /**
     * Initializes the puck for the game.
     * @param position The starting position of the puck
     * @param velocity The starting velocity of the puck
     */
    public Puck(GameVector position, GameVector velocity, int size, int multiplier) {
        this.position = position;
        this.velocity = velocity;
        this.size = size;
        this.multiplier = multiplier;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        System.out.println("x: " + this.position.getX() + " y: " + this.position.getY() + " size: " + this.size);
        g.fillOval((int) this.position.getX(), (int) this.position.getY(), size, size);
    }

    /**
     * Moves the puck.
     * @param frame The frame where the game takes place
     */
    public void move(field.Frame frame) {
        //Set new position according to velocity.
        position.addVector(velocity);

        if (frame != null) {
            goalCollision(frame);

            wallCollision(frame);

            double distance = frame.getPaddle().intersects(position, this.size / 2);
            if (distance <= 0) {
                distance = -distance;
                this.position = frame.getPaddle()
                        .setBack(this.position, this.getVelocity(), distance);
                paddleCollision(frame);
                this.velocity.addVector(new GameVector(
                        frame.getPaddle().velocity.getX()/2, frame.getPaddle().velocity.getY()/2));

            }

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
    private void wallCollision(Frame frame) {
        ArrayList<Rectangle> boxes =  frame.getBoundingBoxes();
        if (position.getY() < (boxes.get(0).getYcord() + boxes.get(0).getHeight())) {
            position.setY(boxes.get(0).getYcord() + boxes.get(0).getHeight());
            velocity.setY(velocity.getY() * (-1 * multiplier));
        } else if (position.getX() < (boxes.get(3).getXcord() + boxes.get(3).getWidth())) {
            position.setX(boxes.get(3).getXcord() + boxes.get(3).getWidth());
            velocity.setX(velocity.getX() * (-1 * multiplier));
        } else if (position.getY() > (boxes.get(2).getYcord() - boxes.get(2).getHeight() - 36)) {
            position.setY(boxes.get(2).getYcord() - boxes.get(2).getHeight() - 36);
            velocity.setY(velocity.getY() * (-1 * multiplier));
        } else if (position.getX() > (boxes.get(1).getXcord() - boxes.get(1).getWidth() - 28)) {
            position.setX(boxes.get(1).getXcord() - boxes.get(1).getWidth() - 28);
            velocity.setX(velocity.getX() * (-1 * multiplier));
        } else {
            velocity.setX(velocity.getX() * (0.992 * multiplier));
            velocity.setY(velocity.getY() * (0.992 * multiplier));
        }
    }

    /**
     * Checks for collisions with the goal so that there can be a score.
     * @param frame the given frame of the game.
     */
    private void goalCollision(Frame frame) {
        ArrayList<Rectangle> goals = frame.getGoals();

        if (position.getY() < (goals.get(0).getYcord() + goals.get(0).getHeight())
                && position.getX() >= goals.get(0).getXcord()
                && position.getX() <= goals.get(0).getXcord() + goals.get(0).getWidth()) {
            System.out.println("AAAAAAAAAAAAAAAAAAAA");
        }

        if (position.getY() > (goals.get(1).getYcord() - goals.get(1).getHeight()  - 39)
                && position.getX() >= goals.get(1).getXcord()
                && position.getX() <= goals.get(1).getXcord() + goals.get(1).getWidth()) {
            System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBB");
        }
    }

    /**
     * Handles the collision with a paddle.
     * @param frame The frame where the game takes place
     */
    private void paddleCollision (field.Frame frame) {
        frame.getPucks().get(0).setVelocity(frame.getPaddle().getBounceDirection(
                position.getX(), position.getY(), getVelocity()));
    }
}
