package udpserver;
import java.net.*;
import java.io.*;
import java.util.*;

public class Server {

    static ServerThread printed1 = null, printed2 = null;
    static int printedIndex1, printedIndex2;
    static boolean displayFlag = false;
    static ServerCoordinator coordinator;

    public static void main(String[] args)  {

        coordinator= new ServerCoordinator( );
        coordinator.start();
        while(true){
             display();
        }
    }



    public static void display(){
        for(int i=0; i<coordinator.threads.size(); i++){
            for(int j=i+1; j<coordinator.threads.size(); j++){
               if(displayFlag )
                    waitForUpdate();
               //System.out.println("<" +coordinator.threads.get(i).clientID+" "+"("+coordinator.threads.get(i).x+", "+coordinator.threads.get(i).y+", "+coordinator.threads.get(i).z+")"+coordinator.threads.get(j).clientID+" "+"("+coordinator.threads.get(j).x+", "+coordinator.threads.get(j).y+", "+coordinator.threads.get(j).z+")>");
                ConstructString(coordinator.threads.get(i), coordinator.threads.get(j));

            }
        }
    }

    public static void waitForUpdate(){
        while(true){
            if((printed1.x!=coordinator.threads.get(printedIndex1).x) || (printed1.y!=coordinator.threads.get(printedIndex1).y) || (printed1.z!=coordinator.threads.get(printedIndex1).z)
                    || (printed2.x!=coordinator.threads.get(printedIndex2).x) || (printed2.y!=coordinator.threads.get(printedIndex2).y) || (printed2.y!=coordinator.threads.get(printedIndex2).y )
                    || !printed1.messageHeader.equals(coordinator.threads.get(printedIndex1).messageHeader) || !printed2.messageHeader.equals(coordinator.threads.get(printedIndex2).messageHeader)){
                displayFlag = false;
                return;
            }
        }
    }

    public static void ConstructString(ServerThread temp1, ServerThread temp2){
        long diffTime = temp1.time - temp2.time;
        if(diffTime<0)
            diffTime = -diffTime;

        int distance = getDistance(temp1.x, temp1.y, temp1.z, temp2.x, temp2.y, temp2.z);
        if(distance<0)
            distance = -distance;


        if(diffTime<=100 && distance<10){
            displayFlag = true;
            printed1 = temp1;
            printed2 = temp2;
            printedIndex1=temp1.clientID-1;
            printedIndex2=temp2.clientID-1;
            if(displayFlag){
                System.out.println("<"+diffTime+" "+temp1.clientID+" "+"("+temp1.x+", "+temp1.y+", "+temp1.z+")"+temp2.clientID+" "+"("+temp2.x+", "+temp2.y+", "+temp2.z+") "+distance+">");
            }
        }
    }

    public static int getDistance(int x1, int y1, int z1, int x2, int y2, int z2){
        return (int) Math.sqrt(((x1-x2)*(x1-x2))+((y1-y2)*(y1-y2))+((z1-z2)*(z1-z2)));

    }


}