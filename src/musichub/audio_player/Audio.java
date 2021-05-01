package musichub.audio_player;

import javax.sound.sampled.*;
import java.io.*;
import java.util.*;

public class Audio{

    public Audio(){}

    public void play(String filepath){
        System.out.println("You can use : p -> play | s -> stop | r -> reset | c -> close | q -> quit");
        Scanner scan = new Scanner(System.in);
        String choice = scan.nextLine();
        try{
            File file = new File(filepath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            while(!choice.equals("q")){
                switch(choice){
                    case("p"):
                        clip.start();
                        choice = scan.nextLine();
                    break;
                    case("s"):
                        clip.stop();
                        choice = scan.nextLine();
                    break;
                    case("r"):
                        clip.setMicrosecondPosition(0);
                        choice = scan.nextLine();
                    break;
                    case("c"):
                        clip.close();
                        choice = scan.nextLine();
                    break;
                    default:
                }
            }

        }catch(Exception ex){
            System.out.println("Error with playing sound");
        }
    }
}