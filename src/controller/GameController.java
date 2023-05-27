package controller;


import listener.GameListener;
import model.*;
import music.MusicPlayer;
import view.ChessComponent;
import view.CellComponent;
import view.ChessboardComponent;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

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

    // after a valid move swap the player
    private void swapColor() {
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
        statusLabel.setText("Current Player: "+currentPlayer.name());
        //System.out.println(currentPlayer);
    }
    private void addTurn(){
        if(currentPlayer==PlayerColor.BLUE) turn++;
        turnLabel.setText("Turn: "+turn);
        //System.out.println(turn);
    }

    //win有两种，一种是走到兽穴，一种是对方棋子无路可走
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
            playMusic("/music/"+model.getChessPieceAt(selectedPoint).getName()+".wav");
            model.moveChessPiece(selectedPoint, point);//走
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));//更换表面
            System.out.println(steps);
            if(model.inDens(point)) {//如果进入兽穴
                view.repaint();
                win();
            }
            //进入陷阱
            model.trap(point,selectedPoint);
            selectedPoint = null;
            swapColor();
            addTurn();
            view.repaint();


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
        }else if(model.isValidCapture(selectedPoint,point)&&selectedPoint!=null){//可以吃
            recordMove(selectedPoint,point,getTurn(),currentPlayer);//记录怎么走
            view.removeChessComponentAtGrid(point);
            model.moveChessPiece(selectedPoint, point);//走
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));//更换表面

            //如果没有棋子可以走，或者没棋子了，另一方胜利
            if(model.checkWin(currentPlayer)){
                win();
            }

            selectedPoint = null;
            swapColor();
            addTurn();
            view.repaint();
        }else{
            System.out.println("Illegal capture");
        }
    }
    private void playMusic(String musicPath){
        MusicPlayer musicPlayer=new MusicPlayer(getClass().getResource(musicPath),false);
        Thread music=new Thread(musicPlayer);
        music.start();
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
        if(currentPlayer==PlayerColor.BLUE){
            turn=0;
        }else {
            turn = 1;
        }
        addTurn();
        currentPlayer=PlayerColor.RED;
        swapColor();
        view.repaint();
        winner=null;
        steps.clear();
        //可以走的格子 validMoves.clear();

    }
    public void unDo(){
        if(steps.size()==0||winner!=null){
            JOptionPane.showMessageDialog(view,"Can not undo!","Notice",JOptionPane.ERROR_MESSAGE);
        }else {
            Step s = steps.remove(steps.size() - 1);
            model.undo(s);
            view.undo(s);
            swapColor();
            turn--;
            addTurn();
            view.repaint();
        }
    }
    public void saveGameToFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(view.getChessGameFrame());
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                FileOutputStream fileOut = new FileOutputStream(file);//写的是File形式的
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(steps);
                out.close();
                fileOut.close();
                System.out.println("Save in " + file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void loadGameFromFile(){
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(view.getChessGameFrame());//在哪个窗口上打开
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                FileInputStream fileIn = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                List<Step> stepList = (List<Step>) in.readObject();
                restartGame();
                for (Step s : stepList) {
                    model.runStep(s);
                    view.restoreChess(s);
                }
                steps = stepList;
                in.close();
                fileIn.close();
                currentPlayer=steps.get(steps.size() - 1).getOwner();
                swapColor();
                if(currentPlayer==PlayerColor.BLUE) {
                    turn = steps.get(steps.size() - 1).getTurn() - 1;
                }else{
                    turn = steps.get(steps.size() - 1).getTurn();
                }
                addTurn();
                view.repaint();
                //todo
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    public void recordMove(ChessboardPoint from,ChessboardPoint to,int turn,PlayerColor owner){
        /**
        *在model.moveChessPiece里面加
         */
        ChessPiece chess= model.getChessPieceAt(from);
        ChessPiece eatenChess= model.getChessPieceAt(to);
        Step s=new Step(from,to,turn,owner,chess,eatenChess);
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

