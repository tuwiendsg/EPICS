/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.depictool.connector;

import at.ac.tuwien.dsg.depic.common.deployment.ElasticService;
import at.ac.tuwien.dsg.depic.common.deployment.ElasticServices;
import at.ac.tuwien.dsg.depic.common.utils.IOUtils;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.depic.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.comot.client.DefaultSalsaClient;
import at.ac.tuwien.dsg.comot.orchestrator.interraction.salsa.SalsaInterraction;
import at.ac.tuwien.dsg.depic.depictool.elstore.ElasticityProcessStore;
import at.ac.tuwien.dsg.depic.depictool.util.Configuration;
import java.util.ArrayList;
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
            Configuration cfg = new Configuration();
            DefaultSalsaClient defaultSalsaClient = new DefaultSalsaClient();
            defaultSalsaClient.getConfiguration().setHost(cfg.getConfig("SALSA.IP"));
            defaultSalsaClient.getConfiguration().setPort(Integer.parseInt(cfg.getConfig("SALSA.PORT")));
            SalsaInterraction salsaInterraction = new SalsaInterraction().withDefaultSalsaClient(defaultSalsaClient);

            String cloudServiceID = comotConnector.getCloudServiceID();

            salsaInterraction.waitUntilRunning(cloudServiceID);

            List<ElasticService> listOfElasticServices = comotConnector.getCloudServiceInfo();
            // ElasticServices elasticServices = new ElasticServices(listOfElasticServices);
            if (listOfElasticServices != null) {

                System.out.println("Check Elastic Services ... ");

                if (listOfElasticServices.size()!= 0) {

                    configureElasticityServices(listOfElasticServices);
                    
//                    ElasticityProcessStore elStore = new ElasticityProcessStore();
//                    elStore.storeElasticServices(listOfElasticServices);
                    saveElasticityServicesInfo(listOfElasticServices);
                    
                }
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

//    private void startGettingElasticServicesOrchestrator() {
////        String eSXML = "";
////        try {
////            eSXML = JAXBUtils.marshal(elasticServices, ElasticServices.class);
////        } catch (JAXBException ex) {
////            java.util.logging.Logger.getLogger(ElasticServiceMonitor.class.getName()).log(Level.SEVERE, null, ex);
////        }
//        
//        
//        
//        
//        
//        Configuration configuration = new Configuration();
//        String ip = configuration.getConfig("ORCHESTRATOR.IP");
//        String port = configuration.getConfig("ORCHESTRATOR.PORT");
//        String resource = configuration.getConfig("ORCHESTRATOR.ELASTIC.SERVICE.RESOURCE");
//
//        RestfulWSClient ws = new RestfulWSClient(ip, port, resource);
//        ws.callPutMethod("");
//        
//        
//        
//        
//
//    }
    
    private void saveElasticityServicesInfo(List<ElasticService> listOfElasticServices){
        try {
           
            List<ElasticService> updateList = new ArrayList<ElasticService>();
            
            for (ElasticService es : listOfElasticServices){
                
                if (es.getRequest()!=-1){
                    updateList.add(es);
                }
            }
            
            
            ElasticServices elasticServices = new ElasticServices(updateList);
            String xml = JAXBUtils.marshal(elasticServices, ElasticServices.class);
            
            
            IOUtils iou = new IOUtils("/home/ubuntu/log");
            iou.overWriteData(xml, "depic_elasticservice.xml");
            
            System.out.println("\n" + xml);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ElasticServiceMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
        
        
        
    private void configureElasticityServices(List<ElasticService> listOfElasticServices) {

        System.out.println("CONFIGURE_EDAAS: " + threadName);

        for (ElasticService elasticService : listOfElasticServices) {

            System.out.println("CONFIGURE SERVICE: " + elasticService.getActionID());
            Configuration cfg = new Configuration();
            String daLoaderIp = cfg.getConfig("DATA.ASSET.LOADER.IP.LOCAL");
            String orchestratorIp = cfg.getConfig("ORCHESTRATOR.IP.LOCAL");

            
            if (elasticService.getActionID().equals(threadName)) {
                String configureDataAssetLoaderUri = elasticService.getUri() + "/eDaaS/rest/dataasset/dataassetloaderip";
                String configureOrchestratorrUri = elasticService.getUri() + "/eDaaS/rest/dataasset/orchestratorip";
                System.out.println("uri: -" + configureDataAssetLoaderUri + "-");
                System.out.println("uri: -" + configureOrchestratorrUri + "-");

                RestfulWSClient ws1 = new RestfulWSClient(configureDataAssetLoaderUri);
                ws1.callPutMethod(daLoaderIp);

                RestfulWSClient ws2 = new RestfulWSClient(configureOrchestratorrUri);
                ws2.callPutMethod(orchestratorIp);

            } else {

                String configureUri = elasticService.getUri() + "/conf";

                RestfulWSClient ws = new RestfulWSClient(configureUri);
                int responseCode =  ws.callPutMethodRC(daLoaderIp);
                
                if (responseCode!=204){
                    elasticService.setRequest(-1);
                }

            }

        }

    }
}
