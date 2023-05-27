package music;

import javax.sound.sampled.*;
import java.net.URL;

public class MusicPlayer implements Runnable{
    private URL musicPath;
    private boolean isLoop=false;
    //private Thread mainThread;
    //private float volume=7;

    private AudioInputStream audioInputStream;
    private AudioFormat audioFormat;
    private SourceDataLine sourceDataLine;
    public MusicPlayer(URL musicPath,boolean isLoop){
        this.musicPath=musicPath;
        this.isLoop=isLoop;
    }

    @Override
    public void run() {
        try {
            Clip clip = AudioSystem.getClip();
            audioInputStream = AudioSystem.getAudioInputStream(this.musicPath);
            clip.open(audioInputStream);
            if(isLoop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }else{
                clip.start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
