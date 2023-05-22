package model.ChessPieces;

import model.ChessPiece;
import model.ChessboardPoint;
import model.PlayerColor;

public class LeopardChessPiece extends ChessPiece {
    public LeopardChessPiece(PlayerColor owner, String name, int rank){
        super(owner,name,rank);
    }
   /* @Override
    public boolean isValidMove(ChessboardPoint target) {
        //todo
        return true;
    }
*/
    @Override
    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest) {
        return false;
    }
}
