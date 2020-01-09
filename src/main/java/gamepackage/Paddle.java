package gamepackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

/**
 * Class which defines a Paddle.
 */
public class Paddle extends MovingEntity implements MouseMotionListener {
    private static final long serialVersionUID = 59692986L;

    /**
     * Initializes a player paddle object for the game.
     *
     * @param position The starting position of the paddle
     * @param velocity The velocity of the paddle
     * @param id       The id of the paddle to identify the player
     * @param height   The height of the paddle
     * @param width    The width of the paddle
     */
    public Paddle(GameVector position, GameVector velocity, int id, int height, int width) {
        this.setPosition(position);
        this.setVelocity(velocity);
        this.setId(id);
        this.setHeight(height);
        this.setWidth(width);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval((int) this.position.getX(), (int) this.position.getY(), getWidth(), getHeight());
    }

    /**
     * Calculates the new direction of the puck after a collision with a paddle.
     *
     * @param x            The x position of the puck
     * @param y            The y position of the puck
     * @param puckVelocity The velocity of the puck
     * @return The new Velocity of the puck
     */
    public GameVector getBounceDirection(double x, double y, GameVector puckVelocity) {

        //Calculate length and direction of the vector
        // of the hitting point perpendicular to the paddle
        double middleXDirection = position.getX() - x;
        double middleYDirection = position.getY() - y;
        double middleLength = Math.sqrt(Math.pow(middleYDirection, 2)
                + Math.pow(middleXDirection, 2));

        //Calculate normals of the perpendicular vector and the velocity of the puck
        GameVector middleNormal = new GameVector(middleXDirection / middleLength,
                middleYDirection / middleLength);
        double puckLength = Math.sqrt(Math.pow(puckVelocity.getX(), 2)
                + Math.pow(puckVelocity.getY(), 2));
        GameVector puckNormal = new GameVector(puckVelocity.getX() / puckLength,
                puckVelocity.getY() / puckLength);

        //Calculate angles between the vectors
        double cosine = middleNormal.dot(puckNormal);
        double sine = Math.sin(Math.acos(cosine));

        //Calculate new direction and magnitude of the puck according to rotation matrix
        double reflectedX = puckVelocity.getX() * cosine + puckVelocity.getY() * sine;
        double reflectedY = -puckVelocity.getY() * cosine - puckVelocity.getX() * sine;
        double newMagnitude = puckLength / Math.sqrt(Math.pow(reflectedX, 2)
                + Math.pow(reflectedY, 2));
        return new GameVector(newMagnitude * reflectedX, newMagnitude * reflectedY);
    }



    /**
     * Moves the paddle when the cursor moves.
     *
     * @param ev The MouseEvent of the event
     */
    public void mouseMoved(MouseEvent ev) {
        this.setVelocity(new GameVector(
                position.getX() - ev.getX(), position.getY() - ev.getY()));
        this.position = new GameVector(ev.getX(), ev.getY());
    }

    /**
     * Moves the paddle when the cursor is dragged.
     *
     * @param ev The MouseEvent of the event
     */
    public void mouseDragged(MouseEvent ev) {
        this.setVelocity(new GameVector(
                position.getX() - ev.getX(), position.getY() - ev.getY()));
        this.position = new GameVector(ev.getX(), ev.getY());
    }
}
