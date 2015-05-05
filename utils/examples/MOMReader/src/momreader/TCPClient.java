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
public class TCPClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here

        String sentence;
        String modifiedSentence;
        //BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        
        
        Socket clientSocket = new Socket("localhost", Integer.parseInt(args[0]));
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
       
        sentence = "hello world 123 123 12345";
        outToServer.writeBytes(sentence + '\n');
        modifiedSentence = inFromServer.readLine();
        System.out.println("FROM SERVER: " + modifiedSentence);
        clientSocket.close();
    }

}
