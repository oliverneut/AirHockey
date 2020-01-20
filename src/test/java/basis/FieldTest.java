package basis;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

import game.Field;

import java.awt.Graphics;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class FieldTest {

    @Test
    void testSetter() {
        Paddle paddle = new Paddle(new GameVector(0, 0), new GameVector(0, 0), 1, 50, 50);
        Paddle opponentPaddle = new Paddle(new GameVector(0, 0), new GameVector(0, 0), 1, 50, 50);
        ArrayList<Puck> pucks = new ArrayList<>();
        pucks.add(new Puck(new GameVector(12, 12), new GameVector(12, 12), 1, 50));
        Field field = new Field(pucks, paddle, opponentPaddle, 1);
        assertNotNull(field);
    }

    @Test
    void testGetBoundBoxes() {
        Paddle paddle = new Paddle(new GameVector(0, 0), new GameVector(0, 0), 1, 50, 50);
        Paddle opponentPaddle = new Paddle(new GameVector(0, 0), new GameVector(0, 0), 1, 50, 50);
        ArrayList<Puck> pucks = new ArrayList<>();
        pucks.add(new Puck(new GameVector(12, 12), new GameVector(12, 12), 1, 50));
        Field field = new Field(pucks, paddle, opponentPaddle, 1);
        assertNotNull(field.getBoundBoxes());
    }

    @Test
    void testGetGoals() {
        Paddle paddle = new Paddle(new GameVector(0, 0), new GameVector(0, 0), 1, 50, 50);
        Paddle opponentPaddle = new Paddle(new GameVector(0, 0), new GameVector(0, 0), 1, 50, 50);
        ArrayList<Puck> pucks = new ArrayList<>();
        pucks.add(new Puck(new GameVector(12, 12), new GameVector(12, 12), 1, 50));
        Field field = new Field(pucks, paddle, opponentPaddle, 1);
        assertNotNull(field.getGoals());
    }

    @Test
    void testPaintNoGoals() {
        ScoreCount.getInstance().resetScore();
        Paddle paddle = new Paddle(new GameVector(0, 0), new GameVector(0, 0), 1, 50, 50);
        Paddle opponentPaddle = new Paddle(new GameVector(0, 0), new GameVector(0, 0), 1, 50, 50);
        ArrayList<Puck> pucks = new ArrayList<>();
        pucks.add(new Puck(new GameVector(12, 12), new GameVector(12, 12), 1, 50));
        Field field = new Field(pucks, paddle, opponentPaddle, 1);
        Graphics g = Mockito.mock(Graphics.class);
        field.paintComponent(g);
        verify(g).drawString("goals: " + ScoreCount.getInstance().getPlayer2(), 120, 20);
    }

    @Test
    void testPaintWinOne() {
        ScoreCount.getInstance().resetScore();
        ScoreCount.getInstance().winOne();
        Paddle paddle = new Paddle(new GameVector(0, 0), new GameVector(0, 0), 1, 50, 50);
        Paddle opponentPaddle = new Paddle(new GameVector(0, 0), new GameVector(0, 0), 1, 50, 50);
        ArrayList<Puck> pucks = new ArrayList<>();
        pucks.add(new Puck(new GameVector(12, 12), new GameVector(12, 12), 1, 50));
        Field field = new Field(pucks, paddle, opponentPaddle, 1);
        Graphics g = Mockito.mock(Graphics.class);
        field.paintComponent(g);
        verify(g).drawString("You Win", 50, 290);
    }

    @Test
    void testPaintTwo() {
        ScoreCount.getInstance().resetScore();
        ScoreCount.getInstance().winTwo();
        Paddle paddle = new Paddle(new GameVector(0, 0), new GameVector(0, 0), 1, 50, 50);
        Paddle opponentPaddle = new Paddle(new GameVector(0, 0), new GameVector(0, 0), 1, 50, 50);
        ArrayList<Puck> pucks = new ArrayList<>();
        pucks.add(new Puck(new GameVector(12, 12), new GameVector(12, 12), 1, 50));
        Field field = new Field(pucks, paddle, opponentPaddle, 1);
        Graphics g = Mockito.mock(Graphics.class);
        field.paintComponent(g);
        verify(g).drawString("You Lose!", 40, 290);
    }

    @Test
    void testPaintNullOpponentPaddle() {
        ScoreCount.getInstance().resetScore();
        Paddle paddle = new Paddle(new GameVector(0, 0), new GameVector(0, 0), 1, 50, 50);
        Paddle opponentPaddle = null;
        ArrayList<Puck> pucks = new ArrayList<>();
        pucks.add(new Puck(new GameVector(12, 12), new GameVector(12, 12), 1, 50));
        Field field = new Field(pucks, paddle, opponentPaddle, 1);
        Graphics g = Mockito.mock(Graphics.class);
        field.paintComponent(g);
        verify(g).drawString("goals: " + ScoreCount.getInstance().getPlayer2(), 120, 20);
    }

}
