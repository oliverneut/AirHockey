package app.match;

import basis.GameVector;

/**
 * Class which defines a Paddle.
 */
public class Paddle extends MovingEntity {

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
}
