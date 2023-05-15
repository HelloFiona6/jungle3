package view.animalsChessComponent;

import model.PlayerColor;

import javax.swing.*;
import java.awt.*;

public class LionChessComponent extends JComponent {
    private PlayerColor owner;

    private boolean selected;
    private ImageIcon image;

    public LionChessComponent(PlayerColor owner, int size) {
        this.owner = owner;
        this.selected = false;
        setSize(size/2, size/2);
        setLocation(0,0);
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
        //换成图片
        //image=new ImageIcon(getClass().getResource("/ChessImage/Lion.png"));
        image=new ImageIcon("resource\\ChessImage\\Lion.png");

        g.drawImage(image.getImage(),0,0,getWidth(),getHeight(), this);
    }

}
