/*package model;

import java.io.Serializable;

//如果撤销怎么办？
public class Step implements Serializable {
    private ChessPiece chess;
    private ChessPiece eatenChess;
    private int turn;
    private PlayerColor owner;
    private ChessboardPoint from;
    private ChessboardPoint to;

    public Step(ChessboardPoint from,ChessboardPoint to,int turn,PlayerColor owner){
        this.turn=turn;
        this.to=to;
        this.from=from;
        this.owner=owner;;
    }


    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public ChessPiece getChess() {
        return chess;
    }

    public void setChess(ChessPiece chess) {
        this.chess = chess;
    }

    public ChessPiece getEatenChess() {
        return eatenChess;
    }

    public void setEatenChess(ChessPiece eatenChess) {
        this.eatenChess = eatenChess;
    }

    public PlayerColor getOwner() {
        return owner;
    }

    public void setOwner(PlayerColor owner) {
        this.owner = owner;
    }

    public ChessboardPoint getTo() {
        return to;
    }

    public void setTo(ChessboardPoint to) {
        this.to = to;
    }

    public ChessboardPoint getFrom() {
        return from;
    }

    public void setFrom(ChessboardPoint from) {
        this.from = from;
    }

    @Override
    public String toString() {
        String o;
        if(owner.equals(PlayerColor.BLUE)){
            o="0";
        }else{
            o="1";
        }
        return o+" "+from+" "+to+" "+turn;
    }

}*/
package model;

        import java.io.Serializable;

//如果撤销怎么办？
public class Step implements Serializable {
    private int turn;
    private PlayerColor owner;
    private ChessboardPoint from;
    private ChessboardPoint to;
    private ChessPiece chess;
    private ChessPiece eaten;
    private ChessPiece eatenChess;

    public Step(ChessboardPoint from,ChessboardPoint to,int turn,PlayerColor owner,ChessPiece chess,ChessPiece eaten){
        this.turn=turn;
        this.to=to;
        this.from=from;
        this.owner=owner;
        this.chess=chess;
        this.eaten=eaten;
    }

    public int getTurn() {
        return turn;
    }

    public ChessPiece getChess() {
        return chess;
    }


    public ChessPiece getEaten() {
        return eaten;
    }

    public void setEaten(ChessPiece eaten) {
        this.eaten = eaten;
    }

    public void setChess(ChessPiece chess) {
        this.chess = chess;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public PlayerColor getOwner() {
        return owner;
    }

    public void setOwner(PlayerColor owner) {
        this.owner = owner;
    }

    public ChessboardPoint getTo() {
        return to;
    }

    public void setTo(ChessboardPoint to) {
        this.to = to;
    }

    public ChessboardPoint getFrom() {
        return from;
    }

    public void setFrom(ChessboardPoint from) {
        this.from = from;
    }

    @Override
    public String toString() {
        String o;
        if(owner.equals(PlayerColor.BLUE)){
            o="0";
        }else{
            o="1";
        }
        return o+" "+from+" "+to+" "+turn;
    }

    public ChessPiece getEatenChess() {
        return eatenChess;
    }
}