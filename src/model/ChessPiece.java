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
        if(rank == 8 && target.rank != 1){
            return false;
        }
        if (rank == 1 && target.rank == 8 ) {
            return true;
        }
        if (rank >= target.rank) {
            return true;
        }
        return false;
    }
    public  boolean ratinriver(){
        return  false;//没做完
    }

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
