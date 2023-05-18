package model;

import model.ChessPieces.*;
import view.ChessComponent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class store the real chess information.
 * The Chessboard has 9*7 cells, and each cell has a position for chess
 */
public class Chessboard {
    private Cell[][] grid;
    private  final Set<ChessboardPoint> river=new HashSet<>();
    private  final Set<ChessboardPoint> trap=new HashSet<>();
    private  final Set<ChessboardPoint> dens=new HashSet<>();

    public Chessboard() {
        this.grid = new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];//19X19
        initGrid();
        initPieces();
    }

    //ok
    private void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();
            }
        }
    }
    private void initSets() {//地形初始化
        river.add(new ChessboardPoint(3,1));
        river.add(new ChessboardPoint(3,2));
        river.add(new ChessboardPoint(4,1));
        river.add(new ChessboardPoint(4,2));
        river.add(new ChessboardPoint(5,1));
        river.add(new ChessboardPoint(5,2));

        river.add(new ChessboardPoint(3,4));
        river.add(new ChessboardPoint(3,5));
        river.add(new ChessboardPoint(4,4));
        river.add(new ChessboardPoint(4,5));
        river.add(new ChessboardPoint(5,4));
        river.add(new ChessboardPoint(5,5));

        trap.add(new ChessboardPoint(0,2));
        trap.add(new ChessboardPoint(0,4));
        trap.add(new ChessboardPoint(1,3));
        trap.add(new ChessboardPoint(7,3));
        trap.add(new ChessboardPoint(8,2));
        trap.add(new ChessboardPoint(8,4));

        dens.add(new ChessboardPoint(0,3));
        dens.add(new ChessboardPoint(8,3));
    }

    public void initPieces() {//初始化每个位置应该放的物品
        grid[2][6].setPiece(new ElephantChessPiece(PlayerColor.BLUE, "Elephant",8));
        grid[0][0].setPiece(new LionChessPiece(PlayerColor.BLUE, "Lion",7));
        grid[0][6].setPiece(new TigerChessPiece(PlayerColor.BLUE, "Tiger",6));
        grid[2][2].setPiece(new LeopardChessPiece(PlayerColor.BLUE, "Leopard",5));
        grid[2][4].setPiece(new WolfChessPiece(PlayerColor.BLUE, "Wolf",4));
        grid[1][1].setPiece(new DogChessPiece(PlayerColor.BLUE, "Dog",3));
        grid[1][5].setPiece(new CatChessPiece(PlayerColor.BLUE, "Cat",2));
        grid[2][0].setPiece(new RatChessPiece(PlayerColor.BLUE, "Rat",1));

        grid[6][0].setPiece(new ElephantChessPiece(PlayerColor.RED, "Elephant",8));
        grid[8][6].setPiece(new LionChessPiece(PlayerColor.RED, "Lion",7));
        grid[8][0].setPiece(new TigerChessPiece(PlayerColor.RED, "Tiger",6));
        grid[6][4].setPiece(new LeopardChessPiece(PlayerColor.RED, "Leopard",5));
        grid[6][2].setPiece(new WolfChessPiece(PlayerColor.RED, "Wolf",4));
        grid[7][5].setPiece(new DogChessPiece(PlayerColor.RED, "Dog",3));
        grid[7][1].setPiece(new CatChessPiece(PlayerColor.RED, "Cat",2));
        grid[6][6].setPiece(new RatChessPiece(PlayerColor.RED, "Rat",1));
    }

    //读取文件时候用
    public void initPieces(List<String> lines){

    }
    public void removeAllPiece(){
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                if(grid[i][j].getPiece()!=null){
                    grid[i][j].setPiece(null);
                }
            }
        }
    }

    private ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }

    private Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }

    private int calculateDistance(ChessboardPoint src, ChessboardPoint dest) {
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
    }

    private ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();
        return chessPiece;
    }

    private void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
    }

    public void moveChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidMove(src, dest)) {
            throw new IllegalArgumentException("Illegal chess move!");
        }
        setChessPiece(dest, removeChessPiece(src));
        //System.out.printf("%s moved from [%d,%d] to [%d,%d]", ChessComponent.,src.getRow(),src.getCol(),dest.getRow(),dest.getCol());
    }

    public void captureChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (isValidCapture(src, dest)) {
            throw new IllegalArgumentException("Illegal chess capture!");
        }
        // TODO: Finish the method.
    }

    public Cell[][] getGrid() {
        return grid;
    }
    public PlayerColor getChessPieceOwner(ChessboardPoint point) {
        return getGridAt(point).getPiece().getOwner();
    }


    //todo isValidMove 在Chesspiece里写了个抽象方法
    public boolean isValidMove(ChessboardPoint src, ChessboardPoint dest) {
        if (getChessPieceAt(src) == null || getChessPieceAt(dest) != null) {
            return false;
        }
        return calculateDistance(src, dest) == 1;
        //Todo
    }


    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest) {
        // TODO:Fix this method
        return false;
    }
}
