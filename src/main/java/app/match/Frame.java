package app.match;

import basis.GameVector;
import basis.Rectangle;
import java.util.ArrayList;

/**
 * This class creates the frame to draw everything in.
 */
@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class Frame {

    protected transient int width = 320;
    protected transient int height = 640;
    protected transient Match match;
    protected transient Paddle paddle;
    protected transient Paddle opponentPaddle;
    protected transient Puck puck;
    protected transient ArrayList<Rectangle> boundingBoxes;
    protected transient ArrayList<Rectangle> goalBoxes;

    /**
     * This method creates a new frame and all of its components.
     */
    public Frame(Match match, int width, int height, ArrayList<Rectangle> boundingBoxes,
                 ArrayList<Rectangle> goalBoxes) {
        this.match = match;
        this.width = width;
        this.height = height;
        this.boundingBoxes = boundingBoxes;
        this.goalBoxes = goalBoxes;
    }

    protected void createPaddle(int height, int width, boolean opponent) {
        int id;
        GameVector position;
        if (opponent) {
            position = new GameVector(width / 2.0, height * 3 / 4.0);
            id = 1;
        } else {
            position = new GameVector(width / 2.0, height / 4.0);
            id = 0;
        }
        GameVector velocity = new GameVector(0, 0);

        if (opponent) {
            opponentPaddle = new Paddle(position, velocity, id, height, width);
        } else {
            paddle = new Paddle(position, velocity, id, height, width);
        }
    }

    /**
     * Creates a puck.
     */
    protected void createPuck(int size, int multiplier) {
        GameVector position = new GameVector(width / 2.0, height / 2.0);
        GameVector velocity = new GameVector(1, 1);
        puck = new Puck(position, velocity, size, multiplier);
    }

    /**
     * Reset the positions of the paddles and pucks.
     */
    void resetMovingEntities(GameVector puckVelocity) {
        paddle.setPosition(new GameVector(width / 2.0, height * 3 / 4.0));
        paddle.setVelocity(new GameVector(0, 0));

        opponentPaddle.setPosition(new GameVector(width / 2.0, height / 4.0));
        opponentPaddle.setVelocity(new GameVector(0, 0));

        puck.setPosition(new GameVector(width / 2.0, height / 2.0));
        puck.setVelocity(puckVelocity);


    }


    /**
     * return the bounding boxes for the collisions.
     *
     * @return an array of collision boxes.
     */
    public ArrayList<basis.Rectangle> getBoundingBoxes() {
        return this.boundingBoxes;
    }

    /**
     * return the goal boxes for collisions.
     *
     * @return an array of collision goals.
     */
    public ArrayList<Rectangle> getGoals() {
        return this.goalBoxes;
    }

    /**
     * Calculates the mirrored coordinates of a position in the x and y axis of the frame.
     *
     * @param position The position to be mirrored
     * @param entity   The entity to be mirrored
     * @return The mirrored coordinates of the given position
     */
    public GameVector mirrorPosition(GameVector position, MovingEntity entity) {
        double x = position.getX();
        double y = position.getY();
        double newX = this.width - x - entity.getWidth() * 42 / 32.0;
        double newY = this.height - y - entity.getHeight() * 3 / 2.0;
        return new GameVector(newX, newY);
    }

    /**
     * Calculates the mirrored velocity.
     *
     * @param velocity The velocity to be mirrored
     * @return The mirrored velocity
     */
    public GameVector mirrorVelocity(GameVector velocity) {
        double x = velocity.getX();
        double y = velocity.getY();
        return new GameVector(-x, -y);
    }
}
