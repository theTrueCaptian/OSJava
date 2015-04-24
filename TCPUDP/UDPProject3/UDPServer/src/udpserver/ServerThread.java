
package udpserver;

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

    static int lastId = 1;
    int id = 0;
    int msgNumber = 0;
    PrintWriter out;
    BufferedReader in;
    int clientID;
    int x, y, z;
    long time;
    boolean update = false;
    boolean sleep = false;
    String messageHeader;
    DatagramPacket packet = null;
    DatagramSocket socket = null;
    public ServerThread(DatagramSocket socket, DatagramPacket packet, int clientID) {
	super("ServerThread");
	this.clientID = clientID;
	id = lastId++;
        this.socket =socket;
        this.packet =packet;
        System.out.println("handling client "+clientID);
    }

    public void run() {
        sendClientID();
        recieveFromClient();

    }

    private void sendClientID() {
        System.out.println("clientID to send to client:"+clientID);

        String stringID = ""+clientID;
        System.out.println(stringID);
        byte[] buf = stringID.getBytes();

        packet.setData(buf);
        try {
            socket.send(packet);
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void recieveFromClient(){
    	try{
            String message;
            System.out.println("reading message from client");
            byte[] buf = new byte[256];
            while(true){
                DatagramPacket packet1= new DatagramPacket(buf, buf.length);
                socket.receive(packet1);
                message = new String(buf,0,0,packet1.getLength());

                process(message);
                message = "";

            }

        }catch(IOException ioException){
            ioException.printStackTrace();
        }

    }

    public  void process(String string){
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
