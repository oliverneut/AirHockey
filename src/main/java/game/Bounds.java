package game;

import basis.GameVector;
import basis.MovingEntity;
import basis.Paddle;
import basis.Puck;
import basis.Rectangle;
import basis.ScoreCount;

import java.util.ArrayList;

public class Bounds {

    /**
     * Handles the collision with a wall.
     *
     * @param frame      The frame where the game takes place
     * @param entity     The entity which collides with a wall
     * @param multiplier The multiplier when the entity is a puck
     */
    //Warning suppressed since PMD detects the recently
    //defined variable boxes as undefined.
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public static void wallCollision(Frame frame, MovingEntity entity, int multiplier) {
        ArrayList<Rectangle> boxes = frame.getBoundingBoxes();
        if (entity instanceof Puck) {
            collisionPuck(boxes, entity, multiplier);
        } else if (entity instanceof Paddle) {
            collisionPaddle(frame, entity);
        }
    }

    /**
     * Checks for collisions with the goal so that there can be a score.
     *
     * @param frame the given frame of the game.
     * @param puck the puck to collide with a goal.
     */
    public static void goalCollision(Frame frame, Puck puck) {
        GameVector position = puck.getPosition();
        ArrayList<Rectangle> goals = frame.getGoals();

        if (position.getY() < (goals.get(0).getYcord() + goals.get(0).getHeight())
                && position.getX() >= goals.get(0).getXcord()
                && position.getX() <= goals.get(0).getXcord() + goals.get(0).getWidth()) {

            ScoreCount.getInstance().goal1();
            frame.resetMovingEntities(new GameVector(1, 1));
            System.out.println("Player 1 goals: " + ScoreCount.getInstance().getPlayer1());
        }

        if (position.getY() > (goals.get(1).getYcord() - goals.get(1).getHeight() - 39)
                && position.getX() >= goals.get(1).getXcord()
                && position.getX() <= goals.get(1).getXcord() + goals.get(1).getWidth()) {

            ScoreCount.getInstance().goal2();
            frame.resetMovingEntities(new GameVector(-1, -1));
            System.out.println("Player 2 goals: " + ScoreCount.getInstance().getPlayer2());
        }
    }



    /**
     * Handles the collision with a puck.
     * @param boxes A list of bounding boxes
     * @param entity The puck entity
     * @param multiplier The multiplier of the puck
     */
    private static void collisionPuck(ArrayList<Rectangle> boxes,
                                      MovingEntity entity, int multiplier) {
        GameVector position = entity.getPosition();
        GameVector velocity = entity.getVelocity();
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
        entity.setPosition(position);
        entity.setVelocity(velocity);
    }

    //Warning suppressed since PMD detects the recently
    //defined variable positionX as undefined.
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    private static void collisionPaddle(Frame frame, MovingEntity entity) {
        ArrayList<Rectangle> boxes = frame.getBoundingBoxes();
        double height = entity.getHeight();
        GameVector position = entity.getPosition();
        double positionY = position.getY() + height / 2;
        double positionX = position.getX();

        if (positionY < (boxes.get(0).getYcord() + boxes.get(0).getHeight())) {
            position.setY(boxes.get(0).getYcord() + boxes.get(0).getHeight());

        } else if (positionX < (boxes.get(3).getXcord() + boxes.get(3).getWidth())) {
            position.setX(boxes.get(3).getXcord() + boxes.get(3).getWidth());

        } else if (positionY > (boxes.get(2).getYcord()
                - boxes.get(2).getHeight() - height / 2)) {
            position.setY(boxes.get(2).getYcord() - boxes.get(2).getHeight() - height / 2);

        } else if (positionX > frame.getWidth() - boxes.get(1).getWidth() - height * 5 / 4) {
            position.setX(frame.getWidth() - boxes.get(1).getWidth() - height * 5 / 4);
        }

        if (positionY < (frame.getHeight() / 2)) {
            position.setY((frame.getHeight() / 2) - (height / 2));
            entity.setVelocity(new GameVector(1, 1));
        }
        entity.setPosition(position);
    }
}
