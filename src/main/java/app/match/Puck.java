package app.match;

import basis.GameVector;
import basis.Rectangle;
import java.util.ArrayList;

/**
 * Class which defines a Puck.
 */
public class Puck extends MovingEntity {
    private static final long serialVersionUID = 5985568796987L;
    private static final double MAX_SPEED = 5;
    private transient int multiplier;
    private transient int size;


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

    /**
     * Moves the puck.
     */
    //Warning suppressed, since PMD incorrectly detects the defined variable
    //paddle as undefined
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public void move(Frame frame) {
        //Set new position according to velocity.
        position.addVector(velocity);
        if (frame != null) {
            goalCollision(frame);

            wallCollision(frame);

            double distanceMe = intersects(frame.paddle);
            double distanceOpponent = getDistanceOpponentPaddle(frame);
            double distance = Math.min(distanceMe, distanceOpponent);
            Paddle paddle = getCollidingPaddle(frame, distance, distanceOpponent);
            if (distance <= 0) {
                distanceOpponent = -distanceOpponent;
                this.position =
                        paddle.setBack(this, distanceOpponent);
                handleCollision(frame, paddle);
                this.velocity.addVector(new GameVector(frame.paddle.velocity.getX() / 2,
                        frame.paddle.velocity.getY() / 2));
                if (this.velocity.getX() > MAX_SPEED) {
                    this.velocity.setX(MAX_SPEED);
                }
                if (this.velocity.getY() > MAX_SPEED) {
                    this.velocity.setY(MAX_SPEED);
                }
            }

        }
    }

    /**
     * Handles a collision with another moving entity.
     *
     * @param frame The frame where the game takes place
     * @param other The colliding MovingEntity
     */
    protected void handleEntityCollision(Frame frame, MovingEntity other) {
        if (other instanceof Paddle) {
            this.setVelocity(other.getBounceDirection(
                    position.getX(), position.getY(), getVelocity()));
        } else if (other instanceof Puck) {
            this.setVelocity(other.getBounceDirection(
                    position.getX(), position.getY(), getVelocity()));
        }

    }

    /**
     * Handles the collision with a wall.
     *
     * @param frame The frame where the game takes place
     */
    protected void wallCollision(Frame frame) {
        ArrayList<Rectangle> boxes = frame.getBoundingBoxes();
        if (position.getY() < (boxes.get(0).getYcord() + boxes.get(0).getHeight())) {
            position.setY(boxes.get(0).getYcord() + boxes.get(0).getHeight());
            velocity.setY(velocity.getY() * (-1 * multiplier));
            frame.match.updatePuck();
        } else if (position.getX() < (boxes.get(3).getXcord() + boxes.get(3).getWidth())) {
            position.setX(boxes.get(3).getXcord() + boxes.get(3).getWidth());
            velocity.setX(velocity.getX() * (-1 * multiplier));
            frame.match.updatePuck();

        } else if (position.getY() > (boxes.get(2).getYcord() - boxes.get(2).getHeight() - 36)) {
            position.setY(boxes.get(2).getYcord() - boxes.get(2).getHeight() - 36);
            velocity.setY(velocity.getY() * (-1 * multiplier));
            frame.match.updatePuck();

        } else if (position.getX() > (boxes.get(1).getXcord() - boxes.get(1).getWidth() - 28)) {
            position.setX(boxes.get(1).getXcord() - boxes.get(1).getWidth() - 28);
            velocity.setX(velocity.getX() * (-1 * multiplier));
            frame.match.updatePuck();
        } else {
            velocity.setX(velocity.getX() * (0.992 * multiplier));
            velocity.setY(velocity.getY() * (0.992 * multiplier));
        }
    }

    /**
     * Checks for collisions with the goal so that there can be a score.
     *
     * @param frame the given frame of the game.
     */
    private void goalCollision(Frame frame) {
        ArrayList<Rectangle> goals = frame.getGoals();

        if (position.getY() < (goals.get(0).getYcord() + goals.get(0).getHeight())
                && position.getX() >= goals.get(0).getXcord()
                && position.getX() <= goals.get(0).getXcord() + goals.get(0).getWidth()) {
            frame.match.updateScore(true);
            frame.resetMovingEntities(new GameVector(1, 1));
            frame.match.updatePuck();
            System.out.println("Player 1 scores");
        }

        if (position.getY() > (goals.get(1).getYcord() - goals.get(1).getHeight() - 39)
                && position.getX() >= goals.get(1).getXcord()
                && position.getX() <= goals.get(1).getXcord() + goals.get(1).getWidth()) {
            frame.match.updateScore(false);
            frame.resetMovingEntities(new GameVector(-1, -1));
            frame.match.updatePuck();
            System.out.println("Player 2 scores");
        }
    }

    /**
     * Gets the distance from this puck to the opponent's paddle.
     *
     * @param frame The frame where the game takes place
     * @return The distance from this puck to the opponent's paddle
     */
    private double getDistanceOpponentPaddle(Frame frame) {
        if (frame.opponentPaddle != null) {
            return intersects(frame.opponentPaddle);
        }
        return Double.MAX_VALUE;
    }

    /**
     * Gets the paddle the puck is currently colliding with.
     *
     * @param frame            The frame where the game takes place
     * @param distance         The distance to the player's paddle
     * @param distanceOpponent The distance to the opponent's paddle
     * @return The paddle the puck is currently colliding with
     */
    private Paddle getCollidingPaddle(Frame frame, double distance, double distanceOpponent) {
        if (distance >= distanceOpponent) {
            return frame.opponentPaddle;
        }
        return frame.paddle;
    }


}
