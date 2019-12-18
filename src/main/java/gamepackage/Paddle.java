package gamepackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

/**
 * Class which defines a Paddle.
 */
public class Paddle extends JPanel implements MouseMotionListener {
    private static final long serialVersionUID = 59692986L;

    protected transient GameVector position;
    protected transient GameVector velocity;
    protected transient int id;

    private transient int width;
    private transient int height;

    /**
     * Initializes a player paddle object for the game.
     * @param position The starting position of the paddle
     * @param velocity The velocity of the paddle
     * @param id The id of the paddle to identify the player
     * @param height The height of the paddle
     * @param width The width of the paddle
     */
    public Paddle(GameVector position, GameVector velocity, int id, int height, int width) {
        this.position = position;
        this.velocity = velocity;
        this.id = id;
        this.height = height;
        this.width = width;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLUE);
        g.fillOval((int) this.position.getX(), (int) this.position.getY(), width, height);
    }

    /**
     * Checks whether there is an intersection with the paddle.
     * @param pos The position of the puck
     * @param radius The radius of the puck
     * @return positive value when there is no intersection, negative or 0 otherwise.
     */
    public double intersects(GameVector pos, double radius) {
        double paddleX = this.position.getX() + (double) width / 2;
        double paddleY = this.position.getY() + (double) height / 2;
        double puckX = pos.getX() + radius;
        double puckY = pos.getY() + radius;
        double paddleRadius = this.width / 2;

        double distance = Math.sqrt(Math.pow(puckX - paddleX, 2)
                + Math.pow(puckY - paddleY, 2));
        return distance - (radius + paddleRadius);
    }

    /**
     * Determines the new position of the puck when it collides with the paddle.
     * @param pos The position of the puck
     * @param puckVelocity The velocity of the puck
     * @param distance The distance between the puck and the paddle
     * @returns the new position of the puck
     */
    //Warning suppressed, since PMD detects the used variables originalX,
    //originalY and puckNormal as unused.
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public GameVector setBack(GameVector pos, GameVector puckVelocity, double distance) {
        if (this.velocity.getX() == 0 && this.velocity.getY() == 0) {
            double originalX = pos.getX();
            double originalY = pos.getY();
            double puckLength = Math.sqrt(Math.pow(puckVelocity.getX(), 2)
                    + Math.pow(puckVelocity.getY(), 2));

            GameVector puckNormal = new GameVector(-puckVelocity.getX() / puckLength,
                    -puckVelocity.getY() / puckLength);

            double pythagorean = 0;
            while (pythagorean <= distance) {
                pos.addVector(puckNormal);
                pythagorean = Math.sqrt(Math.pow(pos.getX() - originalX, 2)
                        + Math.pow(pos.getY() - originalY, 2));
            }
            return pos;
        }
        GameVector negativeVelocity = new GameVector(-velocity.getX(), -velocity.getY());
        pos.addVector(negativeVelocity);
        velocity = negativeVelocity;
        return pos;
    }

    /**
     * Calculates the new direction of the puck after a collision with the paddle.
     * @param x The x position of the puck
     * @param y The y position of the puck
     * @param puckVelocity The velocity of the puck
     * @return The new Velocity of the puck
     */
    public GameVector getBounceDirection(double x, double y, GameVector puckVelocity) {

        //Calculate length and direction of the vector
        // of the hitting point perpendicular to the paddle
        double middleXDirection = this.position.getX() - x;
        double middleYDirection = this.position.getY() - y;
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
     * Gets the position of the paddle.
     * @return The position of the paddle
     */
    public GameVector getPosition() {
        return this.position;
    }

    /**
     * Sets the position of the paddle.
     * @param position The new position of the paddle
     */
    public void setPosition(GameVector position) {
        this.position = position;
    }

    /**
     * Gets the velocity of the paddle.
     * @return The velocity of the paddle
     */
    public GameVector getVelocity() {
        return this.velocity;
    }

    /**
     * Sets the velocity of the paddle.
     * @param velocity The new velocity of the paddle
     */
    public void setVelocity(GameVector velocity) {
        this.velocity = velocity;
    }

    /**
     * Gets the id of the paddle.
     * @return The new id of the paddle
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the paddle.
     * @param id The new id of the paddle
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * Moves the paddle when the cursor moves.
     * @param ev The MouseEvent of the event
     */
    public void mouseMoved(MouseEvent ev) {
        this.setVelocity(new GameVector(
                position.getX() - ev.getX(), position.getY() - ev.getY()));
        this.position = new GameVector(ev.getX(), ev.getY());
    }

    /**
     * Moves the paddle when the cursor is dragged.
     * @param ev The MouseEvent of the event
     */
    public void mouseDragged(MouseEvent ev) {
        this.setVelocity(new GameVector(
                position.getX() - ev.getX(), position.getY() - ev.getY()));
        this.position = new GameVector(ev.getX(), ev.getY());
    }
}
