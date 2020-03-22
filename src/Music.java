import java.applet.*;
import java.io.File;

public class Music {
    private AudioClip backMusic;
    private AudioClip pushMusic;
    private AudioClip successMusic;


    public Music() {
        try {
            backMusic = Applet.newAudioClip((new File("resource/music/menu.wav").toURI().toURL()));
            pushMusic = Applet.newAudioClip((new File("resource/music/chesson.wav")).toURI().toURL());

        }catch (Exception e){
            e.printStackTrace();
        }
        successMusic = null;
    }

    public void BackMusicStop(){
        backMusic.stop();
    }
    public void BackMusicLoop(){
        backMusic.loop();
    }

    public void PusnMusicStart(){
        pushMusic.play();
    }

    public void PushMusicStop(){
        pushMusic.stop();
    }
}
