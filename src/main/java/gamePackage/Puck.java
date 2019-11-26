package gamePackage;

import javax.swing.*;

public class Puck extends JPanel {
    protected GameVector position;
    protected GameVector acceleration;
    protected GameVector velocity;

    public Puck(GameVector position, GameVector acceleration, GameVector velocity){
        this.position = position;
        this.acceleration = acceleration;
        this.velocity = velocity;
    }

    public GameVector getPosition() {
        return position;
    }

    public GameVector getAcceleration() {
        return acceleration;
    }

    public GameVector getVelocity() {
        return velocity;
    }
}
