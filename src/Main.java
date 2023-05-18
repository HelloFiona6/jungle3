import controller.GameController;
import model.Chessboard;
import view.ChessGameFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);//窗口初始化
            GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard());
            mainFrame.setGameController(gameController);
            gameController.setStatusLabel(mainFrame.getStatusLabel());
            gameController.setTurnLabel(mainFrame.getTurnLabel());
            mainFrame.setVisible(true);
            // todo 存储用户信息
        });
        //TODO 创建音乐线程实例+启动实例+调整音量
    }
}
