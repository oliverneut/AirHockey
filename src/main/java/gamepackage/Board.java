package gamepackage;

import javax.swing.*;
import java.awt.*;

/**
 * Instance of the board where all the entities will be put.
 */
public class Board extends JPanel {

    private Puck puck;

    public Board(Puck puck) {
        this.puck = puck;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        puck.paint(g);
    }
}
