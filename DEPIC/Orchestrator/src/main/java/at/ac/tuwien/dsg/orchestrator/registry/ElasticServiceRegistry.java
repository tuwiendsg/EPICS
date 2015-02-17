/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.orchestrator.registry;

import at.ac.tuwien.dsg.common.deployment.DeployAction;
import at.ac.tuwien.dsg.common.deployment.ElasticService;
import at.ac.tuwien.dsg.common.entity.process.ActionDependency;
import at.ac.tuwien.dsg.common.entity.process.MetricElasticityProcess;
import at.ac.tuwien.dsg.common.entity.process.MetricProcess;
import at.ac.tuwien.dsg.common.utils.MySqlConnectionManager;
import at.ac.tuwien.dsg.orchestrator.configuration.Configuration;
import at.ac.tuwien.dsg.orchestrator.elasticityprocessesstore.ElasticityProcessesStore;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Jun
 */
public class ElasticServiceRegistry {
    
    List<ElasticService> listOfElasticServices;
    MetricProcess metricProcess;
    String eDaaSName;
    MySqlConnectionManager connectionManager;
    
    public ElasticServiceRegistry(String eDaaSName) {
        this.eDaaSName=eDaaSName;
        ElasticityProcessesStore eps = new ElasticityProcessesStore();
        listOfElasticServices = eps.getElasticServices();
        metricProcess = eps.getMetricProcess(eDaaSName);
        
    }

    public String getElasticServiceURI(String serviceID){
        
        List<String> listOfServices = new ArrayList<String>();
        
        for (ElasticService elasticService : listOfElasticServices){
            
            if (elasticService.getActionID().equals(serviceID)){
                String uri = elasticService.getUri();
                listOfServices.add(uri);
            }
        }
        
        int randomIndex = randomInt(0, listOfServices.size()-1);
        return  listOfServices.get(randomIndex);
        
    }
    
    private int randomInt(int min, int max){
        
        Random random = new Random();
        
        int randomNumber = random.nextInt(max+1 - min) + min;
        return randomNumber;
    }
    
    

    
  
    
    
    public String getMonitoringMetricName(String actionID){
        String metricName="";
      
        List<MetricElasticityProcess> metricElasticityProcesses = metricProcess.getListOfMetricElasticityProcesses();
        for (MetricElasticityProcess mp : metricElasticityProcesses) {
            if (mp.getMonitorAction().getMonitorActionID().equals(actionID)) {
                metricName=mp.getMetricName();
                break;
            }
        }


        return metricName;
    }
    
}
