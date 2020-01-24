package basis;

import com.github.cliftonlabs.json_simple.JsonObject;
import game.Bounds;
import game.Frame;
import java.awt.Graphics;
import java.math.BigDecimal;

/**
 * Class which defines a Puck.
 */
public class Puck extends MovingEntity {
    private static final long serialVersionUID = 5985568796987L;
    private static final double MAX_SPEED = 5;

    public transient int multiplier;
    public transient int size;

    /**
     * Initializes the puck for the game.
     *
     * @param position   The starting position of the puck
     * @param size       The size of the puck
     * @param velocity   The starting velocity of the puck
     * @param multiplier The amount of friction
     */
    public Puck(GameVector position, GameVector velocity, int size, int multiplier) {
        this.setPosition(position);
        this.setVelocity(velocity);
        this.setWidth(size);
        this.setHeight(size);
        this.size = size;
        this.multiplier = multiplier;
    }

    @Override
    public void paint(Graphics g) {
        g.fillOval((int) this.position.getX(), (int) this.position.getY(), size, size);
    }

    /**
     * Moves the puck.
     *
     * @param frame The frame where the game takes place
     */
    //Warning suppressed, since PMD incorrectly detects the defined variable
    //paddle as undefined
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public void move(Frame frame) {
        //Set new position according to velocity.
        position.addVector(velocity);
        Bounds.goalCollision(frame, this);

        wallCollide(this, frame);

        double distanceMe = intersects(frame.getPaddle());
        double distanceOpponent = getDistanceOpponentPaddle(frame);
        double distance = Math.min(distanceMe, distanceOpponent);
        Paddle paddle = getCollidingPaddle(frame, distance, distanceOpponent);
        if (distance <= 0) {
            this.position = paddle.setBack(this);
            handleCollision(this, paddle);
            this.velocity.addVector(new GameVector(frame.getPaddle().velocity.getX() / 2,
                    frame.getPaddle().velocity.getY() / 2));
        }

        checkMaxVelocity();

        frame.repaint();
    }

    /**
     * Handles a collision with another moving entity.
     *
     * @param other The colliding MovingEntity
     */
    protected void handleEntityCollision(MovingEntity other) {
        if (other instanceof Paddle) {
            this.setVelocity(other.getBounceDirection(
                    position.getX(), position.getY(), getVelocity()));
        } else if (other instanceof Puck) {
            this.setVelocity(other.getBounceDirection(
                    position.getX(), position.getY(), getVelocity()));
        }
    }


    /**
     * Checks if the puck exceeds the maximum velocity,
     * and if so, alters its speed.
     */
    protected void checkMaxVelocity() {
        if (this.velocity.getX() > MAX_SPEED) {
            this.velocity.setX(this.velocity.getX() / MAX_SPEED);
        }
        if (this.velocity.getY() > MAX_SPEED) {
            this.velocity.setY(this.velocity.getY() / MAX_SPEED);
        }
    }

    /**
     * Gets the distance from this puck to the opponent's paddle.
     *
     * @param frame The frame where the game takes place
     * @return The distance from this puck to the opponent's paddle
     */
    private double getDistanceOpponentPaddle(game.Frame frame) {
        return intersects(frame.getOpponentPaddle());
    }

    /**
     * Gets the paddle the puck is currently colliding with.
     *
     * @param frame            The frame where the game takes place
     * @param distance         The distance to the player's paddle
     * @param distanceOpponent The distance to the opponent's paddle
     * @return The paddle the puck is currently colliding with
     */
    private Paddle getCollidingPaddle(game.Frame frame, double distance, double distanceOpponent) {
        if (distance >= distanceOpponent) {
            return frame.getOpponentPaddle();
        }
        return frame.getPaddle();
    }

    /**
     * Apply the position and velocity from the json object.
     *
     * @param json The JsonObject containing the new values.
     */
    public void applyPuckUpdate(JsonObject json) {

        BigDecimal temp = (BigDecimal) json.get("xpos");
        if (temp == null) {
            System.out.println("Puck update null");
            return;
        }

        double xcoord = (temp).doubleValue();
        double ycoord = ((BigDecimal) json.get("ypos")).doubleValue();

        double xvel = ((BigDecimal) json.get("xvel")).doubleValue();
        double yvel = ((BigDecimal) json.get("yvel")).doubleValue();

        this.setPosition(new GameVector(xcoord, ycoord));
        this.setVelocity(new GameVector(xvel, yvel));
    }

}
