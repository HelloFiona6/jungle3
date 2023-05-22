package controller;


import listener.GameListener;
import model.*;
import view.ChessComponent;
import view.CellComponent;
import view.ChessboardComponent;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and onPlayerClickChessPiece()]
 *
*/
public class GameController implements GameListener {


    private Chessboard model;
    private ChessboardComponent view;
    private PlayerColor currentPlayer;

    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;
    private int turn=1;
    private JLabel statusLabel=new JLabel("");
    private JLabel turnLabel=new JLabel("");
    private List<Step> steps; //undo
    private Step step;
    private PlayerColor winner;

    //ok
    public GameController(ChessboardComponent view, Chessboard model) {
        this.view = view;
        this.model = model;
        this.currentPlayer = PlayerColor.BLUE;

        steps=new ArrayList<>();
        view.registerController(this);
        initialize();
        view.initiateChessComponent(model);
        view.repaint();
    }

    public void setStatusLabel(JLabel statusLabel) {
        this.statusLabel = statusLabel;
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public void setTurnLabel(JLabel turnLabel) {
        this.turnLabel = turnLabel;
    }

    public JLabel getTurnLabel() {
        return turnLabel;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    //ok
    private void initialize() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
            }
        }
    }
    //todo save
    //todo load

    // after a valid move swap the player
    private void swapColor() {
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
        statusLabel.setText("Current Player: "+currentPlayer.name());
        //System.out.println(currentPlayer);
    }
    private void addTurn(){
        if(currentPlayer==PlayerColor.BLUE) turn++;
        turnLabel.setText("Turn: "+turn);
        System.out.println(turn);
    }

    //win有两种，一种是走到兽穴，一种是对方棋子无路可走
    private void densWin() {
        // TODO: Check the board if there is a winner
        winner = currentPlayer;
        System.out.println("Winner is " + winner);
        //view.getChessGameFrame().recordWin();
        view.optionWinPanel(winner);
    }
    //一方棋子无路可走
    private void win() {
        // TODO: Check the board if there is a winner
        winner = currentPlayer;
        System.out.println("Winner is " + winner);
        //view.getChessGameFrame().recordWin();
        view.optionWinPanel(winner);
    }


    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
        if (selectedPoint != null && model.isValidMove(selectedPoint, point)) {//可以走
            recordMove(selectedPoint,point,getTurn(),currentPlayer);//记录怎么走
            model.moveChessPiece(selectedPoint, point);//走
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));//更换表面
            //System.out.println(steps);
            if(model.inDens(point)) {//如果进入兽穴
                view.repaint();
                densWin();
            }
            if(model.inTrap()) {
                //rank将为1
            }
            selectedPoint = null;
            swapColor();
            addTurn();
            view.repaint();

            // TODO: if the chess enter Dens or Traps and so on
        }
    }
    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component) {
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                selectedPoint = point;
                component.setSelected(true);
                component.repaint();

                //show ValidMoves
            }
        } else if (selectedPoint.equals(point)) {//点两下
            //hideValidMove
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
        }
        // TODO: Implement capture function
        /*
        如果这个棋子被吃了
            删了这个棋子
         */
        if(model.isValidCapture(selectedPoint,point)&&selectedPoint!=null){//可以吃
            recordMove(selectedPoint,point,getTurn(),currentPlayer);//记录怎么走
            view.removeChessComponentAtGrid(point);
            model.moveChessPiece(selectedPoint, point);//走
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));//更换表面
            selectedPoint = null;
            swapColor();
            addTurn();
            view.repaint();
        }else{
            System.out.println("Illegal capture");
        }
    }

    //ok
    public void restartGame(){
        /*
        1. model 清除所有的棋子
        2. model 添加初始化棋子
        3. view 清除绘制过的棋子
        4. view 重新add棋子
        5. view.repaint()
        6. 行棋方重新设为蓝色
         */
        model.removeAllPiece();
        model.initPieces();
        view.removeAllPieces();
        view.initiateChessComponent(model);
        view.repaint();
        swapColor();
        currentPlayer=PlayerColor.BLUE;
        turn=1;
        winner=null;
        steps.clear();
        //可以走的格子 validMoves.clear();

    }
    public void unDo(){
        Step s=steps.remove(steps.size()-1);
        // todo model
        view.undo(s);
        swapColor();
        view.repaint();
    }
    public void saveGameToFile(String fileName) {
        try{
            FileOutputStream fileOut=new FileOutputStream(fileName);
            ObjectOutputStream out=new ObjectOutputStream(fileOut);
            for (Step s : steps) {
                out.writeObject(s);
            }
            out.close();
            fileOut.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void loadGameFromFile(String fileName){
        try {
            FileInputStream fileIn=new FileInputStream(fileName);
            ObjectInputStream in=new ObjectInputStream(fileIn);
            Step step1=(Step) in.readObject();
            in.close();
            fileIn.close();
            List<String> lines = Files.readAllLines(Path.of(fileName));
            //todo
            for (String s : lines) {
                System.out.println(s);
            }
            model.removeAllPiece();
            model.initPieces(lines);
            view.removeAllPieces();
            view.initiateChessComponent(model);
            view.repaint();
            //todo
        }catch (IOException e){
            throw new RuntimeException(e);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public void recordMove(ChessboardPoint from,ChessboardPoint to,int turn,PlayerColor owner){
        /**
        *在model.moveChessPiece里面加
         */
        Step s=new Step(from,to,turn,owner);
        steps.add(s);
    }

//    public void showValidMoves(ChessboardPoint point) {
//        validMoves = model.getValidMoves(point);
//        view.showValidMoves(validMoves);
//    }
//
//    public void hideValidMoves() {
//        view.hideValidMoves(validMoves);
//    }

}

