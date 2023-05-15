package view;

import model.PlayerColor;

public class ChessComponent {
    private PlayerColor owner;

    private boolean selected;
    public ChessComponent(PlayerColor owner){
        this.owner=owner;
        this.selected=false;
    }
}
