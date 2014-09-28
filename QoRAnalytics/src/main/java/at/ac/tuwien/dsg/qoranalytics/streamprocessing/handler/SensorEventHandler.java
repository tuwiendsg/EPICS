/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.qoranalytics.streamprocessing.handler;

/**
 *
 * @author Jun
 */

import at.ac.tuwien.dsg.qoranalytics.streamprocessing.entity.event.SensorEvent;
import at.ac.tuwien.dsg.qoranalytics.streamprocessing.entity.rule.MonitorEventSubscriber;
import at.ac.tuwien.dsg.qoranalytics.streamprocessing.entity.rule.StatementSubscriber;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

public class SensorEventHandler {
    
    private EPServiceProvider epService;
    private EPStatement monitorEventStatement;
    private StatementSubscriber monitorEventSubscriber;

    
    public SensorEventHandler() {

        monitorEventSubscriber = new MonitorEventSubscriber();
        
    }

    
    public void initService() {

        System.out.println("Initializing Servcie ..");
        Configuration config = new Configuration();
        config.addEventTypeAutoName("at.ac.tuwien.dsg.qoranalytics.streamprocessing.entity.event");
        epService = EPServiceProviderManager.getDefaultProvider(config);

        createTemperatureMonitorExpression();

    }


    private void createTemperatureMonitorExpression() {

        System.out.println("create Timed Average Monitor");
        monitorEventStatement = epService.getEPAdministrator().createEPL(monitorEventSubscriber.getStatement());
        monitorEventStatement.setSubscriber(monitorEventSubscriber);
    }


    public void handle(SensorEvent event) {

        System.out.println(event.toString());
        epService.getEPRuntime().sendEvent(event);

    }

   
    public void afterPropertiesSet() {
        
        System.out.println("Configuring..");
        initService();
    }
    
}
