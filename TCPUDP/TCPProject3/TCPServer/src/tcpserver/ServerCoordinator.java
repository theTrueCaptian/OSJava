package tcpserver;

import java.io.IOException;
import java.net.*;
import java.util.*;

/**
 *
 * @author SMART User
 */
public class ServerCoordinator extends Thread{
    ArrayList<ServerThread> threads;
    int clients=0;
    int portNumber = 4444;

    public ServerCoordinator(ArrayList<ServerThread> inthreads){
        threads = inthreads;
    }

    @Override
    public void run(){
        waitForclients();
    }

    public void waitForclients(){
        ServerSocket serverSocket = null;
        boolean listening = true;

        try {
            serverSocket = new ServerSocket(portNumber);
            while (listening){
                clients ++;
                ServerThread temp = new ServerThread(serverSocket.accept(),  clients);
                System.out.println("Client number "+clients);
                threads.add(temp);
                temp.start();
            }

        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(-1);
        }
    }



}
