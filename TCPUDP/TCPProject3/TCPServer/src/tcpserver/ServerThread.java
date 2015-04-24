
package tcpserver;

/**
 *
 * @author SMART User
 */
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerThread extends Thread {

    int msgNumber = 0;
    PrintWriter out;
    BufferedReader in;
    int clientID;
    int x, y, z;
    long time;
    boolean update = false;
    boolean sleep = false;
    String messageHeader;
    Socket socket = null;
    
    public ServerThread(Socket socket,  int clientID) {
	super("ServerThread");
	this.clientID = clientID;
        this.socket =socket;
        System.out.println("handling client "+clientID);
    }

    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            out.flush();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            sendClientID();
            recieveFromClient();
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void sendClientID() {
        System.out.println("clientID to send to client:"+clientID);

        String stringID = ""+clientID;
        System.out.println(stringID);
        out.println(stringID);
    }

   private void recieveFromClient(){
    	try{
            String message;
            System.out.println("reading message from client");
            while((message=in.readLine())!=null){
                
                process(message);
                message = "";

            }

        }catch(IOException ioException){
            ioException.printStackTrace();
        }

    }

    private  void process(String string){
        StringTokenizer st = new StringTokenizer(string, " ");
        messageHeader = st.nextToken();
        if(messageHeader.equals("update")){
            int inClientID = Integer.parseInt(st.nextToken());
            if(clientID==inClientID){
                time = Long.parseLong(st.nextToken());

                x = Integer.parseInt(st.nextToken());
                y = Integer.parseInt(st.nextToken());
                z = Integer.parseInt(st.nextToken());
                update = true;
                System.out.println("client #"+clientID+": "+x+" "+y+" "+z+" "+time);
            }
        }

    }

}
