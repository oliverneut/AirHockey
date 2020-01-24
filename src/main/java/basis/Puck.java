package basis;

import java.awt.Graphics;
import java.util.ArrayList;

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
    public void move(game.Frame frame) {
        //Set new position according to velocity.
        position.addVector(velocity);
        goalCollision(frame);

        wallCollision(frame);

        double distanceMe = intersects(frame.getPaddle());
        double distanceOpponent = getDistanceOpponentPaddle(frame);
        double distance = Math.min(distanceMe, distanceOpponent);
        Paddle paddle = getCollidingPaddle(frame, distance, distanceOpponent);
        if (distance <= 0) {
            this.position =
                    paddle.setBack(this);
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
            this.setVelocity(((Paddle) other).getBounceDirection(
                    position.getX(), position.getY(), getVelocity()));
        } else if (other instanceof Puck) {
            this.setVelocity(((Puck) other).getBounceDirection(
                    position.getX(), position.getY(), getVelocity()));
        }
    }

    /**
     * Handles the collision with a wall.
     *
     * @param frame The frame where the game takes place
     */
    //Warning suppressed, since PMD detects the redefinition of bounceX
    // as a DD anomaly, and the defined variable bounceX as undefined.
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    protected void wallCollision(game.Frame frame) {
        boolean bounceX = false;
        if (position.getY() < (getBoxPosition(0, false, false, frame))) {
            position.setY(getBoxPosition(0, false, false, frame));
        } else if (position.getX() < (getBoxPosition(3, true, false, frame))) {
            position.setX(getBoxPosition(3, true, false, frame));
            bounceX = true;
        } else if (position.getY() > (getBoxPosition(2, false, true, frame) - 36)) {
            position.setY(getBoxPosition(2, false, true, frame) - 36);
        } else if (position.getX() > (getBoxPosition(1, true, true, frame) - 28)) {
            position.setX(getBoxPosition(1, true, true, frame) - 28);
            bounceX = true;
        } else {
            setVelocity(false, false);
            setVelocity(false, true);
            return;
        }
        setVelocity(true, bounceX);
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
     * Gets the respective setback x or y position of a bounding box.
     * @param box The bounding box to be considered
     * @param isX True when the X coordinate should be returned,
     *            False when the Y coordinate should be returned
     * @param minimal True when the coordinate to be considered is the minimal
     *                box coordinate, false otherwise.
     * @return An x or y position for which the puck should be set back.
     */
    private double getBoxPosition(int box, boolean isX, boolean minimal, game.Frame frame) {
        ArrayList<Rectangle> boxes = frame.getBoundingBoxes();
        if (isX) {
            double width = minimal
                    ? -boxes.get(box).getWidth() : boxes.get(box).getWidth();
            return boxes.get(box).getXcord() + width;
        }
        double height = minimal
                ? -boxes.get(box).getHeight() : boxes.get(box).getHeight();
        return boxes.get(box).getYcord() + height;

    }

    /**
     * Sets the velocity of the puck according to whether it
     * bounced off a wall or not.
     * @param bounced True when the puck bounced off a wall.
     * @param isX True when the X coordinate of the velocity needs to be altered,
     *            False when the Y coordinate of the velocity needs to be altered
     */
    private void setVelocity(boolean bounced, boolean isX) {
        double coefficient = bounced
                ? -1 : 0.992;
        if (isX) {
            velocity.setX(coefficient * multiplier);
        } else {
            velocity.setY(coefficient * multiplier);
        }
    }

    /**
     * Checks for collisions with the goal so that there can be a score.
     *
     * @param frame the given frame of the game.
     */
    private void goalCollision(game.Frame frame) {
        ArrayList<Rectangle> goals = frame.getGoals();

        if (position.getY() < (goals.get(0).getYcord() + goals.get(0).getHeight())
                && position.getX() >= goals.get(0).getXcord()
                && position.getX() <= goals.get(0).getXcord() + goals.get(0).getWidth()) {
            ScoreCount.getInstance().goal1();
            System.out.println("Player 1 goals: " + ScoreCount.getInstance().getPlayer1());
        }

        if (position.getY() > (goals.get(1).getYcord() - goals.get(1).getHeight() - 39)
                && position.getX() >= goals.get(1).getXcord()
                && position.getX() <= goals.get(1).getXcord() + goals.get(1).getWidth()) {
            ScoreCount.getInstance().goal2();
            System.out.println("Player 2 goals: " + ScoreCount.getInstance().getPlayer2());
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


}
