package view.animalsChessComponent;


import model.PlayerColor;
import view.ChessComponent;

import javax.swing.*;
import java.awt.*;

/**
 * This is the equivalent of the ChessPiece class,
 * but this class only cares how to draw Chess on ChessboardComponent
 */
public class RatChessComponent extends ChessComponent {
    private ImageIcon image;

    public RatChessComponent(PlayerColor owner, int size) {
        super(owner, size);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        image=new ImageIcon("resource\\ChessImage\\Rat.png");
        g.drawImage(image.getImage(),0,0,getWidth(),getHeight(), this);
    }
}
