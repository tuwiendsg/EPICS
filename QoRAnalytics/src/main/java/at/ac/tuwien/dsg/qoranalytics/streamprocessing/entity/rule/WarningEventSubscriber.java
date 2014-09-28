/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.qoranalytics.streamprocessing.entity.rule;

import at.ac.tuwien.dsg.qoranalytics.streamprocessing.entity.event.SensorEvent;
import java.util.Map;

/**
 *
 * @author Jun
 */
public class WarningEventSubscriber implements StatementSubscriber {
   
    private static final String WARNING_EVENT_THRESHOLD = "400";

    public String getStatement() {
        
        // Example using 'Match Recognise' syntax.
        String warningEventExpression = "select * from SensorEvent "
                + "match_recognize ( "
                + "       measures A as val1, B as val2 "
                + "       pattern (A B) " 
                + "       define " 
                + "               A as A.value > " + WARNING_EVENT_THRESHOLD + ", "
                + "               B as B.value > " + WARNING_EVENT_THRESHOLD + ")";
        
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
