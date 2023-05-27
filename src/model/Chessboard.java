package model;

import model.ChessPieces.*;
import view.ChessComponent;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.List;
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
                if (((i == 3 && j == 1) && (i == 3 && j == 2)) || ((i == 4 && j == 1) && (i == 4 && j == 2)) || ((i == 5 && j == 1) && (i == 5 && j == 2)) || ((i == 3 && j == 4) && (i == 3 && j == 5)) || ((i == 4 && j == 4) && (i == 4 && j == 5)) || ((i == 5 && j == 4) && (i == 5 && j == 5)) || ((i == 3 && j == 1) && (i == 4 && j == 1) && (i == 5 && j == 1)) || ((i == 3 && j == 2) && (i == 4 && j == 2) && (i == 5 && j == 2)) || ((i == 3 && j == 4) && (i == 4 && j == 4) && ((i == 5 && j == 4)) || ((i == 3 && j == 5)) && (i == 4 && j == 5) && (i == 5 && j == 5))) {
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
       int row1=0;
        if(src.getRow()!= dest.getRow()&&src.getCol()==dest.getCol()){
            row1=1;
        }else{
            row1=0;
        }
        return row1;
}

    public int col1(ChessboardPoint src, ChessboardPoint dest){
        int col1=0;
        if(src.getRow()== dest.getRow()&&src.getCol()!=dest.getCol()){
           col1=1;
        }else{
           col1=0;
        }
        return col1;
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
        boolean canmove = false;
        if (piecesrc == null || piecedest != null) {
            canmove = false;
        }
        if ((src.getCol() != dest.getCol() && src.getRow() != dest.getRow()) || (src.getCol() == dest.getCol() && src.getRow() == dest.getRow()) || src.getCol() > 6 || dest.getCol() > 6 || src.getRow() > 8 || dest.getRow() > 8) {
            canmove = false;
        }
        if (piecesrc != null && piecedest == null) {
            boolean t = true;
            if (calculateDistance(src, dest) == 1) {
                if (piecesrc.getName().equals("Rat")) {
                    canmove = true;
                } else {
                    if (acrossriver(src, dest) == false) {
                        canmove = true;
                    } else {
                        canmove = false;
                    }
                }
            } else if (calculateDistance(src, dest) == 3 || calculateDistance(src, dest) ==4 ) {
                if (!piecesrc.getName().equals("Lion") || !piecesrc.getName().equals("Tiger")) {
                    canmove = false;
                } else {
                    if (ratinriver(src, dest) == true) {
                        canmove = false;
                    } else {
                        if (aroundriver(src, dest) == true) {
                            canmove = true;
                        } else {
                            canmove = false;
                        }
                    }
                }
            }else{
                canmove =false;
            }
        }
        return canmove;
    }
    public ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
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

    public void TrapIsZero(ChessboardPoint point) {
        for (ChessboardPoint p : trap) {
            if (p.equals(point)) {
                //ChessPiece inTrap=new
                ChessPiece animalintrap = getChessPieceAt(point);
                animalintrap.setRank(true);
            }
        }

    }

    public boolean inDens(ChessboardPoint point) {
        for (ChessboardPoint p : dens) {
            if (p.equals(point)) return true;
        }
        return false;
    }

    public void inTrap(ChessboardPoint point) {//进入陷阱变为0 point为陷阱的坐标
        getGridAt(point).getPiece().setRank(true);
    }

    public void outTrap(ChessboardPoint point) {//point 为陷阱
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

    /*  public void trap(ChessboardPoint point, ChessboardPoint selectedPoint) {
          for (ChessboardPoint c : trap) {
              //如果走到陷阱里
              if (c == point && getGridAt(point).getOwner() != getChessPieceOwner(selectedPoint)) {
                  inTrap(point);
                  System.out.println(getChessPieceAt(point).getRank());

                  //走出陷阱
              } else if (c == selectedPoint && getGridAt(selectedPoint).getOwner() != getChessPieceOwner(point)) {
                  outTrap(point);
                  System.out.println(getChessPieceAt(point).getRank());
              }
          }
      }
  */
    public void runStep(Step step) {
        ChessboardPoint to = step.getTo();
        ChessboardPoint from = step.getFrom();
        ChessPiece chess = step.getChess();
        setChessPiece(from, null);
        setChessPiece(to, chess);
    }


    public boolean isValidMove(ChessPiece chess, ChessboardPoint point) {
        int[] x = {1, 0, -1, 0};
        int[] y = {0, -1, 0, 1};
        for (int i = 0; i < 4; i++) {
            ChessboardPoint to = new ChessboardPoint(point.getRow() + x[i], point.getCol() + y[i]);
            if (isValidMove(point, to)) return true;
        }
        return false;
    }


    public boolean checkWin(PlayerColor currentPlayer) {
        //看周围是不是包满了棋子——还能不能走
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 9; j++) {
                ChessboardPoint point = new ChessboardPoint(j, i);
                if (getChessPieceAt(point) != null) {
                    if (isValidMove(getChessPieceAt(point), point)) return false;
                }
            }
        }
        return true;
    }

