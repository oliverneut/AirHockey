package game;

import basis.*;

import java.util.ArrayList;

public class Bounds {

    /**
     * Handles the collision with a wall.
     *
     * @param frame The frame where the game takes place
     * @param entity The entity which collides with a wall
     * @param multiplier The multiplier when the entity is a puck
     */
    public static void wallCollision(Frame frame, MovingEntity entity, int multiplier) {
        ArrayList<Rectangle> boxes = frame.getBoundingBoxes();
        if (entity instanceof Puck) {
            collisionPuck(boxes, entity, multiplier);
        } else if (entity instanceof Paddle){
            collisionPaddle(frame, entity);
        }
    }

    /**
     * Handles the collision with a puck.
     * @param boxes A list of bounding boxes
     * @param entity The puck entity
     * @param multiplier The multiplier of the puck
     */
    private static void collisionPuck(ArrayList<Rectangle> boxes, MovingEntity entity, int multiplier) {
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
