package musichub.client;

import java.io.*;
import java.net.*;
import musichub.business.*;

public class SimpleClient {
	
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket socket;
	
	public String connect(String ip, int query, Object ... obj)
	{
		int port = 6666;
		String response = "null";
        try  {
			//create the socket; it is defined by an remote IP address (the address of the server) and a port number
			socket = new Socket(ip, port);

			//create the streams that will handle the objects coming and going through the sockets
			output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

            //sends the query
			output.writeObject(query);

			//receive string output from the server
			response = (String) input.readObject();

			int i = 0;

			while(response.equals("obj_req")) {
				output.writeObject(obj[i]);
				response = (String) input.readObject();
				i++;
			}

			System.out.println(response);

	    } catch  (UnknownHostException uhe) {
			uhe.printStackTrace();
		}
		catch  (IOException ioe) {
			ioe.printStackTrace();
		}
		catch  (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		finally {
			try {
				input.close();
				output.close();
				socket.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return response;
	}

}