/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.orchestrator.registry;

import at.ac.tuwien.dsg.common.deployment.ElasticService;
import at.ac.tuwien.dsg.common.deployment.ElasticServices;
import at.ac.tuwien.dsg.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.orchestrator.elasticityprocessesstore.ElasticityProcessesStore;

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
  

    public ElasticServiceMonitor(String name) {
        threadName = name;

    }

    public void run() {

        do {

            System.out.println("Update Elasticity Services ...");

            ElasticityProcessesStore eps = new ElasticityProcessesStore();
            List<ElasticService> listOfElasticServices = eps.getElasticServices();

            if (listOfElasticServices != null && listOfElasticServices.size() != 0) {
                
                ElasticServiceRegistry.updateElasticServices(listOfElasticServices);
            } else {
                System.out.println("Elasticity Services Empty");

            }
            try {
                Thread.sleep(10000);

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



}
