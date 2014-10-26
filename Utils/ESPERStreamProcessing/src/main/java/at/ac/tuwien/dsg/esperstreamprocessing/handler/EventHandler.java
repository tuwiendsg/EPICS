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
import at.ac.tuwien.dsg.edasich.entity.daf.complexeventprocessing.DataAnalyticFunctionCep;
import at.ac.tuwien.dsg.esperstreamprocessing.entity.EventSubscriber;
import at.ac.tuwien.dsg.esperstreamprocessing.entity.SensorEvent;
import at.ac.tuwien.dsg.esperstreamprocessing.entity.StatementSubscriber;
import at.ac.tuwien.dsg.esperstreamprocessing.rest.EsperRESTWS;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventHandler {

    private StatementSubscriber eventSubscriber;
    private EPServiceProvider epService;
    private DataAnalyticFunctionCep daf;

    public EventHandler() {
    }

    public EventHandler(DataAnalyticFunctionCep daf) {
        this.daf = daf;
    }

    /*
    
     public EventHandler(List<EventPattern> listOfEventPatterns) {
     this.listOfEventPatterns = listOfEventPatterns;
      
     }

     */
    public void initService() {

        System.out.println("Initializing Servcie ..");

        eventSubscriber = new EventSubscriber(daf);

        Configuration config = new Configuration();
        config.addEventTypeAutoName("at.ac.tuwien.dsg.esperstreamprocessing.entity");
        epService = EPServiceProviderManager.getDefaultProvider(config);

        createExpressions();
    }

    private void createExpressions() {
        System.out.println("subcribe events");

        EPStatement epStatement = epService.getEPAdministrator().createEPL(eventSubscriber.getStatement());
        epStatement.setSubscriber(eventSubscriber);

    }

    public void handle(SensorEvent event) {

        Logger.getLogger(EventHandler.class.getName()).log(Level.INFO, event.toString());
        //System.out.println(event.toString());
        epService.getEPRuntime().sendEvent(event);

    }

    public void afterPropertiesSet() {

        Logger.getLogger(EventHandler.class.getName()).log(Level.INFO, "Configuring..");
        initService();
    }

}
