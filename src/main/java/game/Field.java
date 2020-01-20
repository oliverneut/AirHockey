package game;

import basis.Paddle;
import basis.Puck;
import basis.Rectangle;
import basis.ScoreCount;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * This class creates a field to play on.
 */
public class Field extends JPanel {

    // Define serialization id to avoid serialization related bugs
    public static final long serialVersionUID = 4328743;
    private static Image fieldImage;
    private static Color myColor = new Color(0, 255, 0, 0);
    private static ArrayList<basis.Rectangle> r = new ArrayList<basis.Rectangle>();
    private static ArrayList<basis.Rectangle> goals = new ArrayList<>();
    private transient Paddle paddle;
    private transient Paddle opponentPaddle;
    private transient ArrayList<Puck> puck;
    private transient int mode;
    private transient ScoreCount score;

    /**
     * Initiates the Drawing of a field.
     *
     * @param p      the given puck to draw.
     * @param paddle The given paddle to draw.
     * @param mode   The given mode of the game.
     */

    public Field(ArrayList<Puck> p, Paddle paddle, Paddle opponentPaddle, int mode) {
        this.puck = p;
        score = ScoreCount.getInstance();
        this.mode = mode;
        this.paddle = paddle;
        this.opponentPaddle = opponentPaddle;
        createField();
        try {
            createBoundingBoxes();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the image from the assets folder.
     */
    public final void getImage() {
        fieldImage = new ImageIcon("src/main/java/assets/" + this.mode + ".png").getImage();
    }

    /**
     * Sets the image and its preferred size.
     */
    private final void createField() {
        getImage();
        int w = fieldImage.getWidth(this);
        int h = fieldImage.getHeight(this);
        setPreferredSize(new Dimension(h, w));
    }

    /**
     * Reads the given board file to create the necessary bounding boxes.
     *
     * @throws FileNotFoundException When the file given could not be found.
     */
    private final void createBoundingBoxes() throws FileNotFoundException {
        File file = new File("src/main/java/assets/boards/" + mode + ".txt");
        Scanner sc = new Scanner(file);
        sc.nextDouble();
        sc.nextDouble();
        double n = sc.nextDouble();
        double m = sc.nextDouble();
        for (int i = 0; i < n; i++) {
            this.r.add(new basis.Rectangle((int) sc.nextDouble(), (int) sc.nextDouble(),
                    (int) sc.nextDouble(), (int) sc.nextDouble()));
        }
        for (int i = 0; i < m; i++) {
            this.goals.add(new basis.Rectangle((int) sc.nextDouble(), (int) sc.nextDouble(),
                    (int) sc.nextDouble(), (int) sc.nextDouble()));
        }
        sc.close();
    }

    /**
     * Overrides the draw method and draws the desired objects.
     *
     * @param g the object that actually draws, given by the library.
     */
    @Override
    public void paintComponent(Graphics g) {
        if(ScoreCount.getInstance().getWinner() == 0) {
            g.drawImage(fieldImage, 0, 0, null);
            g.setColor(myColor);
            for (int i = 0; i < r.size(); i++) {
                g.fillRect(r.get(i).getXcord(), r.get(i).getYcord(),
                    r.get(i).getWidth(), r.get(i).getHeight());
            }
            g.setColor(new Color(255, 0, 0, 0));
            for (int i = 0; i < goals.size(); i++) {
                g.fillRect(goals.get(i).getXcord(), goals.get(i).getYcord(),
                    goals.get(i).getWidth(), goals.get(i).getHeight());
            }
            g.setColor(new Color(0, 0, 0, 255));
            for (int i = 0; i < puck.size(); i++) {
                puck.get(i).paint(g);
            }
            paddle.paint(g);
            if (opponentPaddle != null) {
                opponentPaddle.paint(g);
            }
            g.setColor(new Color(100, 100, 100, 100));
            g.drawString("goals: " + score.getPlayer2(), 120, 20);
            g.drawString("goals: " + score.getPlayer1(), 120, 587);
        }
        if(ScoreCount.getInstance().getWinner() == 1) {
            g.setColor(new Color(0, 100, 0, 200));
            g.fillRect(0,0, this.getWidth(), this.getHeight());
            g.setColor(new Color(0,0,0,255));
            g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
            g.drawString("You Win", 50, 290);
        } else if(ScoreCount.getInstance().getWinner() == 2) {
            g.setColor(new Color(100, 0, 0, 200));
            g.fillRect(0,0, this.getWidth(), this.getHeight());
            g.setColor(new Color(0,0,0,255));
            g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
            g.drawString("You Lose!", 40, 290);
        }
    }

    /**
     * this method makes gets the boxes for collision.
     *
     * @return the bounding boxes.
     */
    public ArrayList<basis.Rectangle> getBoundBoxes() {
        return this.r;
    }

    /**
     * Returns the goals on a given map.
     *
     * @return the given maps goals.
     */
    public ArrayList<Rectangle> getGoals() {
        return this.goals;
    }
}
