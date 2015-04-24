/**
 * SocketClient.java
 * Copyright (c) 2002 by Dr. Herong Yang
 * Modified by Maeda Hanafi 11/23/10
 */
import java.io.*;
import java.net.*;
import java.util.*;


public class SocketClient {
	public static void main(String[] args) {

		try{
			//create socket
			Socket socket = new Socket("localhost",5555);
			        
			//scan user input
			Scanner input = new Scanner(System.in);
			String userInput = "";
			
			System.out.print("> ");
			userInput = input.nextLine();
			
			//create output stream
			ObjectOutputStream outputStream = null;
		    try {
		    	outputStream = new ObjectOutputStream(socket.getOutputStream());
		    }catch (IOException e){
		      System.out.println("Could not create output socket. exiting.");
		      System.exit(-1);
		    }
		    
		    //create Message
		    Message message1 = new Message(userInput);
		    outputStream.writeObject(message1);
		    
		    //close stream and socket
		    System.out.println("\nSucessfully sent\n");
		    outputStream.close();		    			
			socket.close();
		}catch(IOException e){
			System.err.println(e.toString());
		}
	}
}

//message that implement Serializable interface 
class Message implements Serializable{
  private String n;

  public Message() {}
  public Message(String n){
	  this.n = n;
  }
  public String toString(){ 
	  return this.n; 
  }
}