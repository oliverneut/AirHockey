package gamepackage;

import javax.swing.*;
import java.awt.*;

public abstract class MovingEntity extends JPanel {

    protected transient GameVector position;
    protected transient GameVector velocity;
    protected transient int id;

    private transient int width;
    private transient int height;


    /**
     * Gets the position of the entity.
     *
     * @return The position of the entity
     */
    public GameVector getPosition() {
        return this.position;
    }

    /**
     * Sets the position of the entity.
     *
     * @param position The new position of the entity
     */
    public void setPosition(GameVector position) {
        this.position = position;
    }

    /**
     * Gets the velocity of the entity.
     *
     * @return The velocity of the entity
     */
    public GameVector getVelocity() {
        return this.velocity;
    }

    /**
     * Sets the velocity of the entity.
     *
     * @param velocity The new velocity of the entity
     */
    public void setVelocity(GameVector velocity) {
        this.velocity = velocity;
    }

    /**
     * Gets the id of the entity.
     *
     * @return The new id of the entity
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the entity.
     *
     * @param id The new id of the entity
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the height of the entity.
     * @param height The new height of the entity
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Gets the height of the entity.
     * @return The current height of the entity
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Sets the width of the entity.
     * @param width The new width of the entity
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Gets the width of the entity.
     * @return The current width of the entity
     */
    public int getWidth() {
        return this.width;
    }

}
