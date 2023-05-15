package view;

import model.PlayerColor;

import javax.swing.*;
import java.awt.*;

public class CellComponent extends JPanel {
    private Color background;

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
        g.fillRect(1, 1, this.getWidth()-1, this.getHeight()-1);
    }
}
