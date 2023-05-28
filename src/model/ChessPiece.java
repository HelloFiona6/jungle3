package model;


import java.io.Serializable;

public abstract class ChessPiece implements Serializable {
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
        if(target.getOwner().equals(owner)){
            return false;
        }
        if (rank >= target.rank && rank != 8) {
            return true;
        } else if (rank == 8 && target.rank != 1) {
            return true;
        } else if (rank == 1 && target.rank == 8 ) {
            return true;
        } else {
            return false;
        }
    }
    public  boolean ratinriver(){
        return  false;//没做完
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
        if(x) {
            rank=0;
        }
    }
    public void setRank(int x ) {
        rank=x;
    }
}
