package musichub.main;
import musichub.business.*;
import musichub.client.*;
import musichub.audio_player.*;

import java.io.*;
import java.util.*;
import java.beans.XMLEncoder;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;

public class Main
{
 	public static void main (String[] args) throws Exception{

		String filepath = "../files/local/";


		System.out.println("Starting MusicHub... Attempting to connect to local server");

		SimpleClient c1 = new SimpleClient();
		try {
			c1.connect("localhost",100,"null");
		} catch(Exception e) {
			System.out.println("Please boot local server");
		}

		Audio player = new Audio();

		System.out.println("Type h for available commands");

		Scanner scan = new Scanner(System.in);
		String choice = scan.nextLine();

		String albumTitle = "null";

		if (choice.length() == 0) System.exit(0);

		while (choice.charAt(0)!= 'q') 	{
			System.out.println("Type h for available commands");
			switch (choice.charAt(0)) 	{
				case 'i':
					//play 1 song
					System.out.println("Choose a song to play, list of available songs :");

					c1.connect("localhost",220,"null");

					String songToPlay = scan.nextLine();

					String songContent = c1.connect("localhost",701,songToPlay);
					
					player.play(filepath+songContent);

					printAvailableCommands();
					choice = scan.nextLine();
				break;
				case 'h':
					printAvailableCommands();
					choice = scan.nextLine();
				break;
				case 't':
					//album titles, ordered by date
					c1.connect("localhost",210,"null");
					printAvailableCommands();
					choice = scan.nextLine();
				break;
				case 'g':
					//songs of an album, sorted by genre
					System.out.println("Songs of an album sorted by genre will be displayed; enter the album name, available albums are:");
					c1.connect("localhost",210,"null");

					albumTitle = scan.nextLine();

					c1.connect("localhost",201,albumTitle);

					printAvailableCommands();
					choice = scan.nextLine();
				break;
				case 'd':
					//songs of an album
					System.out.println("Songs of an album will be displayed; enter the album name, available albums are:");

					c1.connect("localhost",210,"null");

					albumTitle = scan.nextLine();

					c1.connect("localhost",200,albumTitle);

					printAvailableCommands();
					choice = scan.nextLine();
				break;
				case 'u':
					//audiobooks ordered by author
					c1.connect("localhost",230,"null");

					printAvailableCommands();
					choice = scan.nextLine();
				break;
				case 'c':
					// add a new song
                    System.out.println("Enter a new song: ");
                    System.out.println("Song title: ");
                    String title = scan.nextLine();
                    System.out.println("Song genre (jazz, classic, hiphop, rock, pop, rap):");
                    String genre = scan.nextLine();
                    System.out.println("Song artist: ");
                    String artist = scan.nextLine();
                    System.out.println ("Song length in seconds: ");
                    int length = Integer.parseInt(scan.nextLine());
                    System.out.println("Song content: ");
                    String content = scan.nextLine();
                    Song s = new Song (title, artist, length, content, genre);

                    c1.connect("localhost",400,s);

                    printAvailableCommands();
                    choice = scan.nextLine();
                break;
				case 'a':
					// add a new album
                    System.out.println("Enter a new album: ");
                    System.out.println("Album title: ");
                    String aTitle = scan.nextLine();
                    System.out.println("Album artist: ");
                    String aArtist = scan.nextLine();
                    System.out.println ("Album length in seconds: ");
                    int aLength = Integer.parseInt(scan.nextLine());
                    System.out.println("Album date as YYYY-DD-MM: ");
                    String aDate = scan.nextLine();
                    Album a = new Album(aTitle, aArtist, aLength, aDate);

					c1.connect("localhost",410,a);

                    printAvailableCommands();
                    choice = scan.nextLine();
				break;
				case '+':
					//add a song to an album:
					System.out.println("Add an existing song to an existing album");
					System.out.println("Type the name of the song you wish to add. Available songs: ");

					c1.connect("localhost",220,"null");

					String songTitle = scan.nextLine();

					c1.connect("localhost",210,"null");

					System.out.println("Type the name of the album you wish to enrich. Available albums: ");

					String titleAlbum = scan.nextLine();

					c1.connect("localhost",401,songTitle,titleAlbum);

					printAvailableCommands();
                    choice = scan.nextLine();
					break;
				case 'l':
					// add a new audiobook
                    System.out.println("Enter a new audiobook: ");
                    System.out.println("AudioBook title: ");
                    String bTitle = scan.nextLine();
                    System.out.println("AudioBook category (youth, novel, theater, documentary, speech)");
                    String bCategory = scan.nextLine();
                    System.out.println("AudioBook artist: ");
                    String bArtist = scan.nextLine();
                    System.out.println ("AudioBook length in seconds: ");
                    int bLength = Integer.parseInt(scan.nextLine());
                    System.out.println("AudioBook content: ");
                    String bContent = scan.nextLine();
                    System.out.println("AudioBook language (french, english, italian, spanish, german)");
                    String bLanguage = scan.nextLine();
                    AudioBook b = new AudioBook (bTitle, bArtist, bLength, bContent, bLanguage, bCategory);

                    c1.connect("localhost",420,b);

                    printAvailableCommands();
                    choice = scan.nextLine();
				break;
				case 'p':
					//create a new playlist from existing elements
					System.out.println("Add an existing song or audiobook to a new playlist");
					System.out.println("Existing playlists:");

					c1.connect("localhost",240,"null");

					System.out.println("Type the name of the playlist you wish to create:");
					String playListTitle = scan.nextLine();
					PlayList pl = new PlayList(playListTitle);

					c1.connect("localhost",500,pl);
					System.out.println("Available elements: ");

					c1.connect("localhost",250,"null");

					while (choice.charAt(0)!= 'n') 	{
						System.out.println("Type the name of the audio element you wish to add or 'n' to exit:");
						String elementTitle = scan.nextLine();

						c1.connect("localhost",510,elementTitle,playListTitle);

						System.out.println("Type y to add a new one, n to end");
						choice = scan.nextLine();
					}
					System.out.println("Playlist created!");
					printAvailableCommands();
					choice = scan.nextLine();
					break;
				case 'o':
					//display all playlists
					System.out.println("Add an existing song or audiobook to a new playlist");
					System.out.println("Existing playlists:");

					c1.connect("localhost",240,"null");
					printAvailableCommands();
					choice = scan.nextLine();
					break;
				case '-':
					//delete a playlist
					System.out.println("Delete an existing playlist. Available playlists:");
					c1.connect("localhost",240,"null");
					String plTitle = scan.nextLine();

					c1.connect("localhost",520,plTitle);

					System.out.println("Playlist deleted!");
					printAvailableCommands();
					choice = scan.nextLine();
				break;
				case 'e':
					c1.connect("localhost",250,"null");

					printAvailableCommands();
					choice = scan.nextLine();
					break;
				case 's':
					//save elements, albums, playlists
					c1.connect("localhost",600,"null");

					printAvailableCommands();
					choice = scan.nextLine();
				break;

				default:

				break;
			}
		}
		scan.close();

	}

	private static void printAvailableCommands() {
		System.out.println("i: to listen to music");
		System.out.println("t: display the album titles, ordered by date");
		System.out.println("g: display songs of an album, ordered by genre");
		System.out.println("d: display songs of an album");
		System.out.println("u: display audiobooks ordered by author");
		System.out.println("o: display all the playlists");
		System.out.println("e: display all the audio elements");
		System.out.println("c: add a new song");
		System.out.println("a: add a new album");
		System.out.println("+: add a song to an album");
		System.out.println("l: add a new audiobook");
		System.out.println("p: create a new playlist from existing songs and audio books");
		System.out.println("-: delete an existing playlist");
		System.out.println("s: save elements, albums, playlists");
		System.out.println("q: quit program");
	}
}
