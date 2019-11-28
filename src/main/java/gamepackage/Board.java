package gamepackage;

import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * Instance of the board where all the entities will be put.
 */
public class Board extends JPanel {

    private transient Puck puck;
    private static final long serialVersionUID = 998709253L;

    public Board(Puck puck) {
        this.puck = puck;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        puck.paint(g);
    }
}
