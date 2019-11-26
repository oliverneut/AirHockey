package Field;

import javax.swing.*;
import java.awt.*;


public class Field extends JPanel{

    private Image fieldImage;

    public Field() {
        createField();
    }

    public void getImage() {
        fieldImage = new ImageIcon("src/main/java/assets/airHockey.png").getImage();
    }

    public void createField() {
        getImage();
        int w = fieldImage.getWidth(this);
        int h = fieldImage.getHeight(this);
        setPreferredSize(new Dimension(h, w));
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(fieldImage, 0, 0, null);
    }

}
