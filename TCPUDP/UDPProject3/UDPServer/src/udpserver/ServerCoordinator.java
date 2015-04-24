
package udpserver;


import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author hanafim1
 */
public class ServerCoordinator extends Thread{
   ArrayList<ServerThread> threads = new ArrayList<ServerThread>();

    int clients=0;
    int portNumber = 4444;

    public ServerCoordinator(){
    }

    @Override
    public void run(){
        waitForclients();
    }

    public void waitForclients(){
        DatagramSocket serverSocket = null;
        boolean listening = true;

        try {
            serverSocket = new DatagramSocket(portNumber);
            while (listening){
                byte[] buf = new byte[256];

                // receive request
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                serverSocket.receive(packet);
                String str = new String(buf,0,0,packet.getLength());
                //System.out.println("Message  :"+ str);
                StringTokenizer st = new StringTokenizer(str, " ");
                String tempS = st.nextToken();
                if(tempS.equals("request")){
                    clients ++;
                    ServerThread temp = new ServerThread(serverSocket, packet, clients);
                    //System.out.println("Client number "+clients);
                    
                    temp.start();
                    threads.add(temp);
                    

                }else if(tempS.equals("update")){
                    int ID = Integer.parseInt(st.nextToken());
                    threads.get(ID-1).process(str);
                }
            }

        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(-1);
        }
    }



}
