package model;

public class CatChessPiece extends ChessPiece{
    public CatChessPiece(PlayerColor owner, String name, int rank){
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