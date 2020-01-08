package gamepackage;

import javax.swing.*;
import java.awt.*;

public abstract class MovingEntity extends JPanel {

    protected transient GameVector position;
    protected transient GameVector velocity;
    protected transient int id;

    private transient int width;
    private transient int height;


    /**
     * Handles a collision with another MovingEntity
     * @param other The colliding MovingEntity
     */
    public void handleCollision(field.Frame frame, MovingEntity other) {
        if (this instanceof Puck) {
            handlePuckCollision(frame, other);
        } else if (this instanceof Paddle) {
            handlePaddleCollision(frame, other);
        }
    }


    /**
     * Gets the position of the entity.
     *
     * @return The position of the entity
     */
    public GameVector getPosition() {
        return this.position;
    }

    /**
     * Sets the position of the entity.
     *
     * @param position The new position of the entity
     */
    public void setPosition(GameVector position) {
        this.position = position;
    }

    /**
     * Gets the velocity of the entity.
     *
     * @return The velocity of the entity
     */
    public GameVector getVelocity() {
        return this.velocity;
    }

    /**
     * Sets the velocity of the entity.
     *
     * @param velocity The new velocity of the entity
     */
    public void setVelocity(GameVector velocity) {
        this.velocity = velocity;
    }

    /**
     * Gets the id of the entity.
     *
     * @return The new id of the entity
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the entity.
     *
     * @param id The new id of the entity
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the height of the entity.
     * @param height The new height of the entity
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Gets the height of the entity.
     * @return The current height of the entity
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Sets the width of the entity.
     * @param width The new width of the entity
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Gets the width of the entity.
     * @return The current width of the entity
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Checks whether there is an intersection with another MovingEntity.
     *
     * @param other The MovingEntity with which to check the intersection
     * @return positive value when there is no intersection, negative or 0 otherwise
     */
    public double intersects(MovingEntity other) {
        double otherRadius = other.height / 2;
        double thisX = this.position.getX() + (double) getWidth() / 2;
        double thisY = this.position.getY() + (double) getHeight() / 2;
        double otherX = other.position.getX() + otherRadius;
        double otherY = other.position.getY() + otherRadius;
        double thisRadius = getWidth() / 2;

        double distance = Math.sqrt(Math.pow(thisX - otherX, 2)
                + Math.pow(thisY - otherY, 2));
        return distance - (otherRadius + thisRadius);
    }

    /**
     * Determines the new position of the MovingEntity when it collides with another MovingEntity.
     *
     * @param other        The colliding MovingEntity
     * @param distance     The distance between this MovingEntity and the other
     * @return the new position of this MovingEntity
     */
    //Warning suppressed, since PMD detects the used variables originalX,
    //originalY and puckNormal as unused.
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public GameVector setBack(MovingEntity other, double distance) {
        GameVector otherPosition = other.position;
        if (this.velocity.getX() == 0 && this.velocity.getY() == 0) {
            double originalX = otherPosition.getX();
            double originalY = otherPosition.getY();
            double puckLength = Math.sqrt(Math.pow(other.velocity.getX(), 2)
                    + Math.pow(other.velocity.getY(), 2));

            GameVector puckNormal = new GameVector(-other.velocity.getX() / puckLength,
                    -other.velocity.getY() / puckLength);

            double pythagorean = 0;
            while (pythagorean <= distance) {
                otherPosition.addVector(puckNormal);
                pythagorean = Math.sqrt(Math.pow(otherPosition.getX() - originalX, 2)
                        + Math.pow(otherPosition.getY() - originalY, 2));
            }
        }
        GameVector negativeVelocity = new GameVector(-velocity.getX(), -velocity.getY());
        otherPosition.addVector(negativeVelocity);
        velocity = negativeVelocity;
        return otherPosition;
    }


    /**
     * Handles a collision where this MovingEntity is a puck.
     * @param frame The frame where the game takes place
     * @param other The colliding MovingEntity
     */
    private void handlePuckCollision(field.Frame frame, MovingEntity other) {
        Puck thisPuck = (Puck) this;
        if (other instanceof Paddle) {
            Paddle otherPaddle = (Paddle) other;
            thisPuck.setVelocity(otherPaddle.getBounceDirection(
                    position.getX(), position.getY(), getVelocity()));
        }
    }

    /**
     * Handles a collision where this MovingEntity is a paddle.
     * @param frame The frame where the game takes place
     * @param other The colliding MovingEntity
     */
    private void handlePaddleCollision(field.Frame frame, MovingEntity other) {
        Paddle thisPaddle = (Paddle) this;
        if (other instanceof Puck) {
            Puck otherPuck = (Puck) other;

        }
        if (other instanceof Paddle) {
            Paddle otherPaddle = (Paddle) other;
        }
    }
}
