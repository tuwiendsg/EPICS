package at.ac.tuwien.dsg.depic.demo;


import at.ac.tuwien.dsg.depic.dataelasticitycapabilities.CategoricalElasticityCapability;
import at.ac.tuwien.dsg.depic.dataelasticitycapabilities.Cost;
import at.ac.tuwien.dsg.depic.dataelasticitycapabilities.DataElasticityConfiguration;
import at.ac.tuwien.dsg.depic.dataelasticitycapabilities.DataSource;
import at.ac.tuwien.dsg.depic.dataelasticitycapabilities.NumericalElasticityCapability;
import at.ac.tuwien.dsg.depic.dataelasticitycapabilities.NumericalElasticityValue;
import at.ac.tuwien.dsg.depic.dataelasticityrequirement.DataElasticityRequirement;
import at.ac.tuwien.dsg.depic.DataFunctionality;
import at.ac.tuwien.dsg.depic.ElasticDataObject;
import at.ac.tuwien.dsg.depic.utility.Utilities;
import at.ac.tuwien.dsg.sybl.elasticstate.ElasticState;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        NumericalElasticityCapability dataAccuracy = new NumericalElasticityCapability()
                .withDataElasticityConfiguration(new DataElasticityConfiguration()
                                                        .withCostURL( "http://128.130.172.216/elfinder/files/download/dataAccuracyPrice.xml" )
                                                        .withCurrentValueURL("http://localhost:8080/DataGovernance/webresources/DataAccuracyValue")
                                                        .withMonitoringInterval(60))
                .lessThan(85)
                .greaterThan(15)
                .withPriorityOrder(1);
        
        
        CategoricalElasticityCapability dataProcessingSpeedLevel = new CategoricalElasticityCapability()
                .withDataElasticityConfiguration(new DataElasticityConfiguration()
                                                        .withCostURL("http://128.130.172.216/elfinder/files/download/dataProcessingPrice.xml")
                                                        .withCurrentValueURL("http://localhost:8080/DataGovernance/webresources/DataAccuracyValue")
                                                        .withMonitoringInterval(60))
                .lessThanLevel(3)
                .greaterThanLevel(2)
                .withPriorityOrder(2);
            
        DataSource dataSource = new DataSource()
                .withCostURL("http://128.130.172.216/elfinder/files/download/dataSourcePrice.xml");
    //    dataSource.addDataSource("shop1");
        dataSource.addDataSource("shop2");
        dataSource.addDataSource("shop3");
  //      dataSource.addDataSource("shop4");
        dataSource.addDataSource("shop5");
     
        Cost cost = new Cost()
                .lessThan(7)
                .withPriorityOrder(3);
                
        
       
        DataElasticityRequirement dataElReq = new DataElasticityRequirement();
        dataElReq.addRequirement(dataAccuracy);
        dataElReq.addRequirement(dataProcessingSpeedLevel);
        dataElReq.addRequirement(dataSource);
        dataElReq.addRequirement(cost);
        
        
        DataFunctionality dataFunc = new DataFunctionality()
                .withDataFunctionalityURL("http://localhost:8080/DataGovernance/webresources/bargraph");
        
        ElasticDataObject eDO = new ElasticDataObject()
                .withDataElasticityRequirement(dataElReq)
                .withDataFunctionality(dataFunc);
        
        //submit to SYBL
        //eDO.submitElasticityRequirement();
        
        //get eState from MELA
        //ElasticState eState = eDO.getElasticStateFromMELA();
        //System.out.println("APP - eState: " + eState.getStatus());
        
        
        //startService
        eDO.startService();
        
        
                
        /*
        
        
         DataElasticityRequirement dataElReq = new DataElasticityRequirement()
                .withDataAccuracyLevel(new DataAccuracyLevel()
                                            .lessThanLevel(3)
                                            .greaterThanLevel(1)
                        
                                            .withPriorityOrder(1))
                
                .withProcessingSpeedLevel(new DataProcessingSpeed()
                                            .lessThanLevel(3)
                                            .greaterThanLevel(2)
                        
                                            .withPriorityOrder(3))
                .withDataSourceShop1(new DataSourceShop1()
                                            .setValue(true))
                     
                .withBudget(new Budget()
                                            .lessThan(7)
                                            .withPriorityOrder(2));
        */
    }
}
