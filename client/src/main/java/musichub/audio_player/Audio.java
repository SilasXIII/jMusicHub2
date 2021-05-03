package musichub.audio_player;

import javax.sound.sampled.*;
import java.io.*;
import java.util.*;

/**
 * This function is used to listen to music
 * @param path to the .wav file
 * @author Pierre-Louis
 * */

public class Audio{

    public Audio(){}

    public void play(String filepath) throws Exception{
        System.out.println("You can use : p -> play | s -> stop | r -> reset | c -> close the player | q -> return to the main menu");

        Scanner scan = new Scanner(System.in);
        String choice = scan.nextLine();

        File error_file = new File("error.txt");
        PrintWriter err_handler = new PrintWriter(error_file);
        Date date = new Date();

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
            ex.printStackTrace(err_handler);
            err_handler.println(date.toString());
        }
        err_handler.close();
    }
}