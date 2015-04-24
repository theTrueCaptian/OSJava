package udpclient;

import java.io.IOException;
import java.net.*;
import java.util.Calendar;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SMART User
 */
public class Client {

     static DatagramSocket socket=null;
     static DatagramPacket packet=null;
     static int clientID;
     static int x, y, z;
     static long time;

    public static void main(String[] args) {
        try {
            socket = new DatagramSocket();

        } catch (SocketException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        sendRequest();
        receiveID();
        moveAround();
    }

    public static void sendRequest(){
        String message = "request ";
        System.out.println(message);
        byte[] buf = message.getBytes();
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        packet = new DatagramPacket(buf, buf.length, address, 4444);
        packet.setData(buf);
        try {
            socket.send(packet);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void receiveID(){
        byte[] buf = new byte[256];
        packet = new DatagramPacket(buf, buf.length);
        try {
            socket.receive(packet);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

        clientID = Integer.parseInt(new String(buf,0,0,packet.getLength()));
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
        byte[] buf = inMessage.getBytes();
        packet.setData(buf);
        try {
            socket.send(packet);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
