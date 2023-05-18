package controller;


import listener.GameListener;
import model.Constant;
import model.PlayerColor;
import model.Chessboard;
import model.ChessboardPoint;
import view.ChessComponent;
import view.CellComponent;
import view.ChessboardComponent;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

    public GameController(ChessboardComponent view, Chessboard model) {
        this.view = view;
        this.model = model;
        this.currentPlayer = PlayerColor.BLUE;

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

    private boolean win() {
        // TODO: Check the board if there is a winner
        return false;
    }


    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
        if (selectedPoint != null && model.isValidMove(selectedPoint, point)) {
            model.moveChessPiece(selectedPoint, point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
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
            }
        } else if (selectedPoint.equals(point)) {
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
        }
        // TODO: Implement capture function
    }

    //ok
    public void RestartGame(){
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

    }
    public void loadGameFromFile(String path) throws IOException {
        try {
            List<String> lines = Files.readAllLines(Path.of(path));
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
        }
    }

}

