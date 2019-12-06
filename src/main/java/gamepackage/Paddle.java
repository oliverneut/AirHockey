package gamepackage;

import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel;

/**
 * Class which defines a Paddle.
 */
public class Paddle extends JPanel {
    private static final long serialVersionUID = 59692986L;

    private transient int width;
    private transient int height;

    protected transient GameVector position;

    /**
     * Initializes a player paddle object for the game.
     * @param position The starting position of the paddle
     */
    public Paddle(GameVector position, int height, int width) {
        this.position = position;
        this.height = height;
        this.width = width;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLUE);
        g.fillOval((int) this.position.getX(), (int) this.position.getY(), width, height);
    }

    public boolean intersects(GameVector pos, double radius) {
        double distance =
                Math.sqrt(Math.pow(pos.getX()-position.getX(), 2) + Math.pow(pos.getY()-position.getY(), 2))-radius;
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

        //Calculate length and direction of the vector of the hitting point perpendicular to the paddle
        double middleXDirection = this.position.getX()-x;
        double middleYDirection = this.position.getY()-y;
        double middleLength = Math.sqrt(Math.pow(middleYDirection, 2) + Math.pow(middleXDirection, 2));

        //Calculate normals of the perpendicular vector and the velocity of the puck
        GameVector middleNormal = new GameVector(middleXDirection/middleLength, middleYDirection/middleLength);
        double puckLength = Math.sqrt(Math.pow(puckVelocity.getX(), 2) + Math.pow(puckVelocity.getY(), 2));
        GameVector puckNormal = new GameVector(puckVelocity.getX()/puckLength, puckVelocity.getY()/puckLength);

        //Calculate angles between the vectors
        double cosine = middleNormal.dot(puckNormal);
        double sine = Math.sin(Math.acos(cosine));

        //Calculate new direction and magnitude of the puck according to rotation matrix
        double reflectedX = puckVelocity.getX() * cosine + puckVelocity.getY() * sine;
        double reflectedY = -puckVelocity.getY() * cosine - puckVelocity.getX() * sine;
        double newMagnitude = puckLength / Math.sqrt(Math.pow(reflectedX, 2) + Math.pow(reflectedY, 2));
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
}
