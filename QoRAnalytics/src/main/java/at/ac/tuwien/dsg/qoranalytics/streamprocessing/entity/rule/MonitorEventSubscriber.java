/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.qoranalytics.streamprocessing.entity.rule;

import at.ac.tuwien.dsg.qoranalytics.configuration.StatementConfig;
import java.util.Map;

/**
 *
 * @author Jun
 */
public class MonitorEventSubscriber implements StatementSubscriber {
    public String getStatement() {

        return StatementConfig.getStatemement("MONITOR");
    }

    public void update(Map<String, Double> eventMap) {

        Double avg = (Double) eventMap.get("avg_val");

        StringBuilder sb = new StringBuilder();
        sb.append("---------------------------------");
        sb.append("\n- [MONITOR] Average Value = " + avg);
        sb.append("\n---------------------------------");

        System.out.println(sb.toString());
        
    }
}
