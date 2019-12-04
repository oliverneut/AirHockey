package gamepackage;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import field.Frame;


public class Game extends JFrame {

    public static Puck puck;
    public static Frame frame;
    private static final long serialVersionUID = 4714318125998709253L;

    /**
     * Game Class main method.
     * @param args The command line arguments.
     * @throws InterruptedException Checks if thread has been interrupted.
     */
    public static void main(String[] args) throws InterruptedException {

        GUI gui = new GUI();
        gui.titleScreen();

        puck = frame.getPuck();


        while (true) {
            puck.move(frame);
            Thread.sleep(10);
        }
    }


}

