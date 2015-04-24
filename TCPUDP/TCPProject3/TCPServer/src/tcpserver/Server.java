
package tcpserver;

import java.util.ArrayList;

/**
 *
 * maeda hanafi
 */

public class Server {

    static ArrayList<ServerThread> thread = new ArrayList<ServerThread>();
    static ServerThread printed1 = null, printed2 = null;
    static int printedIndex1, printedIndex2;
    static boolean displayFlag = false;

    public static void main(String[] args)  {
        ServerCoordinator coordinator = new ServerCoordinator(thread );
        coordinator.start();
        while(true){
             display();
        }
    }



    public static void display(){
        for(int i=0; i<thread.size(); i++){
            for(int j=i+1; j<thread.size(); j++){
                
                if(displayFlag )
                    waitForUpdate();
                ConstructString(thread.get(i),i+1, thread.get(j),j+1);

            }
        }
    }

    public static void waitForUpdate(){
        while(true){

            if((printed1.x!=thread.get(printedIndex1).x) || (printed1.y!=thread.get(printedIndex1).y) || (printed1.z!=thread.get(printedIndex1).z)
                    || (printed2.x!=thread.get(printedIndex2).x) || (printed2.y!=thread.get(printedIndex2).y) || (printed2.y!=thread.get(printedIndex2).y )
                      || !printed1.messageHeader.equals(thread.get(printedIndex1).messageHeader) || !printed2.messageHeader.equals(thread.get(printedIndex2).messageHeader)){

                displayFlag = false;
                return;
            }
        }
    }

    public static void ConstructString(ServerThread temp1,int ID1, ServerThread temp2,int ID2){
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
            printedIndex1=ID1-1;
            printedIndex2=ID2-1;
            if(displayFlag){
                System.out.println("<"+diffTime+" "+ID1+" "+"("+temp1.x+", "+temp1.y+", "+temp1.z+")"+ID2+" "+"("+temp2.x+", "+temp2.y+", "+temp2.z+") "+distance+">");
            }
        }
    }

    public static int getDistance(int x1, int y1, int z1, int x2, int y2, int z2){
        return (int) Math.sqrt(((x1-x2)*(x1-x2))+((y1-y2)*(y1-y2))+((z1-z2)*(z1-z2)));

    }


}

