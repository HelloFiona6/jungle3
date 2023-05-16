package model;

public class LionChessPiece extends ChessPiece{
    public LionChessPiece(PlayerColor owner, String name, int rank){
        super(owner,name,rank);
    }
    @Override
    public boolean isValidMove(ChessboardPoint target) {
        //todo
        return false;
    }

    @Override
    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest) {
        return false;
    }
}
