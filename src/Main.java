import music.MusicPlayer;
import view.StartFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StartFrame main=new StartFrame(500,810);
            // todo 存储用户信息
        });
        //TODO 创建音乐线程实例+启动实例+调整音量
        MusicPlayer musicPlayer=new MusicPlayer(Main.class.getResource("/music/background.wav"),true);
        Thread music=new Thread(musicPlayer);
        music.start();
    }
}
