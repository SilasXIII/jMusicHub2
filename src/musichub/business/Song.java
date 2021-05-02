package musichub.business;

import org.w3c.dom.*;
import java.io.*;
import java.util.*;


public class Song extends AudioElement {
	private Genre genre;
	
	public Song (String title, String artist, int length, String uid, String content, String genre) {
		super (title, artist, length, uid, content);
		this.setGenre(genre);
	}
	
	public Song (String title, String artist, int length, String content, String genre) {
		super (title, artist, length, content);
		this.setGenre(genre);
	}
	
	public Song (Element xmlElement) throws Exception {
		super(xmlElement);
		File error_file = new File("error.txt");
		PrintWriter err_handler = new PrintWriter(new FileOutputStream(error_file, true));
		Date date = new Date();
		try {
			this.setGenre(xmlElement.getElementsByTagName("genre").item(0).getTextContent());
		} catch (Exception ex) {
			ex.printStackTrace(err_handler);
			err_handler.println(date.toString());
		}
		err_handler.close();
	}
	
	public void setGenre (String genre) {	
		switch (genre.toLowerCase()) {
			case "jazz":
				this.genre = Genre.JAZZ;
				break;
			case "classic":
				this.genre = Genre.CLASSIC;
				break;
			case "hiphop":
				this.genre = Genre.HIPHOP;
				break;
			case "rock":
				this.genre = Genre.ROCK;
				break;
			case "pop":
				this.genre = Genre.POP;
				break;
			case "rap":
				this.genre = Genre.RAP;
				break;
			default:
				break;
		}
	} 

	public String getGenre () {
		return genre.getGenre();
	}
	
	public String toString() {
		return super.toString() + ", Genre = " + getGenre() + "\n";
	}	
	
	public void createXMLElement(Document document, Element parentElement) {
		// song element
        Element song = document.createElement("song");

		super.createXMLElement(document, song);
		
		Element genreElement = document.createElement("genre");
        genreElement.appendChild(document.createTextNode(genre.getGenre()));
        song.appendChild(genreElement);
		
		parentElement.appendChild(song);
		return;
	}
}