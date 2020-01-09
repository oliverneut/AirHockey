package gamepackage;

import field.Frame;
import field.Rectangle;

import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;


/**
 * Class which defines a Puck.
 */
public class Puck extends MovingEntity {
    private static final long serialVersionUID = 5985568796987L;

    private transient int multiplier;
    private transient int size;

    /**
     * Initializes the puck for the game.
     * @param position The starting position of the puck
     * @param size The size of the puck
     * @param velocity The starting velocity of the puck
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
     * @param frame The frame where the game takes place
     */
    //Warning suppressed, since PMD incorrectly detects the defined variable
    //paddle as undefined
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public void move(field.Frame frame) {
        //Set new position according to velocity.
        position.addVector(velocity);

        if (frame != null) {
            goalCollision(frame);

            wallCollision(frame);

            double distanceMe = intersects(frame.getPaddle());
            double distanceOpponent = getDistanceOpponentPaddle(frame);
            double distance = Math.min(distanceMe, distanceOpponent);
            Paddle paddle = getCollidingPaddle(frame, distance, distanceOpponent);
            if (distance <= 0) {
                distanceOpponent = -distanceOpponent;
                this.position =
                        paddle.setBack(this, distanceOpponent);
                handleCollision(paddle);
                this.velocity.addVector(new GameVector(frame.getPaddle().velocity.getX() / 2,
                        frame.getPaddle().velocity.getY() / 2));

            }

            frame.repaint();
        }
    }

    /**
     * Handles a collision with another moving entity
     * @param other The colliding MovingEntity
     */
    protected void handleEntityCollision(MovingEntity other) {
        if (other instanceof Paddle) {
            this.setVelocity(((Paddle) other).getBounceDirection(
                    position.getX(), position.getY(), getVelocity()));
        }
        else if (other instanceof Puck) {
            this.setVelocity(((Puck) other).getBounceDirection(
                    position.getX(), position.getY(), getVelocity()));
        }
    }
    /**
     * Handles the collision with a wall.
     * @param frame The frame where the game takes place
     */
    protected void wallCollision(Frame frame) {
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
     * Gets the distance from this puck to the opponent's paddle.
     * @param frame The frame where the game takes place
     * @return The distance from this puck to the opponent's paddle
     */
    private double getDistanceOpponentPaddle(field.Frame frame) {
        if (frame.getOpponentPaddle() != null) {
            return intersects(frame.getOpponentPaddle());
        }
        return Double.MAX_VALUE;
    }

    /**
     * Gets the paddle the puck is currently colliding with.
     * @param frame The frame where the game takes place
     * @param distance The distance to the player's paddle
     * @param distanceOpponent The distance to the opponent's paddle
     * @return The paddle the puck is currently colliding with
     */
    private Paddle getCollidingPaddle(field.Frame frame, double distance, double distanceOpponent) {
        if (distance >= distanceOpponent) {
            return frame.getOpponentPaddle();
        }
        return frame.getPaddle();
    }


}
