/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.depictool.connector;

import at.ac.tuwien.dsg.common.deployment.ElasticService;
import at.ac.tuwien.dsg.common.deployment.ElasticServices;
import at.ac.tuwien.dsg.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.depictool.util.Configuration;
import java.util.List;
import java.util.logging.Level;
import javax.xml.bind.JAXBException;




/**
 *
 * @author Jun
 */
public class ElasticServiceMonitor implements Runnable {

    private Thread t;
    private String threadName;
    private ComotConnector comotConnector;

    public ElasticServiceMonitor(String name, ComotConnector comotConnector) {
        threadName = name;
        this.comotConnector = comotConnector;
    }

    public void run() {

        do {

            List<ElasticService> listOfElasticServices = comotConnector.getCloudServiceInfo();
            ElasticServices elasticServices = new ElasticServices(listOfElasticServices);
            updateElasticServices(elasticServices);

            try {
                Thread.sleep(5000);

            } catch (InterruptedException ex) {

            }
        } while (true);
    }

    public void start() {
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }

    private void updateElasticServices(ElasticServices elasticServices) {
        String eSXML = "";
        try {
            eSXML = JAXBUtils.marshal(elasticServices, ElasticServices.class);
        } catch (JAXBException ex) {
            java.util.logging.Logger.getLogger(ElasticServiceMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        Configuration configuration = new Configuration();
        String ip = configuration.getConfig("ORCHESTRATOR.IP");
        String port = configuration.getConfig("ORCHESTRATOR.PORT");
        String resource = configuration.getConfig("ORCHESTRATOR.ELASTIC.SERVICE.RESOURCE");

        RestfulWSClient ws = new RestfulWSClient(ip, port, resource);
        ws.callPutMethod(eSXML);

    }

}
