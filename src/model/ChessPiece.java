package model;


import java.io.Serializable;

public abstract class ChessPiece implements Serializable
{
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
            return false;
        } else {
            return false;
        }
    }//可以捕获方法，不考虑河道影响，只考虑等级关系及不能吃自己的棋（包括特殊等级捕食关系）



public  boolean ratinriver(){
        return  false;//没做完
}
    public void setRank(int x ) {
        rank=x;
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

    public void setRank(boolean x) {
        if (x == true) {
            rank = 0;
        }

    }
}
