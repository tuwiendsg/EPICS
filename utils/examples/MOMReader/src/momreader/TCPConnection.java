/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package momreader;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jun
 */
public class TCPConnection implements Runnable{
    int socketPort;

    public TCPConnection(int socketPort) {
        this.socketPort = socketPort;
    }

    
    
    
    @Override
    public void run() {
        
       
        try {
            String clientSentence="";
            String capitalizedSentence= "";
            ServerSocket welcomeSocket = new ServerSocket(socketPort);
            Socket connectionSocket = welcomeSocket.accept();
            
            
            while (true) {
                
           // BufferedReader inFromClient
            //        = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            clientSentence = getShareData();
            System.out.println(System.currentTimeMillis()+" - Sending: " + clientSentence);
            capitalizedSentence = clientSentence.toUpperCase() + '\n';
            outToClient.writeBytes(capitalizedSentence);
                
                try {
                    Thread.sleep(2000);
                    
                } catch (InterruptedException ex) {
                    
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(TCPConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
    
    }
    
    
    private String getShareData(){
        
        String rs ="";
        
        if (ShareData.shareData.size()>0){
            rs = ShareData.shareData.remove(0);
        }
        
        
        return  rs;
    }
    
    
}
