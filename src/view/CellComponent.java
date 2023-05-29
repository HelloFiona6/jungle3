package view;

import model.PlayerColor;

import javax.swing.*;
import java.awt.*;

public class CellComponent extends JPanel {
    private Color background;
    private ImageIcon image;
    private boolean validMove;
    private Color color;

    public CellComponent(Color background, Point location, int size){
        setLayout(new GridLayout(1, 1));
        setLocation(location);
        setSize(size, size);
        this. background= background;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.setColor(background);
        g.fillRect(1, 1, this.getWidth() - 1, this.getHeight() - 1);
        if(image!=null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }
    public void setValidMove(boolean validMove) {
        this.validMove = validMove;
    }

    @Override
    public Color getBackground() {
        return background;
    }

    @Override
    public void setBackground(Color background) {
        this.background = background;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
