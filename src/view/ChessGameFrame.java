package view;

import controller.GameController;
import music.MusicPlayer;
import view.image.ImagePanel;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.HashMap;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;

    private final int ONE_CHESS_SIZE;
    private GameController gameController;
    private JLabel statusLabel;
    private JLabel turnLabel;
    private int imageNumber=0;
    private ImagePanel panel;
    private JRootPane pane;
    private ImageIcon back=new ImageIcon(getClass().getResource("/Background/picture1.jpg"));

    private ChessboardComponent chessboardComponent;
    public ChessGameFrame(int width, int height) {
        setTitle("Jungle"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        //background
        panel=new ImagePanel(back);
        setContentPane(panel);
        panel.setLayout(null);

        //各种按钮
        addChessboard();
        addPlayerLabel();
        addTurnLabel();
        addLoadButton();
        addSaveButton();
        addRestartButton();
        addUndoButton();
        addThemeButton();
        //addStopMusicButton();

    }

    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }

    public void setChessboardComponent(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }

    public GameController getGameController() {
        return gameController;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(JLabel statusLabel) {
        this.statusLabel = statusLabel;
    }


    public JLabel getTurnLabel() {
        return turnLabel;
    }

    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation(HEIGTH / 5, HEIGTH / 10);
        add(chessboardComponent);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addPlayerLabel() {
        statusLabel = new JLabel("Current Player: BLUE");
        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
        statusLabel.setSize(300, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        statusLabel.setForeground(Color.WHITE);
        add(statusLabel);
    }

    private void addTurnLabel(){
        turnLabel=new JLabel("Turn: 1");
        turnLabel.setLocation(HEIGTH, HEIGTH / 7);
        turnLabel.setSize(300, 60);
        turnLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        turnLabel.setForeground(Color.WHITE);
        add(turnLabel);
    }


    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

//    private void addHelloButton() {
//        JButton button = new JButton("Show Hello Here");
//        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Hello, world!"));
//        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
//        button.setSize(200, 60);
//        button.setFont(new Font("Rockwell", Font.BOLD, 20));
//        add(button);
//    }

    private void addRestartButton() {
        JButton button = new JButton("Restart");
        button.addActionListener((e) -> //触发的事件
        {
            playMusic("/music/button.wav");
            gameController.restartGame();//点完重新加载游戏
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    /**
     *model 清除所有棋子
     * model 添加初始化棋子
     * view 清除所有绘制过的棋子
     * view 重新add棋子
     * view.repaint()
     */
    private void playMusic(String musicPath){
        MusicPlayer musicPlayer=new MusicPlayer(getClass().getResource(musicPath),false);
        Thread music=new Thread(musicPlayer);
        music.start();
    }
    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH, HEIGTH / 10 + 280);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {//点完弹出个面板
            playMusic("/music/button.wav");
            System.out.println("Click load");
            gameController.loadGameFromFile();
        });
    }
    private void addSaveButton() {
        JButton button = new JButton("Save");
        button.setLocation(HEIGTH, HEIGTH / 10 + 360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {//点完弹出个面板
            playMusic("/music/button.wav");
            System.out.println("Click save");
            gameController.saveGameToFile();
        });
    }

    private void addUndoButton() {
        JButton button = new JButton("Undo");
        button.setLocation(HEIGTH, HEIGTH / 10 + 200);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {//点完弹出个面板
            playMusic("/music/button.wav");
            System.out.println("Click uodo");
            gameController.unDo();
        });
    }

    private void addThemeButton(){
        JButton button = new JButton("Theme");
        button.setLocation(HEIGTH, HEIGTH / 10 + 440);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        HashMap<Integer,String> pic=new HashMap<>();
        pic.put(0,"/Background/picture1.jpg");
        pic.put(1,"/Background/picture2.jpg");
        pic.put(2,"/Background/picture3.jpg");
        pic.put(3,"/Background/picture4.jpg");

        button.addActionListener(e -> {
            //换图片
            playMusic("/music/button.wav");
            imageNumber=(imageNumber+1)%4;
            back=new ImageIcon(getClass().getResource(pic.get(imageNumber)));
            panel.setBackgroundImage(back);


        });
    }

//    private void addStopMusicButton(){
//        JButton button = new JButton("Stop Music");
//        button.setLocation(HEIGTH, HEIGTH / 10 + 520);
//        button.setSize(200, 60);
//        button.setFont(new Font("Rockwell", Font.BOLD, 20));
//        add(button);
//        button.addActionListener(e -> {
//            //暂停背景音乐播放
//
//
//        });
//    }

}
