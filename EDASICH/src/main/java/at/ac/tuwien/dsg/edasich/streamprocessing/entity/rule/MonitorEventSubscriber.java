/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.edasich.streamprocessing.entity.rule;

import at.ac.tuwien.dsg.edasich.configuration.EventPatternLoader;
import at.ac.tuwien.dsg.edasich.connector.MOMConnector;
import at.ac.tuwien.dsg.edasich.connector.SmartComConnector;
import at.ac.tuwien.dsg.edasich.streamprocessing.entity.event.EventMessage;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jun
 */
public class MonitorEventSubscriber implements StatementSubscriber {

    private String EventSubscriberType = "MONITOR";

    public String getStatement() {

        return EventPatternLoader.getStatemement(EventSubscriberType);
    }

    public void update(Map<String, Double> eventMap) {

        Double avg = (Double) eventMap.get("avg_val");

        StringBuilder sb = new StringBuilder();
        sb.append("---------------------------------");
        sb.append("\n- [MONITOR] Average Value = " + avg);
        sb.append("\n---------------------------------");

        System.out.println(sb.toString());

    }

    public void sendCriticalMessage() {
        EventMessage eventMessage = EventPatternLoader.getEventMessage(EventSubscriberType);

        if (eventMessage != null) {

            SmartComConnector scc = new SmartComConnector();
            at.ac.tuwien.dsg.smartcom.model.Message message = scc.buildMessage(eventMessage);

            try {
                scc.sendMessage(message);
            } catch (Exception ex) {
                Logger.getLogger(MOMConnector.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            System.out.println("No message !");
        }
    }
}
