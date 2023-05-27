package model;

import model.ChessPieces.*;
import view.ChessComponent;

import java.util.HashSet;
import java.util.Objects;
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
        initSets();
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
    public void runStep(Step step) {
        ChessboardPoint to=step.getTo();
        ChessboardPoint from=step.getFrom();
        ChessPiece chess=step.getChess();
        setChessPiece(from,null);
        setChessPiece(to,chess);
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

    private void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {//在某个点上放了某个棋子
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
    public boolean acrossriver(ChessboardPoint src, ChessboardPoint dest) {
        boolean t = true;
        for (int i = Math.min(src.getRow(), dest.getRow()+row1(src,dest)); i < Math.max(src.getRow(), dest.getRow()); i++) {
            for (int j = Math.min(src.getCol(), dest.getCol()+col1(src,dest)); j < Math.max(src.getCol(), dest.getCol()); j++) {
                if ((i == 3 && j == 1) || (i == 3 && j == 2) || (i == 4 && j == 1) || (i == 4 && j == 2) || (i==5 && j == 1) || (i == 5 && j == 2) || (i == 3 && j == 4) || (i == 3 && j == 5) || (i == 4 && j == 4) || (i == 4 && j == 5) || (i == 5 && j == 4) || (i == 5 && j == 5)) {
                    t = true;
                } else {
                    t = false;
                }
            }
        }
        return t;
    }


    public boolean acrossallriver(ChessboardPoint src, ChessboardPoint dest) {
        boolean t = true;
        for (int i = Math.min(src.getRow(), dest.getRow()+row1(src,dest)) ; i < Math.max(src.getRow(), dest.getRow()); i++) {
            for (int j = Math.min(src.getCol(), dest.getCol()+col1(src,dest)); j < Math.max(src.getCol(), dest.getCol()); j++) {
                if (((i == 3 && j == 1) || (i == 3 && j == 2)) || ((i == 4 && j == 1) || (i == 4 && j == 2)) || ((i == 5 && j == 1)
                        || (i == 5 && j == 2)) || ((i == 3 && j == 4) ||(i == 3 && j == 5)) || ((i == 4 && j == 4) || (i == 4 && j == 5))
                        || ((i == 5 && j == 4) || (i == 5 && j == 5))  || (i == 4 && j == 4) && ((i == 5 && j == 4)) || ((i == 3 && j == 5)) && (i == 4 && j == 5) && (i == 5 && j == 5)) {
                    t = true;
                } else {
                    t = false;
                }
            }
        }
        return t;
    }

    public boolean aroundriver(ChessboardPoint src, ChessboardPoint dest) {
        boolean t = true;
        for (int i = Math.min(src.getRow(), dest.getRow()+row1(src,dest)); i < Math.max(src.getRow(), dest.getRow()); i++) {
            for (int j = Math.min(src.getCol(), dest.getCol()+col1(src,dest)); j < Math.max(src.getCol(), dest.getCol()); j++) {
                if ( (i == 3  && j == 0) ||(i == 2  && j == 1) ||(i == 2  && j == 0)  || (i == 3 && j == 3) ||(i == 2 && j == 2) ||(i == 2&& j == 3) ||(i == 4 && j == 0) ||(i == 5&& j == 1) ||(i == 5 && j == 0) ||(i == 6 && j == 1) ||(i == 6 && j == 0) || (i == 4 && j == 3)  ||(i == 5 && j == 3) ||(i == 6 && j == 2) ||(i == 6 && j == 3) || (i == 6 && j == 4) ||  (i == 2 && j == 4) || (i == 3 && j == 6) ||(i == 2 && j == 5) ||(i == 2 && j == 6) || (i == 4 && j == 6) ||     (i == 5 && j == 6)||   (i == 6 && j == 5)||   (i == 6 && j == 6)) {
                    t = true;
                } else {
                    t = false;
                }
            }
        }
        return t;
    }

    public boolean ratinriver(ChessboardPoint src, ChessboardPoint dest) {
        boolean t = true;
        for (int i = Math.min(src.getRow(), dest.getRow()+row1(src,dest)) + 1; i < Math.max(src.getRow(), dest.getRow()); i++) {
            for (int j = Math.min(src.getCol(), dest.getCol()+col1(src,dest)); j < Math.max(src.getCol(), dest.getCol()); j++) {
                if (!grid[i][j].getPiece().getName().equals("Rat")) {
                    t = true;
                } else {
                    t = false;
                }
            }
        }
        return t;
    }
    public int row1(ChessboardPoint src, ChessboardPoint dest){
        int srcR=src.getRow();
        int srcC=src.getCol();
        int destC=dest.getCol();
        int destR=dest.getRow();
        if( srcC==destC && srcR<destR) {
            return 1;//在一条竖直线上且向下跳
        }else if(srcC==destC && srcR>destR){
            return -1;
        }else{
            return 0;
        }
    }

    public int col1(ChessboardPoint src, ChessboardPoint dest){
        int srcR=src.getRow();
        int srcC=src.getCol();
        int destC=dest.getCol();
        int destR=dest.getRow();
        if(srcC==destC && destR>srcR) {
            return 1;//在一条水平线上
        }else if(srcC==destC && destR<srcR){
            return -1;
        }else{
            return 0;
        }
//        int col1;
//        if(src.getRow()== dest.getRow()&&src.getCol()!=dest.getCol()){
//            col1=1;//在一条水平线上
//        }else{
//            col1=0;
//        }
//        return col1;
    }
    // public boolean isValidMove(ChessboardPoint src, ChessboardPoint dest) {
    //    if (getChessPieceAt(src) == null || getChessPieceAt(dest) != null) {
    //        return false;
    ///    }//确保src有棋子，dest无棋子
    //    return specialMove(src,dest) && densMove(src,dest);
    // }
    public boolean isValidMove(ChessboardPoint src, ChessboardPoint dest) {
        ChessPiece piecesrc = getChessPieceAt(src);
        ChessPiece piecedest = getChessPieceAt(dest);
        int distance=calculateDistance(src, dest);
//        boolean canmove = false;

        //排除不成立选项，剩下来的情况只有向空白处能不能移动
        if (piecesrc == null || piecedest != null) {
            return false;
        }
        //如果是河，且是老鼠，间距==1 return true
        if(river.contains(dest)){
            if(!piecesrc.getName().equals("Rat")) return false;
            if (distance == 1) return true;
        }
        //狮子老虎可以跳河
        /*
        必须是同一行列的格子，且中间只能有河
        上面有老鼠就不能跳
        对面有棋子不能挑（上面已经排除了）
         */
        if(distance>1 && (piecesrc.getName().equals("Lion") || piecesrc.getName().equals("Tiger"))){
            if(row1(src,dest)==0||col1(src,dest)==0) return false;

            for (int i = 1; i < 4; i++) {
                int temp=src.getRow()+row1(src,dest)*i;
                ChessboardPoint c=new ChessboardPoint(temp,src.getCol());
                if(!river.contains(c)) return false;
                if(getChessPieceAt(c)!=null) return false;
            }
            for (int i = 1; i < 3; i++) {
                int temp=src.getCol()+col1(src,dest)*i;
                ChessboardPoint c=new ChessboardPoint(src.getRow(),temp);
                if(!river.contains(c)) return false;
                if(getChessPieceAt(c)!=null) return false;
            }
//            if(col1(src,dest)==1) {
//                for (int i = 0; i <3; i++) {
//                    if(!river.contains(new ChessboardPoint(src.getRow(),src.getCol()+i))){
//                        return false;
//                    }
//                }
//            } else if (col1(src,dest)==-1) {
//                for (int i = 0; i <3; i++) {
//                    if(!river.contains(new ChessboardPoint(src.getRow(),src.getCol()-i))){
//                        return false;
//                    }
//                }
//            }
            }
        //不能走到自己的dens里
        if(getChessPieceAt(src).getOwner().equals(getGridAt(dest).getOwner()) && dens.contains(dest)){
            return false;
        }
        return distance==1;
//        if ((src.getCol() != dest.getCol() && src.getRow() != dest.getRow()) || (src.getCol() == dest.getCol() && src.getRow() == dest.getRow()) || src.getCol() > 6 || dest.getCol() > 6 || src.getRow() > 8 || dest.getRow() > 8) {
//            canmove = false;
//        }
//            boolean t = true;
//            if (calculateDistance(src, dest) == 1) {
//                if (piecesrc.getName().equals("Rat")) {
//                    canmove = true;
//                } else {
//                    if (acrossriver(src, dest) == false) {
//                        canmove = true;
//                    } else {
//                        canmove = false;
//                    }
//                }
//            } else if (calculateDistance(src, dest) == 3 || calculateDistance(src, dest) ==4 ) {
//                if (!piecesrc.getName().equals("Lion") || !piecesrc.getName().equals("Tiger")) {
//                    canmove = false;
//                } else {
//                    if (ratinriver(src, dest) == true) {
//                        canmove = false;
//                    } else {
//                        if (aroundriver(src, dest) == true) {
//                            canmove = true;
//                        } else {
//                            canmove = false;
//                        }
//                    }
//                }
//            }else{
//                canmove =false;
//            }
    }
    public ChessPiece getChessPieceAt(ChessboardPoint point){
        return getGridAt(point).getPiece();
    }
    public boolean isValidMove(ChessPiece chess,ChessboardPoint point){
        int[] x={1,0,-1,0};
        int[] y={0,-1,0,1};
        for (int i = 0; i < 4; i++) {
            ChessboardPoint to=new ChessboardPoint(point.getRow()+x[i],point.getCol()+y[i]);
            if(isValidMove(point,to)) return true;
        }
        return  false;
    }


    public boolean checkWin(PlayerColor currentPlayer){
        //看周围是不是包满了棋子——还能不能走
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 9; j++) {
                ChessboardPoint point=new ChessboardPoint(j,i);
                if(getChessPieceAt(point)!=null) {
                    if (isValidMove(getChessPieceAt(point), point)) return false;
                }
            }
        }
        return true;
    }


    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest) {
        // TODO:Fix this method
        ChessPiece piecesrc = getChessPieceAt(src);
        ChessPiece piecedest = getChessPieceAt(dest);
        boolean t = true;
        if (piecesrc == null ) {
            t = false;
        }
        if (((src.getCol() != dest.getCol() && src.getRow() != dest.getRow()) || (src.getCol() == dest.getCol() && src.getRow() == dest.getRow()) || src.getCol() > 6 || dest.getCol() > 6 || src.getRow() > 8 || dest.getRow() > 8) || src == null) {
            return false;
        } else {
            if (piecesrc != null && piecedest != null) {
                if (piecesrc.canCapture(piecedest) == false) {
                    t = false;
                } else {
                    if (calculateDistance(src, dest) == 1) {
                        if (piecesrc.getName().equals("Rat")&&piecedest.getName().equals("Elephant")&&ratinriver(src,dest)!=true) {
                            t = true;
                        } else {
                            if (acrossriver(src, dest) == false) {
                                t = true;
                            } else {
                                t = false;
                            }
                        }
                    } else if (calculateDistance(src, dest) == 3 || calculateDistance(src, dest) == 5) {
                        if (!piecesrc.getName().equals("Lion" )&& !piecesrc.getName().equals("Tiger")) {
                            t = false;
                        } else {
                            if (ratinriver(src, dest) == true) {
                                t = false;
                            } else {
                                if (acrossallriver(src, dest) == true&&aroundriver(src,dest)==true) {
                                    t = true;
                                } else {
                                    t = false;
                                }

                            }
                        }
                    } else {
                        t = false;
                    }
                }
            }
            //Todo
            if (piecesrc != null && piecedest == null) {
                t = isValidMove(src, dest);
            }
        }
        return t;

    }

    public void  Trapiszero(ChessboardPoint point ){
        for(ChessboardPoint p:trap){
            if(p.equals(point)){
                //ChessPiece inTrap=new
                ChessPiece animalintrap = getChessPieceAt(point);
                animalintrap.setRank(true);
            }
        }

    }
    public boolean inDens(ChessboardPoint point){
        for(ChessboardPoint p:dens){
            if(p.equals(point)) return true;
        }
        return false;
    }
    public void inTrap(ChessboardPoint point) {//进入陷阱变为0 point为陷阱的坐标
        getGridAt(point).getPiece().setRank(true);
    }
    public void outTrap(ChessboardPoint point){//point 为陷阱
        int rank = switch (getGridAt(point).getPiece().getName()) {
            case "Rat" -> 1;
            case "Cat" -> 2;
            case "Dog" -> 3;
            case "Wolf" -> 4;
            case "Leopard" -> 5;
            case "Tiger" -> 6;
            case "Lion" -> 7;
            case "Elephant" -> 8;
            default -> 0;
        };
        getGridAt(point).getPiece().setRank(rank);
    }
    public void trap(ChessboardPoint point,ChessboardPoint selectedPoint){
        for(ChessboardPoint c:trap){
            //如果走到陷阱里
            if(c==point && getGridAt(point).getOwner()!=getChessPieceOwner(selectedPoint)) {
                inTrap(point);
                System.out.println(getChessPieceAt(point).getRank());

                //走出陷阱
            }else if(c==selectedPoint && getGridAt(selectedPoint).getOwner()!=getChessPieceOwner(point)){
                outTrap(point);
                System.out.println(getChessPieceAt(point).getRank());
            }
        }
    }

    public void undo(Step step){
        /*
        to的子删掉，放到from
        恢复吃的子ChessPiece
         */
        ChessPiece chess=step.getChess();
        ChessPiece eaten=step.getEaten();
        ChessboardPoint from=step.getFrom();
        ChessboardPoint to=step.getTo();
        removeChessPiece(to);
        setChessPiece(from,chess);
        if(eaten!=null){
            setChessPiece(to,eaten);
        }
    }

}