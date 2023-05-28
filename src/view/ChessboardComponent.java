package view;


import controller.GameController;
import model.*;
import view.animalsChessComponent.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;

/**
 * This class represents the checkerboard component object on the panel
 */
public class ChessboardComponent extends JComponent {//绘制时棋盘
    private final CellComponent[][] gridComponents = new CellComponent[CHESSBOARD_ROW_SIZE.getNum()][CHESSBOARD_COL_SIZE.getNum()];
    private final int CHESS_SIZE;//棋子大小
    private ChessGameFrame chessGameFrame;
    private final Set<ChessboardPoint> riverCell = new HashSet<>();
    private final Set<ChessboardPoint> trapCell = new HashSet<>();
    private final Set<ChessboardPoint> densCell = new HashSet<>();

    private GameController gameController;
    private ImageIcon image;

    /*
    棋盘中还包含什么
    1. 长宽和棋子大小
    2. 可以用鼠标点
    3. 能layout
    4. 网格组成可以初始化
    5.看得见
     */
    public ChessboardComponent(int chessSize) {
        CHESS_SIZE = chessSize;
        int width = CHESS_SIZE * 7;
        int height = CHESS_SIZE * 9;//棋盘大小
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);// Allow mouse events to occur
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        System.out.printf("chessboard width, height = [%d : %d], chess size = %d\n", width, height, CHESS_SIZE);
        initiateGridComponents();
        setVisible(true);
    }


    /**
     * This method represents how to initiate ChessComponent
     * according to Chessboard information
     */
    public void initiateChessComponent(Chessboard chessboard) {
        Cell[][] grid = chessboard.getGrid();

        //清空一下
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                gridComponents[i][j].removeAll();
            }
        }

        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                // TODO: Implement the initialization checkerboard

                if (grid[i][j].getPiece() != null) {
                    ChessPiece chessPiece = grid[i][j].getPiece();
                    //System.out.println(chessPiece.getOwner());
                    switch (chessPiece.getName()) {
                        case "Elephant" -> gridComponents[i][j].add(
                                new ElephantChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE));
                        case "Lion" -> gridComponents[i][j].add(
                                new LionChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE));
                        case "Leopard" -> gridComponents[i][j].add(
                                new LeopardChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE));
                        case "Tiger" -> gridComponents[i][j].add(
                                new TigerChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE));
                        case "Cat" -> gridComponents[i][j].add(
                                new CatChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE));
                        case "Dog" -> gridComponents[i][j].add(
                                new DogChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE));
                        case "Wolf" -> gridComponents[i][j].add(
                                new WolfChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE));
                        case "Rat" -> gridComponents[i][j].add(
                                new RatChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE));
                    }
                }
            }
        }
    }
    //view 层移除所有棋子
    public  void removeAllPieces(){
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                if(gridComponents[i][j].getComponents().length>0){//有棋子
                    removeChessComponentAtGrid(new ChessboardPoint(i,j));
                }
            }
        }
    }

    //给棋盘分布定性
    public void initiateGridComponents() {

        riverCell.clear();
        trapCell.clear();
        densCell.clear();

        //river
        riverCell.add(new ChessboardPoint(3, 1));
        riverCell.add(new ChessboardPoint(3, 2));
        riverCell.add(new ChessboardPoint(4, 1));
        riverCell.add(new ChessboardPoint(4, 2));
        riverCell.add(new ChessboardPoint(5, 1));
        riverCell.add(new ChessboardPoint(5, 2));

        riverCell.add(new ChessboardPoint(3, 4));
        riverCell.add(new ChessboardPoint(3, 5));
        riverCell.add(new ChessboardPoint(4, 4));
        riverCell.add(new ChessboardPoint(4, 5));
        riverCell.add(new ChessboardPoint(5, 4));
        riverCell.add(new ChessboardPoint(5, 5));

        //trap
        trapCell.add(new ChessboardPoint(0,2));
        trapCell.add(new ChessboardPoint(0,4));
        trapCell.add(new ChessboardPoint(1,3));
        trapCell.add(new ChessboardPoint(7,3));
        trapCell.add(new ChessboardPoint(8,2));
        trapCell.add(new ChessboardPoint(8,4));

        //den
        densCell.add(new ChessboardPoint(0,3));
        densCell.add(new ChessboardPoint(8,3));

        //遍历+放颜色
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint temp = new ChessboardPoint(i, j);
                CellComponent cell;
                if (riverCell.contains(temp)) {
                    cell = new CellComponent(new Color(137, 219, 232), calculatePoint(i, j), CHESS_SIZE);
                    //this.add(cell);
                }else if(trapCell.contains(temp)) {
                    cell = new CellComponent(new Color(129, 112, 112), calculatePoint(i, j), CHESS_SIZE);
//                    Graphics g = null;
//                    paintComponent(g);
//                    image=new ImageIcon("/resource/ChessBoard/trap.png");
                }else if(densCell.contains(temp)){
                    cell = new CellComponent(new Color(91, 15, 15), calculatePoint(i, j), CHESS_SIZE);
                } else {
                    cell = new CellComponent(new Color(126, 147, 126), calculatePoint(i, j), CHESS_SIZE);
                    //this.add(cell);
                }
                this.add(cell);
                gridComponents[i][j] = cell;
            }
        }
    }
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        Graphics2D g2 = (Graphics2D) g;
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        image=new ImageIcon("resource\\ChessImage\\Cat.png");
//        g.drawImage(image.getImage(),0,0,getWidth(),getHeight(), this);
//    }

    //ok
    public void registerController(GameController gameController) {
        this.gameController = gameController;
    }

    public ChessGameFrame getChessGameFrame() {
        return chessGameFrame;
    }

    public void undo(Step step){
        ChessboardPoint from=step.getFrom();
        ChessboardPoint to=step.getTo();
        ChessPiece chess=step.getChess();
        ChessPiece eaten=step.getEaten();
        /*
        把to的棋子拿走，出现在from里
        如果有棋子被吃了
            新建一个
         */
        removeChessComponentAtGrid(to);
        switch (chess.getName()) {
            case "Elephant" -> setChessComponentAtGrid(from, new ElephantChessComponent(chess.getOwner(), CHESS_SIZE));
            case "Lion" -> setChessComponentAtGrid(from, new LionChessComponent(chess.getOwner(), CHESS_SIZE));
            case "Tiger" -> setChessComponentAtGrid(from, new TigerChessComponent(chess.getOwner(), CHESS_SIZE));
            case "Leopard" -> setChessComponentAtGrid(from, new LeopardChessComponent(chess.getOwner(), CHESS_SIZE));
            case "Wolf" -> setChessComponentAtGrid(from, new WolfChessComponent(chess.getOwner(), CHESS_SIZE));
            case "Dog" -> setChessComponentAtGrid(from, new DogChessComponent(chess.getOwner(), CHESS_SIZE));
            case "Cat" -> setChessComponentAtGrid(from, new CatChessComponent(chess.getOwner(), CHESS_SIZE));
            case "Rat" -> setChessComponentAtGrid(from, new RatChessComponent(chess.getOwner(), CHESS_SIZE));
        }
        if(eaten!=null){
            switch (eaten.getName()) {
                case "Elephant" -> setChessComponentAtGrid(to, new ElephantChessComponent(eaten.getOwner(), CHESS_SIZE));
                case "Lion" -> setChessComponentAtGrid(to, new LionChessComponent(eaten.getOwner(), CHESS_SIZE));
                case "Tiger" -> setChessComponentAtGrid(to, new TigerChessComponent(eaten.getOwner(), CHESS_SIZE));
                case "Leopard" -> setChessComponentAtGrid(to, new LeopardChessComponent(eaten.getOwner(), CHESS_SIZE));
                case "Wolf" -> setChessComponentAtGrid(to, new WolfChessComponent(eaten.getOwner(), CHESS_SIZE));
                case "Dog" -> setChessComponentAtGrid(to, new DogChessComponent(eaten.getOwner(), CHESS_SIZE));
                case "Cat" -> setChessComponentAtGrid(to, new CatChessComponent(eaten.getOwner(), CHESS_SIZE));
                case "Rat" -> setChessComponentAtGrid(to, new RatChessComponent(eaten.getOwner(), CHESS_SIZE));
            }
        }

    }
    public void restoreChess(Step step){//复原棋子
        ChessboardPoint to=step.getTo();
        ChessPiece c=step.getChess();
        ChessboardPoint from=step.getFrom();
        ChessPiece e=step.getEaten();
        removeChessComponentAtGrid(from);
        if(e!=null){
            removeChessComponentAtGrid(to);
        }
        switch (c.getName()) {
            case "Elephant" -> setChessComponentAtGrid(to, new ElephantChessComponent(c.getOwner(), CHESS_SIZE));
            case "Lion" -> setChessComponentAtGrid(to, new LionChessComponent(c.getOwner(), CHESS_SIZE));
            case "Tiger" -> setChessComponentAtGrid(to, new TigerChessComponent(c.getOwner(), CHESS_SIZE));
            case "Leopard" -> setChessComponentAtGrid(to, new LeopardChessComponent(c.getOwner(), CHESS_SIZE));
            case "Wolf" -> setChessComponentAtGrid(to, new WolfChessComponent(c.getOwner(), CHESS_SIZE));
            case "Dog" -> setChessComponentAtGrid(to, new DogChessComponent(c.getOwner(), CHESS_SIZE));
            case "Cat" -> setChessComponentAtGrid(to, new CatChessComponent(c.getOwner(), CHESS_SIZE));
            case "Rat" -> setChessComponentAtGrid(to, new RatChessComponent(c.getOwner(), CHESS_SIZE));
        }

    }
    //ok
    public void setChessComponentAtGrid(ChessboardPoint point, ChessComponent chess) {
        getGridComponentAt(point).add(chess);
    }

    //ok
    public ChessComponent removeChessComponentAtGrid(ChessboardPoint point) {//在小格子中删除
        // Note re-validation is required after remove / removeAll.
        ChessComponent chess = (ChessComponent) getGridComponentAt(point).getComponents()[0];
        getGridComponentAt(point).removeAll();
        getGridComponentAt(point).revalidate();
        chess.setSelected(false);
        return chess;
    }

    //ok
    private CellComponent getGridComponentAt(ChessboardPoint point) {
        return gridComponents[point.getRow()][point.getCol()];
    }

    //ok
    private ChessboardPoint getChessboardPoint(Point point) {
        System.out.println("[" + point.y / CHESS_SIZE + ", " + point.x / CHESS_SIZE + "] Clicked");
        return new ChessboardPoint(point.y / CHESS_SIZE, point.x / CHESS_SIZE);
    }

    //ok
    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    //ok
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(image.getImage(),0,0,CHESS_SIZE,CHESS_SIZE, this);
    }

    //ok 找鼠标点到哪里
    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
            if (clickedComponent.getComponentCount() == 0) {//判断格子里有没有棋子
                System.out.print("None chess here and ");
                gameController.onPlayerClickCell(getChessboardPoint(e.getPoint()), (CellComponent) clickedComponent);
            } else {
                System.out.print("One chess here and ");
                gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (ChessComponent) clickedComponent.getComponents()[0]);
            }
        }
    }

    //胜利弹窗
    public void optionWinPanel(PlayerColor winner){
        //按钮标签
        Object[] options={"New Game","Exit"};//再来一局还是返回主页
        //显示对话框
        int result=JOptionPane.showOptionDialog(null,"The winner is "+winner,"Win!",
                JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE,null,options,options[0]);
        //每个按钮的结果
        if(result==JOptionPane.YES_OPTION){
            gameController.restartGame();
//            JFrame topFrame=(JFrame) JOptionPane.getRootFrame();
//            topFrame.dispose();
        }else{
            //返回主页面
        }
    }
}
