package basis;

import game.Frame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

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
        if (ev.getComponent() != null) {
            wallCollide(this, (Frame) ev.getComponent());
        }
    }

    /**
     * Moves the paddle when the cursor is dragged.
     *
     * @param ev The MouseEvent of the event
     */
    public void mouseDragged(MouseEvent ev) {
        mouseMoved(ev);
    }
}
