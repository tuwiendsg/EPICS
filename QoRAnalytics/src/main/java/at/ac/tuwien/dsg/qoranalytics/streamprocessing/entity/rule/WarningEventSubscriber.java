/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.qoranalytics.streamprocessing.entity.rule;

import at.ac.tuwien.dsg.qoranalytics.configuration.StatementConfig;
import at.ac.tuwien.dsg.qoranalytics.streamprocessing.entity.event.SensorEvent;
import java.util.Map;

/**
 *
 * @author Jun
 */
public class WarningEventSubscriber implements StatementSubscriber {
   
    public String getStatement() {
        
        // Example using 'Match Recognise' syntax.
        String warningEventExpression = StatementConfig.getStatemement("WARNING");
        
        return warningEventExpression;
    }
    
    public void update(Map<String, SensorEvent> eventMap) {

        SensorEvent val1 = (SensorEvent) eventMap.get("val1");
        SensorEvent val2 = (SensorEvent) eventMap.get("val2");

        StringBuilder sb = new StringBuilder();
        sb.append("--------------------------------------------------");
        sb.append("\n- [WARNING] : OVER THRESHOLD DETECTED = " + val1 + "," + val2);
        sb.append("\n--------------------------------------------------");

        System.out.println(sb.toString());
    }
}
