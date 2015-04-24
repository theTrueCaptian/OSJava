/**
 * Maeda Hanafi 11/23/10
 */
import java.net.Socket;
import java.net.ServerSocket;
import java.io.*;

public class EchoServer {

    public static void main(String[] args) throws Exception {

        // create socket
        int port = 5555;
        ServerSocket serverSocket = new ServerSocket(port);
        System.err.println("Started server on port " + port);

        // repeatedly wait for connections from clients, and process
        while (true) {
            // a "blocking" call which waits until a connection is requested
            Socket clientSocket = serverSocket.accept();
            System.err.println("Accepted connection from client");

            // open up streams              
            ObjectInputStream  inputStream = null;
            try {
            	inputStream = new ObjectInputStream(clientSocket.getInputStream());
            	            
            	// waits for data and reads it in until connection dies
            	Message recieve = null;		        
		        while(true){   
		        	recieve = (Message) inputStream.readObject();
		        	System.out.println("Client said: " + recieve);		           		       
            	}
		        
	         }catch (java.io.EOFException e){
	             System.out.println("Done reading message.");
	         }catch (ClassNotFoundException e){
	             System.out.println("Could not read message. exiting.");
	             System.exit(-1);
	         }catch (IOException e){
	        	 System.err.println(e.toString());
	             System.exit(-1);
	         }
        
            // close IO streams, then socket
            System.err.println("Closing connection with client");
            inputStream.close();
            clientSocket.close();
           break;
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
