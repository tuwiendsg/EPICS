# DEPIC
Data Elasticity Programming in the Cloud
=====

Application as examples: [GPSAnalytics](https://github.com/tuwiendsg/EPICS/tree/master/depic/examples/applications/GPSAnalytics)

### Main flow of DEPIC:

#### Step 1: Generate the elastic processes and the eDaaS

1. DaaS provider submits [DAF, QoR and PrimitiveActions](https://github.com/tuwiendsg/EPICS/tree/master/depic/examples/applications/GPSAnalytics/experiment1/case1/inputs). Based on that, the [Generator](https://github.com/tuwiendsg/EPICS/blob/master/depic/depic-tooling/src/main/java/at/ac/tuwien/dsg/depic/depictool/generator/Generator.java) executes the generation algorithm. 

2. The [ElasticProcessGenerator](https://github.com/tuwiendsg/EPICS/tree/master/depic/depic-elastic-process-generator/src/main/java/at/ac/tuwien/dsg/depic/elastic/process/generator) 
generates a [DataElasticityManagementProcess](https://github.com/tuwiendsg/EPICS/blob/master/depic/depic-common/src/main/java/at/ac/tuwien/dsg/depic/common/entity/eda/elasticprocess/DataElasticityManagementProcess.java).
An example of process is in the [output folder](https://github.com/tuwiendsg/EPICS/tree/master/depic/examples/applications/GPSAnalytics/experiment1/case1/output)

3. The [DaaSGenerator](https://github.com/tuwiendsg/EPICS/blob/master/depic/depic-tooling/src/main/java/at/ac/tuwien/dsg/depic/depictool/generator/DaaSGenerator.java) generates the eDaaS based on the [eDaaS Template](https://github.com/tuwiendsg/EPICS/tree/master/depic/depic-tooling/src/main/resources)

4. The **[?]** deploy the eDaaS, primitive actions, control processes and monitoring processes.

#### Step 2: Provide elastic data asset

1. The eDaaS is now running. When receiving a request from a customer, the eDaaS calls the "DataAssetRequestHandler.java" (see in project.zip). It activate the [DataElasticityMonitor](https://github.com/tuwiendsg/EPICS/blob/aa2521dfc706861752b11cf48ee3563e63452a9b/depic/orchestrator/src/main/java/at/ac/tuwien/dsg/orchestrator/dataelasticitycontroller/DataElasticityMonitor.java)

According to the [Elastic Process](https://github.com/tuwiendsg/EPICS/blob/master/depic/examples/experiment1/case1/output/elastic_process.yml), DEPIC will check the list of the data column in the *listOfParameters*. For example, the parameters belows are *longtitudeIndex, latitude and speedIndex*:
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
`1. API *repo/datacopy*: Clone a copy of the data from the table *DataAsset* to the table *ProcessingDataAsset*
`2. API *repo/getpartition*: Load the cloned data and enrich it.
`3. API *repo/savepartition*: Save the enriched data back *ProcessingDataAsset* 
`4. When the elastic process execution is done, the data is send back to customer

