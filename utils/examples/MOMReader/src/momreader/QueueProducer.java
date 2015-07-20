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

import org.apache.activemq.ActiveMQConnectionFactory;
 
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
 


public class QueueProducer {
    
    private String mom_ip;
    private String mom_port;
    private String mon_queue;
    private String url;
    
    public QueueProducer(String mom_ip, String mom_port, String mon_queue) {
        this.mom_ip = mom_ip;
        this.mom_port = mom_port;
        this.mon_queue = mon_queue;
        configure();
    }
    
    
    
    
    public void produce(){
         
        
        try {
                // Create a ConnectionFactory
             
             
                ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
 
                // Create a Connection
                Connection connection = connectionFactory.createConnection();
                connection.start();
 
                // Create a Session
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
 
                // Create the destination (Topic or Queue)
                Destination destination = session.createQueue(mon_queue);
 
                // Create a MessageProducer from the Session to the Topic or Queue
                MessageProducer producer = session.createProducer(destination);
                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
 
            for (int i= 0 ;i<100;i++){ 
                 String text = "SEND BLA BLA BLA " + i;
                 
                 MapMessage message = session.createMapMessage();
                 message.setString("StrAAA", text);
                
                System.out.println("Sent message: "+ text );
                producer.send(message);
                
               try {
                        Thread.sleep(1000);

                    } catch (InterruptedException ex) {

                    }
             }
             
             
               
 
                // Clean up
                session.close();
                connection.close();
            }
            catch (Exception e) {
                System.out.println("Caught: " + e);
                e.printStackTrace();
            }
        
         
    }
    
    private void configure() {
       
        
        url = "tcp://" + mom_ip + ":" + mom_port;
       
        
    }
}
