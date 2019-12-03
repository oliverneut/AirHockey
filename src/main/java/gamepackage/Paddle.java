package gamepackage;

import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel;

/**
 * Class which defines a Paddle.
 */
public class Paddle extends JPanel {
    private static final long serialVersionUID = 59692986L;

    protected transient GameVector position;

    /**
     * Initializes a player paddle object for the game.
     * @param position The starting position of the paddle
     */
    public Paddle(GameVector position) {
        this.position = position;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLUE);
        g.fillOval((int) this.position.getX(), (int) this.position.getY(), 75, 20);
    }

    /**
     * Gets the position of the paddle.
     * @return The position of the paddle
     */
    public GameVector getPosition() {
        return this.position;
    }

    /**
     * Sets the position of the paddle.
     * @param position The new position of the paddle
     */
    public void setPosition(GameVector position) {
        this.position = position;
    }
}
