package musichub.audio_player;

import javax.sound.sampled.*;
import java.io.*;
import java.util.*;

/**
 * This function is used to listen to music
 * @param filepath
 * @author Pierre-Louis
 * */

public class Audio{

    public Audio(){}

    public void play(String filepath) throws Exception{
        System.out.println("You can use : p -> play | s -> stop | r -> reset | c -> close the player | q -> return to the main menu");

        //set up the scanner to get the user's input
        Scanner scan = new Scanner(System.in);
        String choice = scan.nextLine();

        //error handler
        File error_file = new File("error.txt");
        PrintWriter err_handler = new PrintWriter(error_file);
        Date date = new Date();

        try{
            //launche the music to play
            File file = new File(filepath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            while(!choice.equals("q")){//do different action in function of user's input
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

        }catch(Exception ex){//error handler, print the error in a .txt file
            System.out.println("Error with playing sound");
            ex.printStackTrace(err_handler);
            err_handler.println(date.toString());
        }
        err_handler.close();
    }
}