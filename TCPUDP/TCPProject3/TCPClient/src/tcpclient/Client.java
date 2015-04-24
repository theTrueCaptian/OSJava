package tcpclient;



import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;

/**
 *
 * maeda hanfi
 */
public class Client {

     static Socket socket=null;
     static int clientID;
     static int x, y, z;
     static long time;
     static PrintWriter out = null;
     static BufferedReader in = null;

    public static void main(String[] args)  {
        try {
            socket = new Socket("localhost", 4444);

            out = new PrintWriter(socket.getOutputStream(), true);
            out.flush();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        receiveID();
        moveAround();
    }



    public static void receiveID() {
        try {
            clientID = Integer.parseInt(in.readLine());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("client ID:"+clientID);
        


    }

    public static void moveAround(){
        while(true){
            try {
                generateRandomNumber();
                String message = constructMessage();
                System.out.println(message);
                sendMessage(message);
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void generateRandomNumber(){
        Random generator = new Random();
        x = generator.nextInt(101);
        y = generator.nextInt(101);
        z = generator.nextInt(101);
    }

    public static String constructMessage(){
        Calendar now = Calendar.getInstance();
        time = now.getTimeInMillis();
        return "update " + clientID + " " + time + " " + x + " " + y+" "+z;
    }

    public static void sendMessage(String inMessage){
        out.println(inMessage);
        out.flush();

    }
}
