package view.image;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {
    private Image backgroundImage;
    public ImagePanel(ImageIcon icon){
        backgroundImage=icon.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    public void setBackgroundImage(ImageIcon image) {
        this.backgroundImage = image.getImage();
        this.repaint();
    }
}
