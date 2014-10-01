package at.ac.tuwien.dsg.edasich.batchjobprocessing.daw.test;

import at.ac.tuwien.dsg.edasich.batchjobprocessing.analytic.LeastSquareRegression;

import at.ac.tuwien.dsg.edasich.configuration.Configuration;
import at.ac.tuwien.dsg.edasich.connector.MOMConnector;
import at.ac.tuwien.dsg.edasich.batchjobprocessing.daw.engine.WorkflowEngine;
import at.ac.tuwien.dsg.edasich.configuration.EventPatternLoader;
import at.ac.tuwien.dsg.edasich.streamprocessing.entity.event.EventMessage;
import at.ac.tuwien.dsg.smartcom.model.Identifier;
import at.ac.tuwien.dsg.smartcom.model.Message;

public class demo {

    public static final void main(String[] args) {
        /*
        WorkflowEngine wfEngine = new WorkflowEngine("daw1");
        wfEngine.startWFEngine();
        */
        
        
        /*
        MOMConnector connector = new MOMConnector();
        connector.openConnection();
       */
        
            String statement="select avg(value) as avg_val from SensorEvent.win:time_batch(5 sec) (A (B | C)) where name='Sensor12'";
            String store="config/monitor-event-rule.xml";
        
            String testId = ""; //unique identifier of message
            String testcontent = "Methane concentration is over admisible.";  //unique identifier of message content
            String testType = "control message";      //the type of message
            String testSubType = "";          //component specific
            String sender = "QoRAnalytic";               //unique identifier of sender
            String receiver = "operator";           //unique identifier of receiver
            //String conversationId="conversationId"; //OPTIONAL  //If any two process run parallaly then they communicate with each other using this id
            long ttl = 3;                             //OPTIONAL  //the time duration to monitor the message
            String testLanguage = "English";          //OPTIONAL //the language of the message
            String securityToken = "SecurityToken";   //OPTIONAL //information about the authenticity of the message
            
           // EventMessage message = new EventMessage(testId, testcontent, testType, testSubType, sender, receiver, "", ttl, testLanguage, securityToken);
            
            
            
            EventPatternLoader.saveConfig(statement, store, null);
// connector.sendCriticalMessage();
        
        /*
        Sampling s = new Sampling();
        s.start(10);
        */
        
        
        /*
        LeastSquareRegression lsr = new LeastSquareRegression();
        lsr.start(5, 2);
        */
        
    }

}
