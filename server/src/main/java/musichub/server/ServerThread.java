package musichub.server;

import java.io.*;
import java.net.*;
import musichub.business.*;
import java.util.*;

 
/**
 * This thread is responsible to handle client connection and send information back to the client
 * @author Alexandre Meyer
 */

public class ServerThread extends Thread {
    private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private MusicHub theHub;
 
    public ServerThread(Socket socket,MusicHub theHub) {
        this.socket = socket;
        this.theHub = theHub;
    }
 
    public void run() {
        try {

			//create the streams that will handle the objects coming through the sockets
			input = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());
 
 			int query = (int) input.readObject();  //read the object received through the stream and deserialize it
			System.out.println("Query " + query + " received.");

			switch (query) {
				case 100:
					//handshake
					output.writeObject("Connected to local server");
					break;
				case 200:
					//songs of an album
					output.writeObject("obj_req");
					String albumTitle_200 = (String) input.readObject();
					try {
						output.writeObject(theHub.getAlbumSongs(albumTitle_200).toString());
					} catch (NoAlbumFoundException ex) {
						output.writeObject(ex.getMessage());
					}
					break;
				case 201:
					//songs of an album ordered by genre
					output.writeObject("obj_req");
					String albumTitle_201 = (String) input.readObject();
					try {
						output.writeObject(theHub.getAlbumSongsSortedByGenre(albumTitle_201).toString());
					} catch (NoAlbumFoundException ex) {
						output.writeObject(ex.getMessage());
					}
					break;
				case 210:
					//all albums
					output.writeObject(theHub.getAlbumsTitlesSortedByDate());
					break;
				case 220:
					//all songs
					output.writeObject(theHub.getAllSongs());
					break;
				case 230:
					//audiobooks
					output.writeObject(theHub.getAudiobooksTitlesSortedByAuthor());
					break;
				case 240:
					//playlists
					output.writeObject(theHub.getAllPlaylists());
					break;
				case 250:
					output.writeObject(theHub.getAllSongs() + theHub.getAudiobooksTitlesSortedByAuthor());
					break;
				case 400:
					//add a song
					output.writeObject("obj_req");
					Song s = (Song) input.readObject();
					theHub.addElement(s);
					output.writeObject("Song created!");
					break;
				case 401:
					//add song to album
					output.writeObject("obj_req");
					String songTitle_401 = (String) input.readObject();
					output.writeObject("obj_req");
					String albumTitle_401 = (String) input.readObject();
					try {
						theHub.addElementToAlbum(songTitle_401, albumTitle_401);
					} catch (NoAlbumFoundException ex){
						System.out.println (ex.getMessage());
					} catch (NoElementFoundException ex){
						System.out.println (ex.getMessage());
					}
					output.writeObject("Song added to the album!");
					break;
				case 410:
					//add an album
					output.writeObject("obj_req");
					Album a = (Album) input.readObject();
					theHub.addAlbum(a);
					output.writeObject("Album created");
					break;
				case 420:
					//add an audiobook
					output.writeObject("obj_req");
					AudioBook b = (AudioBook) input.readObject();
					theHub.addElement(b);
					output.writeObject("Audiobook created");
					break;
				case 500:
					//create playlist
					output.writeObject("obj_req");
					PlayList pl = (PlayList) input.readObject();
					theHub.addPlaylist(pl);
					output.writeObject("Playlist created");
					break;
				case 510:
					//add element to playlist
					output.writeObject("obj_req");
					String elementTitle_510 = (String) input.readObject();
					output.writeObject("obj_req");
					String playlistTitle_510 = (String) input.readObject();
					try {
						theHub.addElementToPlayList(elementTitle_510, playlistTitle_510);
					} catch (NoPlayListFoundException ex) {
						System.out.println (ex.getMessage());
					} catch (NoElementFoundException ex) {
						System.out.println (ex.getMessage());
					}
					output.writeObject("Element created");
					break;
				case 520:
					//deletus playlistus
					output.writeObject("obj_req");
					String playlistTitle_520 = (String) input.readObject();
					try {
						theHub.deletePlayList(playlistTitle_520);
					}	catch (NoPlayListFoundException ex) {
						System.out.println (ex.getMessage());
					}
					output.writeObject("Playlist deleted!");
					break;
				case 600:
					//save
					theHub.saveElements();
					theHub.saveAlbums();
					theHub.savePlayLists();
					output.writeObject("Elements, albums and playlists saved!");
					break;
				case 701:
					//outputs the filename of a given song
					output.writeObject("obj_req");
					String songTitle_701 = (String) input.readObject();
					output.writeObject(theHub.getSongFilename(songTitle_701));
					break;
				default:
					System.out.println("Not a valid query...");
					output.writeObject("Not a valid query...");
					break;
			}

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();

		} catch (ClassNotFoundException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
			try {
				output.close();
				input.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
    }

}