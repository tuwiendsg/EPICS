/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.connector;

import at.ac.tuwien.dsg.depic.common.entity.runtime.ElasticService;
import at.ac.tuwien.dsg.depic.common.entity.runtime.ElasticServices;
import at.ac.tuwien.dsg.depic.common.utils.IOUtils;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.depic.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.comot.client.DefaultSalsaClient;
import at.ac.tuwien.dsg.comot.orchestrator.interraction.salsa.SalsaInterraction;
import at.ac.tuwien.dsg.depic.common.utils.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;


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

                System.err.println("Check Elastic Services ... ");

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
                System.err.println(ex);
            }
        } while (true);
    }

    public void start() {
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }

    private void saveElasticityServicesInfo(List<ElasticService> listOfElasticServices){
        try {
           
            List<ElasticService> updateList = new ArrayList<ElasticService>();
            
            for (ElasticService es : listOfElasticServices){
                
                if (es.getRequest()!=-1){
                    updateList.add(es);
                    System.err.println("PASS CASE: " + es.getUri());
                } else {
                    System.err.println("FAILED CASE: " + es.getUri());
                }
            }
            
            
            ElasticServices elasticServices = new ElasticServices(updateList);
            String xml = JAXBUtils.marshal(elasticServices, ElasticServices.class);
            Configuration cfg = new Configuration();
            String elasticServiceConfigPath = cfg.getConfig("DB.ELASTIC.SERVICES.UPDATE");
            
            IOUtils iou = new IOUtils(elasticServiceConfigPath);
            iou.overWriteData(xml, "depic_elasticservice.xml");
            
            System.err.println("\n" + xml);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ElasticServiceMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
        
        
        
    private void configureElasticityServices(List<ElasticService> listOfElasticServices) {

        System.err.println("CONFIGURE_EDAAS: " + threadName);

        for (ElasticService elasticService : listOfElasticServices) {

            System.err.println("CONFIGURE SERVICE: " + elasticService.getActionID());
            Configuration cfg = new Configuration();
            String daLoaderIp = cfg.getConfig("DATA.ASSET.LOADER.IP.LOCAL");
            String orchestratorIp = cfg.getConfig("ORCHESTRATOR.IP.LOCAL");
            
            
            System.err.println("DATA LOADER IP : " + daLoaderIp);
            System.err.println("ORCHESTRATOR IP : " + orchestratorIp);
            
            if (elasticService.getActionID().equals(threadName)) {
                String configureDataAssetLoaderUri = elasticService.getUri() + "/eDaaS/rest/dataasset/dataassetloaderip";
                String configureOrchestratorrUri = elasticService.getUri() + "/eDaaS/rest/dataasset/orchestratorip";
                System.err.println("uri: -" + configureDataAssetLoaderUri + "-");
                System.err.println("uri: -" + configureOrchestratorrUri + "-");

                RestfulWSClient ws1 = new RestfulWSClient(configureDataAssetLoaderUri);
                ws1.callPutMethod(daLoaderIp);

                RestfulWSClient ws2 = new RestfulWSClient(configureOrchestratorrUri);
                ws2.callPutMethod(orchestratorIp);

            } else {

                String configureUri = elasticService.getUri() + "/conf";
                
                
                
                RestfulWSClient ws = new RestfulWSClient(configureUri);
                int responseCode =  ws.callPutMethodRC(daLoaderIp);
                
                System.err.println("CONFIGURE URI: " + configureUri);
                System.err.println("RESPONSE CODE: " + responseCode);
                
                
                if (responseCode!=204){
                    elasticService.setRequest(-1);
                }

            }

        }

    }
}
