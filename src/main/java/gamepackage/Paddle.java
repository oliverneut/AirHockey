package gamepackage;

import field.Frame;
import field.Rectangle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * Class which defines a Paddle.
 */
public class Paddle extends MovingEntity implements MouseMotionListener {
    private static final long serialVersionUID = 59692986L;

    /**
     * Initializes a player paddle object for the game.
     *
     * @param position The starting position of the paddle
     * @param velocity The velocity of the paddle
     * @param id       The id of the paddle to identify the player
     * @param height   The height of the paddle
     * @param width    The width of the paddle
     */
    public Paddle(GameVector position, GameVector velocity, int id, int height, int width) {
        this.setPosition(position);
        this.setVelocity(velocity);
        this.setId(id);
        this.setHeight(height);
        this.setWidth(width);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval((int) this.position.getX(), (int) this.position.getY(), getWidth(), getHeight());
    }


    /**
     * Moves the paddle when the cursor moves.
     *
     * @param ev The MouseEvent of the event
     */
    public void mouseMoved(MouseEvent ev) {
        this.setVelocity(new GameVector(
                position.getX() - ev.getX(), position.getY() - ev.getY()));
        this.position = new GameVector(ev.getX(), ev.getY());
        wallCollide((Frame) ev.getComponent());
    }

    /**
     * Moves the paddle when the cursor is dragged.
     *
     * @param ev The MouseEvent of the event
     */
    public void mouseDragged(MouseEvent ev) {
        this.setVelocity(new GameVector(
                position.getX() - ev.getX(), position.getY() - ev.getY()));
        this.position = new GameVector(ev.getX(), ev.getY());
    }

    /**
     * Handles the collision with a wall and the middle of the screen.
     * @param frame The frame where the game takes place
     */
    //Warning suppressed, since PMD incorrectly detects the defined variable
    //positionX as undefined
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    protected void wallCollision(field.Frame frame) {
        ArrayList<Rectangle> boxes =  frame.getBoundingBoxes();

        double positionY = position.getY()+getHeight()/2;
        double positionX = position.getX()+getWidth()/2;

        if (positionY < (boxes.get(0).getYcord() + boxes.get(0).getHeight())) {
            position.setY(boxes.get(0).getYcord() + boxes.get(0).getHeight());

        } else if (positionX < (boxes.get(3).getXcord() + boxes.get(3).getWidth())) {
            position.setX(boxes.get(3).getXcord() + boxes.get(3).getWidth());

        } else if (positionY > (boxes.get(2).getYcord() - boxes.get(2).getHeight() - 36)) {
            position.setY(boxes.get(2).getYcord() - boxes.get(2).getHeight() - 36);

        } else if (positionX > (boxes.get(1).getXcord() - boxes.get(1).getWidth() - 28)) {
            position.setX(boxes.get(1).getXcord() - boxes.get(1).getWidth() - 28);
        }

        if (positionY < (frame.getHeight() / 2)) {
            position.setY((frame.getHeight() / 2) - (getHeight() / 2));
        }
    }
}
