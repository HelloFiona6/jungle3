package view.animalsChessComponent;

import model.PlayerColor;
import view.ChessComponent;

import javax.swing.*;
import java.awt.*;

public class LionChessComponent extends ChessComponent {
    private ImageIcon image;

    public LionChessComponent(PlayerColor owner, int size) {
        super(owner,size);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        image=new ImageIcon("resource\\ChessImage\\Lion.png");
        g.drawImage(image.getImage(),0,0,getWidth(),getHeight(), this);
    }

}
