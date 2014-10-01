/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.esperstreamprocessing.handler;

/**
 *
 * @author Jun
 */



import at.ac.tuwien.dsg.edasich.entity.stream.EventPattern;
import at.ac.tuwien.dsg.esperstreamprocessing.entity.EventSubscriber;
import at.ac.tuwien.dsg.esperstreamprocessing.entity.SensorEvent;
import at.ac.tuwien.dsg.esperstreamprocessing.entity.StatementSubscriber;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import java.util.ArrayList;
import java.util.List;

public class SensorEventHandler {
    
    private List<EPStatement> listOfEPSStatements;
    private List<StatementSubscriber> listOfEventSubscribers;
    private List<EventPattern> listOfEventPatterns;
    private EPServiceProvider epService;

    public SensorEventHandler() {
    }

    public SensorEventHandler(List<EventPattern> listOfEventPatterns) {
        this.listOfEventPatterns = listOfEventPatterns;
    }

    public void initService() {

        System.out.println("Initializing Servcie ..");
        
        listOfEventSubscribers = new ArrayList<>();
        listOfEPSStatements = new ArrayList<>();
        
         for (EventPattern eventPattern : listOfEventPatterns) {
           EventSubscriber eventSubscriber = new EventSubscriber(eventPattern);
           listOfEventSubscribers.add(eventSubscriber);
        }
        
        Configuration config = new Configuration();
        config.addEventTypeAutoName("at.ac.tuwien.streamprocessing");
        epService = EPServiceProviderManager.getDefaultProvider(config);

        createExpressions();
    }
    
    private void createExpressions() {
        System.out.println("subcribe events");
        for (StatementSubscriber eventSubscriber : listOfEventSubscribers) {
            EPStatement epStatement = epService.getEPAdministrator().createEPL(eventSubscriber.getStatement());
            epStatement.setSubscriber(eventSubscriber);
            listOfEPSStatements.add(epStatement);
        }
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
