/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.qoranalytics.daw.demo;

/**
 *
 * @author Jun
 */
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class WorkflowManager {

    DefaultCamelContext camelContext;

    public WorkflowManager() {
        camelContext = new DefaultCamelContext();
        RouteBuilder routeBuilder = new RouteBuilder() {

            @Override
            public void configure() throws Exception {
                from("timer:schedule?period=1s&daemon=true").process(new ProcessOne()).process(new ProcessTwo());
            }
        };
        try {
            camelContext.addRoutes(routeBuilder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() throws Exception {
        camelContext.start();
    }

    public void stop() throws Exception {
        camelContext.stop();
    }

    public static void main(String[] args) {
        WorkflowManager workflowManager = new WorkflowManager();
        try {
            workflowManager.start();
            while(true) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