//新增加的
}
/*
    private boolean riverLegal(ChessboardPoint src, ChessboardPoint dest) {
        ChessPiece icon1 = getChessPieceAt(src);
        ChessPiece icon2 = getChessPieceAt(dest);

        if (icon1.getName().equals("mouse")) {
            return true;
        } else {
            if (icon2 != null) {
                return !icon2.getName().equals("river");
            }
            return true;
        }
    }//除了老鼠以外，任何动物都不能进入小河

    private boolean occupycamp(ChessboardPoint src, ChessboardPoint dest) {
        ChessPiece icon1 = getChessPieceAt(src);
        ChessPiece icon2 = getChessPieceAt(dest);
        if (dest.getRow() == 3 && dest.getCol() == 0) {
            //这是蓝方兽穴所在位置，只有红方动物能够占据
            if (icon1.getOwner().getColor().equals(Color.RED)) {
                return true;
            } else return false;
        } else if (dest.getRow() == 3 && dest.getCol() == 8) {
            //这是红方兽穴所在位置，只有蓝方动物能够占据
            if (icon1.getOwner().getColor().equals(Color.BLUE)) {
                return true;
            } else return false;
        }
        return true;
        //如果b所在格子并非兽穴，则不考虑这个问题
    }

    private boolean distancelegal(ChessboardPoint src, ChessboardPoint dest) {
        return (Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol()) - dest.getCol()) == 1;
        //行与列差距的和只能为一
    }

    private boolean jumplegal(ChessboardPoint src, ChessboardPoint dest) {
        int left = Math.min(src.getRow(), dest.getRow());//横跳的时候找到左边的那个
        int top = Math.min(src.getCol(), dest.getCol());//纵跳的时候找到上面的那个
        Set<Integer> row = new HashSet<Integer>();//根据横条的两点建立Set
        row.add(src.getRow());
        row.add(dest.getRow());
        Set<Integer> col = new HashSet<Integer>();//根据纵跳的两点建立Set
        col.add(src.getCol());
        col.add(dest.getCol());
        Set<Integer> rowstandard1 = new HashSet<Integer>();//横条的两个标准情况
        rowstandard1.add(0);
        rowstandard1.add(3);
        Set<Integer> rowstandard2 = new HashSet<Integer>();
        rowstandard2.add(6);
        rowstandard2.add(3);
        Set<Integer> colstandard1 = new HashSet<Integer>();///纵跳只有一个标准情况
        colstandard1.add(2);
        colstandard1.add(6);
       // ChessboardPoint icon = (ChessboardPoint) dest.getIcon();
        ChessPiece icon = getChessPieceAt(src);

        if (((src.getCol() == 3 && dest.getCol() == 3) || (src.getCol() == 4 && dest.getCol() == 4) || (src.getCol() == 5 && dest.getCol() == 5)) && (row.equals(rowstandard1) || (row.equals(rowstandard2)))) {
            //横跳的时候需要先确保动物所处的行正确，再比较建立出来的Set和标准的Set
            for (int i = left + 1; i <= left + 2; i++) {  //从左边的格子开始检查有没有动物
                ChessboardPoint c = (ChessboardPoint) grid[i][src.getCol()].getChessPieceAt(src);
                if (c != null && (c. != "river")) {
                    // 如果小河当中有动物（老鼠）阻挡，则无法跳跃，
                    //因为小河本身也是图片，所以在这里要做一下排除
                    return false;
                }
            }

            if (icon.getName() == "tiger" || icon.getName() == "lion") {
                // 判断动物是不是老虎或狮子
                return eatlegal(src, dest);
            } else return false;
        }

        // 纵跳时的分析相似，不做过多讲解
        else if (((src.getRow() == 1 && dest.getRow() == 1) || (src.getRow() == 2 && dest.getRow() == 2) || (src.getRow() == 4 && dest.getRow() == 4) ||
                (src.getRow() == 5 && dest.getRow() == 5)) && (col.equals(colstandard1))) {
            for (int i = top + 1; i <= top + 3; i++) {
                MyIcon d = (MyIcon) labels[src.getRow()][i].getIcon();
                if (d != null && (getname(d) != "river")) {
                    return false;
                }
            }
            if (getname(icon) == "tiger" || getname(icon) == "lion") {
                return eatlegal(src, dest);
            } else return false;
        } else {//如果不符合跳跃的情况，就自动归到distancelegal，判断是否符合普通走法
            return distancelegal(src, dest);
        }
    }


    private static boolean eatlegal(ChessboardPoint src, ChessboardPoint dest) {
        if (dest. != null) //{被走到的格子上得有东西，不能是空的，否则函数就没意义
            MyIcon icon = (MyIcon) a.getIcon();
        MyIcon icon1 = (MyIcon) b.getIcon();
        if (getname(icon) != "river" && (getname(icon1) == "river")) {
            //如果是从地上到小河里，自动归为riverlegal
            return riverlegal(a, b);
        } else {
            if (getname(icon) == "mouse" && getname(icon1) == "elephant") { //研究鼠吃象的情况
                if ((a.row == 1 || a.row == 2 || a.row == 4 || a.row == 5)
                        && (a.col == 3 || a.col == 4 || a.col == 5)) {
                    //鼠如果在河里，不能吃象
                    return false;
                }
                return true;
            } else if (getname(icon) == "elephant" && getname(icon1) == "mouse") { // 象不能吃鼠
                return false;
            } else if (icon1.istrapped) {//掉进陷阱里的动物谁都可以吃
                return true;
            } else {
                if (getname(icon1) != "trap" && getname(icon1) != "river" && getname(icon1) != "camp") {//这一步是确保对象是动物，只有动物才有相应的赋值，这也是在规避空指针，如果对象不是动物，那函数也失去了意义
                    return (power.get(getname(icon)) >= power.get(getname(icon1)));
                    //动物可以吃比自己实力弱或与自己实力相当的动物
                } else return true;
            }
        }
    }
    //    else

    //{
    //    return true;
   // }


    private static boolean islegalmove(ChessboardPoint src, ChessboardPoint dest) {
        return (occupycamp(a,b)&&riverlegal(a,b)&&eatlegal(a,b)&&jumplegal(a,b));
    }




    private void move(MyLabel jl) {
        MyIcon icon1=(MyIcon) jl.getIcon();
        this.clickcount++;//第一件事就是更新计数器
        if(clickcount%2==1) {//直接判断计数器的奇偶性，先分析奇数，即选定要走的棋子
            if(jl.getIcon()==null||
                    (getname(icon1)=="trap"&&getname(icon1)=="river"&&getname(icon1)=="camp")) {
                //如果点到了小河，陷阱，兽穴或者空地时计数器直接减一，
                //不需要其他任何操作，玩家需要重新点击
                clickcount--;
            }
            else {  //现在点到了动物
                jl.setBackground(Color.YELLOW);// 背景设为黄色，代表已选中
                        movingpiece=icon1; //设置要走的棋子
                this.movinglabel=jl;//设置相应的MyLabel

                //如果之前轮到红方走，那么现在红方走完了就轮到蓝方了，反之亦然
                if(getside(movingpiece)=="red"&&sidetomove=="red") {
                    sidetomove="blue";
                }
                else if(getside(movingpiece)=="blue"&&sidetomove=="blue") {
                    sidetomove="red";
                }
                else {//点错了就会告诉你还没轮到你走，顺便也把计数器还原
                    clickcount--;
                    System.out.println("not your turn");
                    jl.setBackground(Color.white);
                }
            }
        }

        else { //开始分析偶数的情况了，即把选定的棋子走到选定的格子中
            if(islegalmove(movinglabel,jl)) {
                //当然要判断走的棋是否合理

                if(jl.getIcon()!=null&&getname(icon1）=="trap") {
                    //走到陷阱里时要注明一下
                    movingpiece.istrapped=true;
                }
                jl.setIcon(movingpiece);//把目标格子设定为选定的棋子
                        //然后再把其他的格子还原

                for(int i=0;i<rows;i++) {
                    for(int j=0;j<cols;j++) {
                        if((labels[i][j].getBackground()).equals(Color.YELLOW)) {
                            //如果背景是黄色，说明这是棋子原来的位置，那么我们就要对这个位置进行相应的还原
                            labels[i][j].setIcon(null);// 取消格子上的图片
                            labels[i][j].setBackground(Color.white);//背景重新设置为白色
                            if(((i==2||i==4)&&(j==0||j==8))||(i==3&&(j==1||j==7))) {
                               // 如果是陷阱，设置上陷阱的图片
                                labels[i][j].setIcon(new MyIcon(paths[16]));
                            }
                            else if((i==1||i==2||i==4||i==5)&&(j==3||j==4||j==5)) {
                                //如果是小河，设置上小河的图片
                                labels[i][j].setIcon(new MyIcon(paths[17]));
                            }
                        }
                    }
                }
            }
            else {//这说明走的棋违规了
                System.out.println("illegal move");
                movinglabel.setBackground(Color.white);//把背景还原成白色
                //这时出现了一个问题，因为sidetomove在之前已经被提前改动了，所以即使一方走的棋是违规的，另一方也可以点击自己的棋子来走。这算是一个小bug吧。因此要用一下的代码修改一下，把sidetomove换回去
                if(sidetomove=="red") {
                    sidetomove="blue";
                }
                else if(sidetomove=="blue") {
                    sidetomove="red";
                }
            }
        }
    }



    private static boolean campoccupied() {
        if(getside((MyIcon)labels[3][0].getIcon())=="red") {
            return true;
        }
        else if(getside((MyIcon)labels[3][8].getIcon())=="blue") {
            return true;
        }
        return false;
    }



    private static boolean haspieceleft() {
       // 先给双方各设置一个布尔值，默认值是false
        boolean red=false;
        boolean blue=false;
        for(int i=0;i<rows;i++) {
            for(int j=0;j<cols;j++) {//一个一个格子地检查是否有剩余动物，
                //检查到有剩余的动物，就更该相应的布尔值为true
                if(labels[i][j].getIcon()!=null&&getside((MyIcon) labels[i][j].getIcon())=="red") {
                    red=true;
                }
                else if(labels[i][j].getIcon()!=null&&getside((MyIcon) labels[i][j].getIcon())=="blue") {
                    blue=true;
                }
            }
        }
        return (red&&blue);//必须双方都有剩余动物，才能返回true
    }



    private static boolean haslegalmove() {
       // 初始布尔值为false
        boolean red=false;
        boolean blue=false;
        for(int i=0;i<rows;i++) {
            for(int j=0;j<cols;j++) {//先一个一个格子地找动物
                if(labels[i][j].getIcon()!=null&&getside((MyIcon) labels[i][j].getIcon())=="red") {
                   // 当发现一只动物时，再对每一个个格子循环判断从该动物所处的格子走到目标格子是否合法
                    for(int x=0;x<rows;x++) {
                        for(int y=0;y<cols;y++) {
                            if(islegalmove(labels[i][j],labels[x][y])) {
                               // 找到一个符合要求的格子，就更改布尔值为true
                                        red=true;
                            }
                        }
                    }
                }
            }
        }
        for(int i=0;i<rows;i++) {
            for(int j=0;j<cols;j++) {
                if(labels[i][j].getIcon()!=null&&getside((MyIcon) labels[i][j].getIcon())=="blue") {
                    for(int x=0;x<rows;x++) {
                        for(int y=0;y<cols;y++) {
                            if(islegalmove(labels[i][j],labels[x][y])) {
                                blue=true;
                            }
                        }
                    }
                }
            }
        }
        return (red&&blue);//只有双方都有合法走法的时候才会返回true
    }


    private static boolean isWin() {
        return (campoccupied()||(!haspieceleft())||(!haslegalmove()));
    }*/













