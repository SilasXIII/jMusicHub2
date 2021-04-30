package musichub.audio_player;

import javax.sound.sampled.*;
import java.io.*;
import java.util.*;

public class Audio{

    public Audio(){}

    public void play(){
        String filepath="src/musichub/audio_player/ps-passion.wav";
        try{
            File file = new File(filepath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }catch(Exception ex){
            System.out.println("Error with playing sound");
        }
    }
}