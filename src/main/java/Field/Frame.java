package Field;

import javax.swing.JFrame;

public class Frame extends JFrame{

    public Frame() {

        createFrame();
    }

    private void createFrame() {

        add(new Field());
        setTitle("Board One");
        setSize(320, 640);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
