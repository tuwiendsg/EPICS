/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.orchestrator.registry;

import at.ac.tuwien.dsg.depic.common.entity.runtime.ElasticService;
import at.ac.tuwien.dsg.depic.common.entity.runtime.ElasticServices;
import at.ac.tuwien.dsg.depic.common.utils.IOUtils;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.orchestrator.configuration.Configuration;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

          //  ElasticityProcessesStore eps = new ElasticityProcessesStore();
            List<ElasticService> listOfElasticServices = getUpdatedElasticService();

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
    
    private List<ElasticService>  getUpdatedElasticService(){
        
        List<ElasticService> listOfElasticServices = null;
        Configuration cfg = new Configuration();
        
        String elasticServiceUpdatePath = cfg.getConfig("DB.ELASTIC.SERVICES.UPDATE");
        
        
        IOUtils iou = new IOUtils(elasticServiceUpdatePath);
        
        String xml = iou.readData("depic_elasticservice.xml");
        
        if (xml!=null && !xml.equals("")) {
        
        try {
            ElasticServices elasticServices = JAXBUtils.unmarshal(xml, ElasticServices.class);
            listOfElasticServices = elasticServices.getListOfElasticServices();
            
        } catch (JAXBException ex) {
            Logger.getLogger(ElasticServiceMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        
        return listOfElasticServices;
    }



}
