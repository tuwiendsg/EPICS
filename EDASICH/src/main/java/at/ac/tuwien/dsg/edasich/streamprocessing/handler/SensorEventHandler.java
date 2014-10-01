/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.edasich.streamprocessing.handler;

/**
 *
 * @author Jun
 */

import at.ac.tuwien.dsg.edasich.streamprocessing.entity.event.SensorEvent;
import at.ac.tuwien.dsg.edasich.streamprocessing.entity.rule.MonitorEventSubscriber;
import at.ac.tuwien.dsg.edasich.streamprocessing.entity.rule.StatementSubscriber;
import at.ac.tuwien.dsg.edasich.streamprocessing.entity.rule.WarningEventSubscriber;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

public class SensorEventHandler {
    
    private EPServiceProvider epService;
    private EPStatement monitorEventStatement;
    private EPStatement warningEventStatement;
    private StatementSubscriber monitorEventSubscriber;
    private StatementSubscriber warningEventSubscriber;

    
    public SensorEventHandler() {

        monitorEventSubscriber = new MonitorEventSubscriber();
        warningEventSubscriber = new WarningEventSubscriber();
        
    }

    
    public void initService() {

        System.out.println("Initializing Servcie ..");
        Configuration config = new Configuration();
        config.addEventTypeAutoName("at.ac.tuwien.dsg.qoranalytics.streamprocessing.entity.event");
        epService = EPServiceProviderManager.getDefaultProvider(config);

        createMonitorExpression();
        createWarningExpression();
    }


    private void createMonitorExpression() {

        System.out.println("create Average Value Monitor");
        monitorEventStatement = epService.getEPAdministrator().createEPL(monitorEventSubscriber.getStatement());
        monitorEventStatement.setSubscriber(monitorEventSubscriber);
    }
    
    private void createWarningExpression() {
        System.out.println("create Threshold Value Warning");
        warningEventStatement = epService.getEPAdministrator().createEPL(warningEventSubscriber.getStatement());
        warningEventStatement.setSubscriber(warningEventSubscriber);
        
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
