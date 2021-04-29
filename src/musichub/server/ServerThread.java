package musichub.server;

import java.io.*;
import java.net.*;
import musichub.business.*;
 
/**
 * This thread is responsible to handle client connection.
 */
public class ServerThread extends Thread {
    private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
 
    public ServerThread(Socket socket) {
        this.socket = socket;
    }
 
    public void run() {
        try {
			//create a musichub instance
			MusicHub theHub = new MusicHub ();

			//create the streams that will handle the objects coming through the sockets
			input = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());
 
 			String text = (String)input.readObject();  //read the object received through the stream and deserialize it
			System.out.println("server received album :" + text);
			
			//Album album = new Album(1234, "john.doe");
			output.writeObject(theHub.getAlbumsTitlesSortedByDate());		//serialize and write the Student object to the stream
 
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