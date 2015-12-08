DEPIC - Data Elasticity Programming in the Cloud
=====
Contact: truong@dsg.tuwien.ac.at

### Application as examples:
 - [GPSAnalytics](https://github.com/tuwiendsg/EPICS/tree/master/depic/examples/applications/GPSAnalytics)
 - DataPlay
 
### Main flow of DEPIC:

#### Step 1: Generate the elastic processes and the eDaaS

1. DaaS provider submits [DAF, QoR and PrimitiveActions](https://github.com/tuwiendsg/EPICS/tree/master/depic/examples/applications/GPSAnalytics/experiment1/case1/inputs). Based on that, the [Generator](https://github.com/tuwiendsg/EPICS/blob/master/depic/depic-tooling/src/main/java/at/ac/tuwien/dsg/depic/depictool/generator/Generator.java) executes the generation algorithm. 

2. The [ElasticProcessGenerator](https://github.com/tuwiendsg/EPICS/blob/master/depic/depic-elastic-process-generator/src/main/java/at/ac/tuwien/dsg/depic/elastic/process/generator/ElasticProcessesGenerator.java#L83-L104) 
generates a [DataElasticityManagementProcess](https://github.com/tuwiendsg/EPICS/blob/master/depic/depic-common/src/main/java/at/ac/tuwien/dsg/depic/common/entity/eda/elasticprocess/DataElasticityManagementProcess.java).
An example of process is in the [output folder](https://github.com/tuwiendsg/EPICS/tree/master/depic/examples/applications/GPSAnalytics/experiment1/case1/output)

3. The [DaaSGenerator](https://github.com/tuwiendsg/EPICS/blob/master/depic/depic-tooling/src/main/java/at/ac/tuwien/dsg/depic/depictool/generator/DaaSGenerator.java) generates the eDaaS based on the [eDaaS Template](https://github.com/tuwiendsg/EPICS/tree/master/depic/depic-tooling/src/main/resources)

4. The [generated eDaaS artifact](http://128.130.172.214:8080/DepicTooling/edaasproject/edaas1.zip) is deployed manually. The primitive actions, control processes and monitoring processes are deployed via the [COMOT connector](https://github.com/tuwiendsg/EPICS/blob/master/depic/depic-tooling/src/main/java/at/ac/tuwien/dsg/depic/depictool/connector/ComotConnector.java)

#### Step 2: Provide elastic data asset

1. The eDaaS is now running. When receiving a request from a customer, the eDaaS calls the "DataAssetRequestHandler.java" (see in project.zip). It activate the [DataElasticityMonitor](https://github.com/tuwiendsg/EPICS/blob/aa2521dfc706861752b11cf48ee3563e63452a9b/depic/orchestrator/src/main/java/at/ac/tuwien/dsg/orchestrator/dataelasticitycontroller/DataElasticityMonitor.java)

  According to the [Elastic Process](https://github.com/tuwiendsg/EPICS/blob/master/depic/examples/applications/GPSAnalytics/experiment1/case1/output/elastic_process.yml#L192-L210), DEPIC will check the list of the data column in the *listOfParameters*. For example, the parameters belows are *longtitudeIndex, latitude and speedIndex*:
  ```yaml
   estimatedResult: 
      conditionID: vehicleArc_c1
      lowerBound: 81.0
      metricName: vehicleArc
      upperBound: 100.0
   listOfParameters: 
   - !at.ac.tuwien.dsg.depic.common.entity.primitiveaction.Parameter
      parameterName: longtitudeIndex
      type: int
      value: 0
   - !at.ac.tuwien.dsg.depic.common.entity.primitiveaction.Parameter
      parameterName: latitudeIndex
      type: int
      value: 1
   - !at.ac.tuwien.dsg.depic.common.entity.primitiveaction.Parameter
      parameterName: speedIndex
      type: int
      value: 2
  ```
2. The primitive action gets, enriches and provides data via the [DataAssetLoader](https://github.com/tuwiendsg/EPICS/blob/master/depic/data-asset-loader/src/main/java/at/ac/tuwien/dsg/dataassetloader/restws/DataassetResource.java). The procedure include steps:
  1. API [repo/datacopy](https://github.com/tuwiendsg/EPICS/blob/master/depic/data-asset-loader/src/main/java/at/ac/tuwien/dsg/dataassetloader/restws/DataassetResource.java#L107-L111): Clone a copy of the data from the table *DataAsset* to the table *ProcessingDataAsset*
  2. API [repo/getpartition](https://github.com/tuwiendsg/EPICS/blob/master/depic/data-asset-loader/src/main/java/at/ac/tuwien/dsg/dataassetloader/restws/DataassetResource.java#L287-L290): Load the cloned data and enrich it.
  3. API [repo/savepartition](https://github.com/tuwiendsg/EPICS/blob/master/depic/data-asset-loader/src/main/java/at/ac/tuwien/dsg/dataassetloader/restws/DataassetResource.java#L340-L343): Save the enriched data back *ProcessingDataAsset* 

3. The [ElasticDaaSClient](https://github.com/tuwiendsg/EPICS/tree/master/depic/examples/utils/ElasticDaaSClient) is used to simulate a number of users to request the eDaaS. User can configure the IP of the eDaaS in the [DaaSClient](https://github.com/tuwiendsg/EPICS/blob/master/depic/examples/utils/ElasticDaaSClient/src/main/java/at/ac/tuwien/dsg/elasticdaasclient/demo/DaaSClient.java), then run [the example of calling REST is here](https://github.com/tuwiendsg/EPICS/blob/master/depic/examples/utils/ElasticDaaSClient/src/main/java/at/ac/tuwien/dsg/elasticdaasclient/demo/RestClientEDaaS.java)

