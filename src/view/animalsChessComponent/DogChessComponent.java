package view.animalsChessComponent;


import model.PlayerColor;
import view.ChessComponent;

import javax.swing.*;
import java.awt.*;

/**
 * This is the equivalent of the ChessPiece class,
 * but this class only cares how to draw Chess on ChessboardComponent
 */
public class DogChessComponent extends ChessComponent {
    private PlayerColor owner;

    private boolean selected;
    private ImageIcon image;

    public DogChessComponent(PlayerColor owner, int size) {
        super(owner,size);
        setSize(size / 2, size / 2);
        setLocation(2, 6);
        setVisible(true);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        image=new ImageIcon("resource\\ChessImage\\Dog.png");
        g.drawImage(image.getImage(),0,0,getWidth(),getHeight(), this);
    }
}
