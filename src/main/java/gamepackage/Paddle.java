package gamepackage;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * Class which defines a Paddle.
 */
public class Paddle extends JPanel {
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
     * @param pos The position of the paddle
     * @param radius The radius of the puck
     */
    public boolean intersects(GameVector pos, double radius) {
        double distance = Math.sqrt(Math.pow(pos.getX() - position.getX(), 2)
                + Math.pow(pos.getY() - position.getY(), 2)) - radius;
        return distance <= width;
    }

    /**
     * Calculates the new direction of the puck after a collision with the paddle.
     * @param x The x position of the puck
     * @param y The y position of the puck
     * @param puckVelocity The velocity of the puck
     * @return The new Velocity of the puck
     */
    public GameVector getBounceDirection(double x, double y, GameVector puckVelocity) {

        double middleXDirection = this.position.getX() - x;
        double middleYDirection = this.position.getY() - y;
        double middleLength = Math.sqrt(Math.pow(middleYDirection, 2)
                + Math.pow(middleXDirection, 2));

        GameVector middleNormal = new GameVector(-middleYDirection / middleLength,
                middleXDirection / middleLength);
        double puckLength = Math.sqrt(Math.pow(puckVelocity.getX(), 2)
                + Math.pow(puckVelocity.getY(), 2));
        GameVector puckNormal = new GameVector(puckVelocity.getX() / puckLength,
                puckVelocity.getY() / puckLength);

        double cosine = middleNormal.dot(puckNormal);
        double reflectedX = puckVelocity.getX() * -cosine;
        double reflectedY = puckVelocity.getY() * -cosine;
        return new GameVector(reflectedX, reflectedY);
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
}
