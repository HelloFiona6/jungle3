package view;

import model.PlayerColor;

import javax.swing.*;
import java.awt.*;

/**
 * This is the equivalent of the Cell class,
 * but this class only cares how to draw Cells on ChessboardComponent
 */

public class ChessComponent extends JComponent {//每个棋子的父类

    private PlayerColor owner;//蓝方 or 红方

    private boolean selected;//是否被选上

    public ChessComponent(PlayerColor owner, int size) {//size是固定的
        this.owner = owner;//确定哪一方
        this.selected = false;
        setSize(size/2, size/2);
        setLocation(0,0);
        setVisible(true);//可见


//        //随着鼠标动发生颜色的变化
//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseEntered(MouseEvent e) {
//                if (hoverListener != null) {
//                    hoverListener.onHovered(Component.this);
//                }
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//                if (hoverListener != null) {
//                    hoverListener.onExited(Component.this);
//                }
//            }
//
//            @Override
//            public void mousePressed(MouseEvent e) {
//                // Trigger the click event in ChessboardComponent
//                getParent().dispatchEvent(new MouseEvent(getParent(), e.getID(), e.getWhen(), e.getModifiersEx(), getLocation().x + e.getX(), getLocation().y + e.getY(), e.getClickCount(), e.isPopupTrigger(), e.getButton()));
//            }
//        });
    }
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    //区别红蓝方——背景画圈
    protected void paintComponent(Graphics g){
        super.paintComponents(g);

        if (owner == PlayerColor.BLUE) {
            g.setColor(Color.BLUE);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
        else if (owner == PlayerColor.RED) {
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
        //选中变黄
        if(isSelected()){
            g.setColor(Color.ORANGE);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
    }

}
