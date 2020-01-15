package gamepackage;

import javax.swing.JPanel;

public abstract class MovingEntity extends JPanel {

    private static final long serialVersionUID = 5985568791324L;

    protected transient GameVector position;
    protected transient GameVector velocity;
    protected transient int id;

    private transient int width;
    private transient int height;


    /**
     * Handles a collision with another MovingEntity.
     *
     * @param other The colliding MovingEntity
     */
    public void handleCollision(MovingEntity other) {
        if (this instanceof Puck) {
            ((Puck) this).handleEntityCollision(other);
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
     * Gets the height of the entity.
     *
     * @return The current height of the entity
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Sets the height of the entity.
     *
     * @param height The new height of the entity
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Gets the width of the entity.
     *
     * @return The current width of the entity
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Sets the width of the entity.
     *
     * @param width The new width of the entity
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Checks whether there is an intersection with another MovingEntity.
     *
     * @param other The MovingEntity with which to check the intersection
     * @return positive value when there is no intersection, negative or 0 otherwise
     */
    public double intersects(MovingEntity other) {
        double otherRadius = other.height / 2;
        double thisRadius = getWidth() / 2;
        double thisX = this.position.getX() + thisRadius;
        double thisY = this.position.getY() + thisRadius;
        double otherX = other.position.getX() + otherRadius;
        double otherY = other.position.getY() + otherRadius;

        double distance = Math.sqrt(Math.pow(thisX - otherX, 2)
                + Math.pow(thisY - otherY, 2));
        if (distance == 0) {
            return distance;
        }
        return distance - (otherRadius + thisRadius);
    }

    /**
     * Determines the new position of the MovingEntity when it collides with another MovingEntity.
     *
     * @param other    The colliding MovingEntity
     * @param distance The distance between this MovingEntity and the other
     * @return the new position of this MovingEntity
     */
    //Warning suppressed, since PMD detects the used variables originalX,
    //originalY and puckNormal as unused.
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public GameVector setBack(MovingEntity other, double distance) {
        GameVector otherPosition = other.position;
        if (this.velocity.getX() != 0 && this.velocity.getY() != 0) {
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
     * Calculates the new direction of a MovingEntity after a collision with another MovingEntity.
     *
     * @param x        The x position of the entity
     * @param y        The y position of the entity
     * @param velocity The velocity of the entity
     * @return The new Velocity of the entity
     */
    public GameVector getBounceDirection(double x, double y, GameVector velocity) {

        //Calculate length and direction of the vector
        // of the hitting point perpendicular to the other entity
        double middleXDirection = position.getX() - x;
        double middleYDirection = position.getY() - y;
        double middleLength = Math.sqrt(Math.pow(middleYDirection, 2)
                + Math.pow(middleXDirection, 2));

        //Calculate normals of the perpendicular vector and the velocity of the entity
        GameVector middleNormal = new GameVector(middleXDirection / middleLength,
                middleYDirection / middleLength);
        double puckLength = Math.sqrt(Math.pow(velocity.getX(), 2)
                + Math.pow(velocity.getY(), 2));
        GameVector puckNormal = new GameVector(velocity.getX() / puckLength,
                velocity.getY() / puckLength);

        //Calculate angles between the vectors
        double cosine = middleNormal.dot(puckNormal);
        double sine = Math.sin(Math.acos(cosine));

        //Calculate new direction and magnitude of the entity according to rotation matrix
        double reflectedX = velocity.getX() * cosine + velocity.getY() * sine;
        double reflectedY = -velocity.getY() * cosine - velocity.getX() * sine;
        double newMagnitude = puckLength / Math.sqrt(Math.pow(reflectedX, 2)
                + Math.pow(reflectedY, 2));
        return new GameVector(newMagnitude * reflectedX, newMagnitude * reflectedY);
    }

    /**
     * Handles the collision with a wall.
     *
     * @param frame The frame where the game takes place.
     */
    public void wallCollide(field.Frame frame) {
        if (this instanceof Puck) {
            ((Puck) this).wallCollision(frame);
        } else if (this instanceof Paddle) {
            ((Paddle) this).wallCollision(frame);
        }
    }

}
