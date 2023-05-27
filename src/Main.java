import controller.GameController;
import model.Chessboard;
import music.MusicPlayer;
import view.ChessGameFrame;
import view.StartFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StartFrame main=new StartFrame(500,810);
//            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);//窗口初始化
//            GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard());
//            mainFrame.setGameController(gameController);
//            gameController.setStatusLabel(mainFrame.getStatusLabel());
//            gameController.setTurnLabel(mainFrame.getTurnLabel());
//            mainFrame.setVisible(true);
            // todo 存储用户信息
        });
        //TODO 创建音乐线程实例+启动实例+调整音量
        MusicPlayer musicPlayer=new MusicPlayer(Main.class.getResource("/music/background.wav"),true);
        Thread music=new Thread(musicPlayer);
        music.start();
    }
}
