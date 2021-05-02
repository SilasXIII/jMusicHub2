package common;
import java.io.Serializable;
/**
 * this class is a test
 */
public class Student implements Serializable		//must implement Serializable in order to be sent over a Socket
{
    private int id;
    private String name;
   
    public Student (int id, String name) {
		this.id=id;
		this.name=name;
	}
	/**
	 * getter for id
	 * @return id of student
	 */
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}		
}