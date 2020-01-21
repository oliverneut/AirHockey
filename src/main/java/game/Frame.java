package game;

import basis.GameVector;
import basis.MovingEntity;
import basis.Paddle;
import basis.Puck;
import basis.Rectangle;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFrame;

/**
 * This class creates the frame to draw everything in.
 */
@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class Frame extends JFrame {

    // Define serialization id to avoid serialization related bugs
    public static final long serialVersionUID = 4328743;
    protected transient Field field;
    private transient Paddle paddle;
    private transient Paddle opponentPaddle;
    private transient int width = 320;
    private transient int height = 640;
    private transient int mode;
    private transient ArrayList<Puck> pucks = new ArrayList<>();

    /**
     * This method creates a new frame and initiates the necessary methods to draw everything.
     *
     * @param mode The mode of the game.
     * @throws FileNotFoundException when the board file can not be found.
     */

    public Frame(int mode) throws FileNotFoundException {
        this.mode = mode;
        File file = new File("src/main/java/assets/boards/" + mode + ".txt");
        Scanner sc = new Scanner(file);
        this.width = sc.nextInt();
        this.height = sc.nextInt();
        sc.close();

        try {
            createPuck();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.paddle = createPaddle();
        this.opponentPaddle = createPaddle();
        this.opponentPaddle.setPosition(new GameVector(0, 0));
        this.opponentPaddle.setId(1);
        createNewFrame();

        this.addMouseMotionListener(paddle);

        BufferedImage image = getGraphicsConfiguration()
                .createCompatibleImage(1, 1, Transparency.BITMASK);
        Graphics2D g = image.createGraphics();
        g.setBackground(new Color(0, 0, 0, 0));
        g.clearRect(0, 0, 1, 1);

        Cursor invisibleCursor = getToolkit().createCustomCursor(
                image, new Point(0, 0), "Invisible");
        this.setCursor(invisibleCursor);
    }

    /**
     * This method creates the frame for the window and then draws everything in.
     */
    private void createNewFrame() {
        setSize(this.width, this.height);

        this.field = new Field(this.pucks, this.paddle, this.opponentPaddle, mode);
        add(field);
        setTitle("Board One");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private Paddle createPaddle() {
        GameVector position = new GameVector(100,
                100);
        GameVector velocity = new GameVector(0, 0);
        return new Paddle(position, velocity, 0, 70, 70);
    }

    /**
     * Creates a puck as specified in the given file.
     *
     * @throws FileNotFoundException if the file does not exist.
     */
    private void createPuck() throws FileNotFoundException {
        File file = new File("src/main/java/assets/pucks/" + mode + ".txt");
        Scanner sc = new Scanner(file);
        int n = sc.nextInt();
        GameVector position;
        GameVector velocity;

        for (int i = 0; i < n; i++) {
            position = new GameVector(sc.nextInt(),
                    sc.nextInt());
            velocity = new GameVector(20.0, 10.0);
            pucks.add(new Puck(position, velocity, sc.nextInt(), sc.nextInt()));
        }
        sc.close();
    }

    /**
     * Reset the positions of the paddles and pucks.
     */
    public void resetMovingEntities(GameVector puckVelocity) {
        paddle.setPosition(new GameVector(width / 2.0, height * 3 / 4.0));
        paddle.setVelocity(new GameVector(0, 0));

        opponentPaddle.setPosition(new GameVector(width / 2.0, height / 4.0));
        opponentPaddle.setVelocity(new GameVector(0, 0));

        for (Puck puck : pucks) {
            puck.setPosition(new GameVector(width / 2.0, height / 2.0));
            puck.setVelocity(puckVelocity);
        }

    }

    /**
     * Method returns the puck.
     *
     * @return a getter for the made puck.
     */
    public ArrayList<Puck> getPucks() {
        return this.pucks;
    }

    /**
     * Method returns the paddle.
     *
     * @return a getter for the made paddle.
     */
    public Paddle getPaddle() {
        return this.paddle;
    }

    /**
     * Method returns the opponent's paddle.
     *
     * @return a getter for the made paddle.
     */
    public Paddle getOpponentPaddle() {
        return this.opponentPaddle;
    }

    /**
     * return the bounding boxes for the collisions.
     *
     * @return an array of collision boxes.
     */
    public ArrayList<basis.Rectangle> getBoundingBoxes() {
        return this.field.getBoundBoxes();
    }

    /**
     * return the goal boxes for collisions.
     *
     * @return an array of collision goals.
     */
    public ArrayList<Rectangle> getGoals() {
        return this.field.getGoals();
    }

    /**
     * Calculates the mirrored position in the x axis of the frame.
     *
     * @param position The position to be mirrored
     * @param entity   The entity to be mirrored
     * @return The mirrored position of the given vector
     */
    public GameVector mirrorPosition(GameVector position, MovingEntity entity) {
        double x = position.getX();
        double y = position.getY();
        double newX = this.width - x - entity.getWidth() * 42 / 32.0;
        double newY = this.height - y - entity.getHeight() * 3 / 2.0;
        return new GameVector(newX, newY);
    }

    /**
     * Calculates the mirrored velocity.
     *
     * @param velocity The velocity to be mirrored
     * @return The mirrored velocity of the given vector
     */
    public GameVector mirrorVelocity(GameVector velocity) {
        double x = velocity.getX();
        double y = velocity.getY();
        return new GameVector(-x, -y);
    }
}
