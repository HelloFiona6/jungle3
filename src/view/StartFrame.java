package view;

import controller.GameController;
import model.Chessboard;
import music.MusicPlayer;
import view.image.ImagePanel;

import javax.swing.*;
import java.awt.*;

public class StartFrame extends JFrame {
    private final int WIDTH;
    private final int HEIGHT;
    private ImagePanel startPanel;
    //private ChessGameFrame frame;
    private ImageIcon back=new ImageIcon("resource\\Background\\start.jpg");

    public StartFrame(int width,int height){
        setTitle("Jungle");
        this.HEIGHT=height;
        this.WIDTH=width;
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        setVisible(true);
        background();
        addMultiplayerGameButton();
        //addSinglePlayerGameButton();
        addRuleButton();
    }
    //排名


    public int getHEIGHT() {
        return HEIGHT;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    private void addMultiplayerGameButton() {
        JButton button = new JButton("Multiplayer Game");
        button.addActionListener((e) -> //触发的事件
        {
            playMusic("/music/button.wav");
            startGame();
        });
        button.setLocation(WIDTH/2-125, HEIGHT / 2);
        button.setSize(250, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
//    private void addSinglePlayerGameButton() {
//        JButton button = new JButton("Single Player Game");
//        button.addActionListener((e) -> //触发的事件
//        {
//            playMusic("/music/button.wav");
//            startGame();
//        });
//        button.setLocation(WIDTH/2-125, HEIGHT / 2+80);
//        button.setSize(250, 60);
//        button.setFont(new Font("Rockwell", Font.BOLD, 20));
//        add(button);
//    }
    private void startGame(){
        ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);//窗口初始化
        GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard());
        mainFrame.setGameController(gameController);
        gameController.setStatusLabel(mainFrame.getStatusLabel());
        gameController.setTurnLabel(mainFrame.getTurnLabel());
        setVisible(false);
        mainFrame.setVisible(true);
    }
    private void background(){
        startPanel=new ImagePanel(back);
        setContentPane(startPanel);
        startPanel.setLayout(null);
    }
    private void playMusic(String musicPath){
        MusicPlayer musicPlayer=new MusicPlayer(getClass().getResource(musicPath),false);
        Thread music=new Thread(musicPlayer);
        music.start();
    }

    private void addRuleButton(){
        JButton rule=new JButton("Rules");
        rule.addActionListener((e)->{
            playMusic("/music/button.wav");
            JOptionPane.showMessageDialog(null, """
                     Introduction:\s
                    Jungle or Dou Shou Qi (斗兽棋), is a modern Chinese board game with an obscure history, as shown in the Figure 1.\s
                    The game is played on a 7×9 board and is popular with children in the Far East. Jungle is a two-player strategy\s
                    game and has been cited by The Playboy Winner's Guide to Board Games as resembling the Western game Stratego.\s

                    Pieces:
                    Each player owns 8 animal pieces representing different animals of various ranks, and higher ranked animals can\s
                    capture the animals of lower or equal rank. But there is a special case that elephant cannot capture the rat\s
                    while the rat can capture the eleplant. (Eleplant>Lion>Tiger>Leopard>Wolf>Dog>Cat>Rat) Each player moves\s
                    alternatively, and all pieces can move one square horizontally or vertically, but not diagonally. As shown in the\s
                    table, there are some special movements for lion, tiger and rat. These will be explained in detail:\s
                    Entering the river:\s
                    The rat is the only animal that may go onto a water square. After entering the river, the rat cannot be captured\s
                    by any piece on land.\s
                    Also, the rat in river cannot capture the eleplant on the land.\s
                    Jumping across the river:\s
                    The lion and tiger can jump over a river vertically or horizonally. They jump from a square on one edge of the river\s
                    to the next non-water square on the other side.\s
                    If that square contains an enemy piece of equal or lower rank, the lion or tiger capture it as part of their jump.
                    However, a jumping move is blocked (not permitted) if a rat of either color currently occupies any of the intervening\s
                    water squares.\s

                    Chessboard :
                    Jungle game has an interesting chessboard with differerent terrains including dens, traps and rivers. After the\s
                    initialization, the pieces start on squares with pictures corresponding to their animals, which are invariably shown\s
                    on the Jungle board.
                    The three kinds of special terrains are explained as:
                    Dens(兽穴): It is not allowed that the piece enters its own den. If the player's piece enters the dens of his/her opponent,\s
                    then the player wins.\s
                    Trap(陷阱): If a piece entering the opponents's trap, then its rank is reduced into 0 temporarily before exiting. The trapped\s
                    piece could be captured by any pieces of the defensing side.\s
                    River(河流): They are located in the center of the chessboard, each comprising 6 squares in a 2×3 rectangle. Only rats could\s
                    enter the river, and lions and tigers could jump across the river.\s""","Rules",JOptionPane.PLAIN_MESSAGE);

        });
        rule.setLocation(WIDTH/2-125, HEIGHT / 2+100 );
        rule.setSize(250, 60);
        rule.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(rule);
    }

}
