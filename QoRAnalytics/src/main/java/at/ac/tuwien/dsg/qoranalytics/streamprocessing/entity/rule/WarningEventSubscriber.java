/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.qoranalytics.streamprocessing.entity.rule;

import at.ac.tuwien.dsg.qoranalytics.configuration.EventPatternLoader;
import at.ac.tuwien.dsg.qoranalytics.connector.MOMConnector;
import at.ac.tuwien.dsg.qoranalytics.connector.SmartComConnector;
import at.ac.tuwien.dsg.qoranalytics.streamprocessing.entity.event.EventMessage;
import at.ac.tuwien.dsg.qoranalytics.streamprocessing.entity.event.SensorEvent;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jun
 */
public class WarningEventSubscriber implements StatementSubscriber {
   
    private String EventSubscriberType = "WARNING";
    
    public String getStatement() {
        
        // Example using 'Match Recognise' syntax.
        String warningEventExpression = EventPatternLoader.getStatemement(EventSubscriberType);
        
        return warningEventExpression;
    }
    
    public void update(Map<String, SensorEvent> eventMap) {

        /*
        SensorEvent val1 = (SensorEvent) eventMap.get("val1");
        SensorEvent val2 = (SensorEvent) eventMap.get("val2");
*/
        
        sendCriticalMessage();
        
        StringBuilder sb = new StringBuilder();
        sb.append("--------------------------------------------------");
        sb.append("\n- [WARNING] : THRESHOLD DETECTED ");
        sb.append("\n--------------------------------------------------");

        System.out.println(sb.toString());
    }
    
    
    public void sendCriticalMessage(){
        EventMessage eventMessage = EventPatternLoader.getEventMessage(EventSubscriberType);
        
        if (eventMessage!=null) {
        
        SmartComConnector scc = new SmartComConnector();
        at.ac.tuwien.dsg.smartcom.model.Message message = scc.buildMessage(eventMessage);
        
        try {
            scc.sendMessage(message);
        } catch (Exception ex) {
            Logger.getLogger(MOMConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }
    }
}
