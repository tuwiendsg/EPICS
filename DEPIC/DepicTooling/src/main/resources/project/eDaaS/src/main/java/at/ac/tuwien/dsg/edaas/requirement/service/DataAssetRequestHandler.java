/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.edaas.requirement.service;

import at.ac.tuwien.dsg.common.entity.eda.ElasticDataAsset;
import at.ac.tuwien.dsg.common.entity.eda.ElasticState;
import at.ac.tuwien.dsg.common.entity.eda.MetricCondition;
import at.ac.tuwien.dsg.common.entity.eda.da.DataAsset;
import at.ac.tuwien.dsg.common.entity.eda.ep.MonitoringSession;
import at.ac.tuwien.dsg.common.utils.IOUtils;
import at.ac.tuwien.dsg.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.edaas.config.Configuration;
import at.ac.tuwien.dsg.edaas.requirement.ConsumerRequirement;
import at.ac.tuwien.dsg.edaas.requirement.DataAssetRequest;
import at.ac.tuwien.dsg.edaas.requirement.TemplateConstraint;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */
public class DataAssetRequestHandler {
    
    
    
    public DataAsset processDataAssetRequest(DataAssetRequest dataAssetRequest){
        
        requestToGetDataAsset(dataAssetRequest.getDataAssetID());
       
        List<String> expectedElasticStates = findExpectedElasticState(dataAssetRequest);
        MonitoringSession monitoringSession = new MonitoringSession(dataAssetRequest.getDataAssetID(), expectedElasticStates);
        
        startMonitoringServices(monitoringSession);
        
        DataAsset da = getDataAsset(dataAssetRequest.getDataAssetID());
        
        return da;
    }
    
    
    public void requestToGetDataAsset(String dataAssetID){
        
        Configuration configuration  = new Configuration();
        String ip = configuration.getConfig("DAF.MANAGEMENT.IP");
        String port = configuration.getConfig("DAF.MANAGEMENT.PORT");
        String resource = configuration.getConfig("DAF.MANAGEMENT.RESOURCE");
      
        IOUtils iou = new IOUtils();
        String dafXML = iou.readData(dataAssetID);
        
        RestfulWSClient ws = new RestfulWSClient(ip, port, resource);
        ws.callPutMethod(dafXML);
   
    }
    
    public List<String> findExpectedElasticState(DataAssetRequest dataAssetRequest){
        
        
        ConsumerRequirement consumerRequirement = dataAssetRequest.getConsumerRequirement();    
        ConstraintConverter constraintConverter = new ConstraintConverter();
        List<TemplateConstraint> listOfConstraints = constraintConverter.getListOfConstraints(consumerRequirement);
        
        ElasticityProcessesStore elasticityProcessesStore = new ElasticityProcessesStore();
        ElasticDataAsset eda = elasticityProcessesStore.getEDAModel(dataAssetRequest.getDataAssetID());
        
        List<String> expectedElasticStateIDs = new ArrayList<String>();
        
        List<ElasticState> listOfElasticStates = eda.getListOfElasticStates();
        
        for (ElasticState elasticState : listOfElasticStates) {
            
            if (isExpectedEState(listOfConstraints, elasticState)){
                expectedElasticStateIDs.add(elasticState.geteStateID());
            }
           
        }
        
       
        return expectedElasticStateIDs;
        
        
    }
    
    
    public void startMonitoringServices(MonitoringSession monitoringSession){
        
        String mSessionXML="";
        try {
            mSessionXML = JAXBUtils.marshal(monitoringSession, MonitoringSession.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DataAssetRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Configuration configuration  = new Configuration();
        String ip = configuration.getConfig("DEPIC.MONITORING.IP");
        String port = configuration.getConfig("DEPIC.MONITORING.PORT");
        String resource = configuration.getConfig("DEPIC.MONITORING.RESOURCE");
        
        RestfulWSClient ws = new RestfulWSClient(ip, port, resource);
        ws.callPutMethod(mSessionXML);
    }
    
    public DataAsset getDataAsset(String dataAssetID){
         Configuration configuration  = new Configuration();
        String ip = configuration.getConfig("DATA.ASSET.LOADER.IP");
        String port = configuration.getConfig("DATA.ASSET.LOADER.PORT");
        String resource = configuration.getConfig("DATA.ASSET.LOADER.RESOURCE");
        
        RestfulWSClient ws = new RestfulWSClient(ip, port, resource);
        
        String rs = ws.callPutMethod(dataAssetID);
        
        DataAsset da=null;
        try {
            da = JAXBUtils.unmarshal(rs, DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DataAssetRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return da;
    }
    
    
    private boolean isExpectedEState(List<TemplateConstraint> listOfConstraints, ElasticState elasticState){
        
        boolean rs = true;
        List<MetricCondition> listOfMetricConditions = elasticState.getListOfConditions();
        
        for (MetricCondition metricCondition : listOfMetricConditions) {
            
            TemplateConstraint constraint = findMatchConstraint(listOfConstraints, metricCondition.getMetricName());
            
            if(!(constraint.getMinValue()>=metricCondition.getLowerBound()) || !(constraint.getMaxValue()<=metricCondition.getUpperBound())){
                rs = false;
                break;
            }
            
            
        }
        
        
        return rs;
    }
    
    private TemplateConstraint findMatchConstraint(List<TemplateConstraint> listOfConstraints,String metricName){
        
        TemplateConstraint templateConstraint = null;
        for (TemplateConstraint constraint : listOfConstraints) {
            if (constraint.getConstraintName().equals(metricName)){
                templateConstraint = constraint;
            }
        }
        return templateConstraint;
    }
    
    
}
