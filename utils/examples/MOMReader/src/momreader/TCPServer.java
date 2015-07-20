/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package momreader;

/**
 *
 * @author Jun
 */

import java.io.*;
import java.net.*;
public class TCPServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here

        
        String mom_ip = "128.130.172.214";
        String mom_port = "9124";
        String queue_name = args[0];
        int socketPort = Integer.parseInt(args[1]);
        
        QueueClient queueClient = new QueueClient(mom_ip, mom_port, queue_name, socketPort);
        queueClient.run();
        
//        
//        
//        String clientSentence;
//        String capitalizedSentence;
//        ServerSocket welcomeSocket = new ServerSocket(Integer.parseInt(args[0]));
//        Socket connectionSocket = welcomeSocket.accept();
//        
//        
//        while (true) {
//            
//           // BufferedReader inFromClient
//            //        = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
//            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
//            clientSentence = "hello world 123 123 1235";
//            System.out.println(System.currentTimeMillis()+" - Received: " + clientSentence);
//            capitalizedSentence = clientSentence.toUpperCase() + '\n';
//            outToClient.writeBytes(capitalizedSentence);
//          
//            try {
//                Thread.sleep(2000);
//
//            } catch (InterruptedException ex) {
//
//            }
//        }
    }

}
