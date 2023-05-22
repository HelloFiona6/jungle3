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
    private final Set<ChessboardPoint> river = new HashSet<>();
    private final Set<ChessboardPoint> trap = new HashSet<>();
    private final Set<ChessboardPoint> dens = new HashSet<>();

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
        river.add(new ChessboardPoint(3, 1));
        river.add(new ChessboardPoint(3, 2));
        river.add(new ChessboardPoint(4, 1));
        river.add(new ChessboardPoint(4, 2));
        river.add(new ChessboardPoint(5, 1));
        river.add(new ChessboardPoint(5, 2));

        river.add(new ChessboardPoint(3, 4));
        river.add(new ChessboardPoint(3, 5));
        river.add(new ChessboardPoint(4, 4));
        river.add(new ChessboardPoint(4, 5));
        river.add(new ChessboardPoint(5, 4));
        river.add(new ChessboardPoint(5, 5));

        trap.add(new ChessboardPoint(0, 2));
        trap.add(new ChessboardPoint(0, 4));
        trap.add(new ChessboardPoint(1, 3));
        trap.add(new ChessboardPoint(7, 3));
        trap.add(new ChessboardPoint(8, 2));
        trap.add(new ChessboardPoint(8, 4));

        dens.add(new ChessboardPoint(0, 3));
        dens.add(new ChessboardPoint(8, 3));
    }

    public void initPieces() {//初始化每个位置应该放的物品
        grid[2][6].setPiece(new ElephantChessPiece(PlayerColor.BLUE, "Elephant", 8));
        grid[0][0].setPiece(new LionChessPiece(PlayerColor.BLUE, "Lion", 7));
        grid[0][6].setPiece(new TigerChessPiece(PlayerColor.BLUE, "Tiger", 6));
        grid[2][2].setPiece(new LeopardChessPiece(PlayerColor.BLUE, "Leopard", 5));
        grid[2][4].setPiece(new WolfChessPiece(PlayerColor.BLUE, "Wolf", 4));
        grid[1][1].setPiece(new DogChessPiece(PlayerColor.BLUE, "Dog", 3));
        grid[1][5].setPiece(new CatChessPiece(PlayerColor.BLUE, "Cat", 2));
        grid[2][0].setPiece(new RatChessPiece(PlayerColor.BLUE, "Rat", 1));

        grid[6][0].setPiece(new ElephantChessPiece(PlayerColor.RED, "Elephant", 8));
        grid[8][6].setPiece(new LionChessPiece(PlayerColor.RED, "Lion", 7));
        grid[8][0].setPiece(new TigerChessPiece(PlayerColor.RED, "Tiger", 6));
        grid[6][4].setPiece(new LeopardChessPiece(PlayerColor.RED, "Leopard", 5));
        grid[6][2].setPiece(new WolfChessPiece(PlayerColor.RED, "Wolf", 4));
        grid[7][5].setPiece(new DogChessPiece(PlayerColor.RED, "Dog", 3));
        grid[7][1].setPiece(new CatChessPiece(PlayerColor.RED, "Cat", 2));
        grid[6][6].setPiece(new RatChessPiece(PlayerColor.RED, "Rat", 1));
    }

    //读取文件时候用
    public void initPieces(List<String> lines) {

    }

    public void removeAllPiece() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (grid[i][j].getPiece() != null) {
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

    // public boolean isValidMove(ChessboardPoint target){
    // }
    //todo isValidMove 在Chesspiece里写了个抽象方法

    public boolean isValidMove(ChessboardPoint src, ChessboardPoint dest) {
        ChessPiece piecesrc = getChessPieceAt(src);
        //ChessPiece piecedest = getChessPieceAt(dest);
        boolean canmove = false;
        if (src == null) {
            canmove = false;
        }
        if (src != null && dest != null) {
            boolean acrossriver = false;
            boolean ratinriver = false;
            boolean t = true;
            for (int i = Math.min(src.getRow(), dest.getRow()) + 1; i < Math.max(src.getRow(), dest.getRow()); i++) {
                for (int j = Math.min(src.getCol(), dest.getCol()); j < Math.max(src.getCol(), dest.getCol()); j++) {
                    if ((i == 3 && j == 1) || (i == 3 && j == 2) || (i == 4 && j == 1) || (i == 4 && j == 2) || (5 == 3 && j == 1) || (i == 5 && j == 2) || (i == 3 && j == 4) || (i == 3 && j == 5) || (i == 4 && j == 4) || (i == 4 && j == 5) || (i == 5 && j == 4) || (i == 5 && j == 5)) {
                        acrossriver = true;
                    } else {
                        acrossriver = false;
                    }
                }
            }
            for (int i = Math.min(src.getRow(), dest.getRow()) + 1; i < Math.max(src.getRow(), dest.getRow()); i++) {
                for (int j = Math.min(src.getCol(), dest.getCol()); j < Math.max(src.getCol(), dest.getCol()); j++) {
                    if (grid[i][j] != null) {
                        ratinriver = true;
                    } else {
                        ratinriver = false;
                    }
                }
            }
            if (calculateDistance(src, dest) == 1) {
                if (piecesrc.getName() == "Rat") {
                    canmove = true;
                } else {
                    if (acrossriver == false) {
                        canmove = true;
                    } else {
                        canmove = false;
                    }
                }
            } else if (calculateDistance(src, dest) == 3 || calculateDistance(src, dest) == 5) {
                if (piecesrc.getName() != "Lion" && piecesrc.getName() != "Tiger") {
                    canmove = false;
                } else {
                    if (ratinriver == true) {
                        canmove = false;
                    } else {
                        canmove = true;
                    }
                }
            }
        }
        if (src != null && dest == null) {
            canmove =false;
           // canmove = isValidCapture(src, dest);
        }
        return canmove;

    }

    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest) {
        // TODO:Fix this method
        // return isValidMove(src, dest);

        ChessPiece piecesrc = getChessPieceAt(src);
        ChessPiece piecedest = getChessPieceAt(dest);
        boolean acrossriver = true;
        boolean ratinriver =true;
        boolean t = true;
        for (int i = Math.min(src.getRow(), dest.getRow()) + 1; i < Math.max(src.getRow(), dest.getRow()); i++) {
            for (int j = Math.min(src.getCol(), dest.getCol()); j < Math.max(src.getCol(), dest.getCol()); j++) {
                if ((i == 3 && j == 1) || (i == 3 && j == 2) || (i == 4 && j == 1) || (i == 4 && j == 2) || (5 == 3 && j == 1) || (i == 5 && j == 2) || (i == 3 && j == 4) || (i == 3 && j == 5) || (i == 4 && j == 4) || (i == 4 && j == 5) || (i == 5 && j == 4) || (i == 5 && j == 5)) {
                    acrossriver = true;
                } else {
                    acrossriver = false;
                }
            }
        }



        for (int i = Math.min(src.getRow(), dest.getRow()) + 1; i < Math.max(src.getRow(), dest.getRow()); i++) {
            for (int j = Math.min(src.getCol(), dest.getCol()); j < Math.max(src.getCol(), dest.getCol()); j++) {
                if (grid[i][j] != null) {
                    ratinriver = true;
                } else {
                    ratinriver = false;
                }
            }
        }

        if ((src.getCol() != dest.getCol() && src.getRow() != dest.getRow()) || (src.getCol() == dest.getCol() && src.getRow() == dest.getRow()) || src.getCol() > 6 || dest.getCol() > 6 || src.getRow() > 8 || dest.getRow() > 8) {
            return false;
        } else {
          /*  if (src == null) {
                t = false;
            }

            if (src != null && dest == null) {
                if (calculateDistance(src, dest) == 1) {
                    if (piecesrc.getName() == "Rat") {
                        t = true;
                    } else {
                        if (acrossriver == false) {
                            t = true;
                        } else {
                            t = false;
                        }
                    }
                } else
                 if (calculateDistance(src, dest) == 3 || calculateDistance(src, dest) == 5) {

                    if (piecesrc.getName() != "Lion" && piecesrc.getName() != "Tiger") {
                        t = false;
                    } else {
                        if (ratinriver == true) {
                            t = false;
                        } else {
                            t = true;
                        }
                    }
                }
            }
*/

            if (src != null && dest != null) {
                if (piecedest.canCapture(piecedest) == false) {
                    t = false;
                } else {
                    if (calculateDistance(src, dest) == 1) {
                        if (piecesrc.getName() == "Rat") {
                            t = true;
                        } else {
                            if (acrossriver == false) {
                                t = true;
                            } else {
                                t = false;
                            }
                        }
                    } else if (calculateDistance(src, dest) == 3 || calculateDistance(src, dest) == 5) {
                        if (piecesrc.getName() != "Lion" && piecesrc.getName() != "Tiger") {
                            t = false;
                        } else {
                            if (ratinriver == true) {
                                t = false;
                            } else {
                                t = true;
                            }
                        }
                    }

                }
                //Todo
            }
        }
        return t;

    }
    public boolean inDens(){

        if(grid[0][3]==null&&grid[8][3]==null){
            return false;
        }else{
           return true;
    }}
    public boolean inTrap() {
        if (grid[0][2] == null && grid[0][4] == null && grid[1][3] == null && grid[8][2] == null && grid[8][4] == null && grid[7][3] == null ) {
            return false;
        } else {
        /*    if (grid[0][3].getPiece().getOwner()==PlayerColor.RED&&grid[8][3]==null){
                tfinDens = true;
            }
            if (grid[8][3].getPiece().getOwner()==PlayerColor.BLUE&&grid[0][3]==null){
                tfinDens =true;
            }
            if(grid[8][3]!=null&&grid[0][3]!=null){
                tfinDens = true;
            }
        }*/
            return true;
        }
    }
}