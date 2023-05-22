package model;


public abstract class ChessPiece {
    // the owner of the chess
    private PlayerColor owner;
    // Elephant? Cat? Dog? ...
    private String name;
    private int rank;
    public ChessPiece(PlayerColor owner, String name, int rank) {
        this.owner = owner;
        this.name = name;
        this.rank = rank;
    }
    public boolean canCapture(ChessPiece target) {
        // TODO Finish this method! **finish
        //if(Chessboard.isValidMove(this.name,target))
        if(rank>= target.rank&&rank!=8){
            return true;
        }else if(rank==8&&target.rank!=1) {
            return true;
        }else{
            return false;
        }
    }

   // public abstract boolean isValidMove(ChessboardPoint target);
    public abstract boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest);
    //todo 这里isValidCapture传参可以改，我随便写的
    public String getName() {
        return name;
    }

   public int getRank() {
        return rank;
    }

    public PlayerColor getOwner() {
        return owner;
    }

    public void setRank(boolean x ) {
        if(x==true) {
            rank=0;
        }

    }
}
