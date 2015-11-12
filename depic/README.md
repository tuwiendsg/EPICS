DEPIC
=====

Data Elasticity Programming in the Cloud
=====

Application as examples: [GPSAnalytics](https://github.com/tuwiendsg/EPICS/tree/master/depic/examples/applications/GPSAnalytics)

Main flow of DEPIC:

1. User submit [DAF and QOR](https://github.com/tuwiendsg/EPICS/tree/master/depic/examples/applications/GPSAnalytics/experiment1/case1/inputs). The PrimitiveActionRepository is stored in the database. The [Generator](https://github.com/tuwiendsg/EPICS/blob/master/depic/depic-tooling/src/main/java/at/ac/tuwien/dsg/depic/depictool/generator/Generator.java) generate 2 things:

- Call [ElasticProcessGenerator](https://github.com/tuwiendsg/EPICS/tree/master/depic/depic-elastic-process-generator/src/main/java/at/ac/tuwien/dsg/depic/elastic/process/generator) 
will generate a [DataElasticityManagementProcess](https://github.com/tuwiendsg/EPICS/blob/master/depic/depic-common/src/main/java/at/ac/tuwien/dsg/depic/common/entity/eda/elasticprocess/DataElasticityManagementProcess.java).
An example of process is in the [output folder](https://github.com/tuwiendsg/EPICS/tree/master/depic/examples/applications/GPSAnalytics/experiment1/case1/output)

- Call [DaaSGenerator](https://github.com/tuwiendsg/EPICS/blob/master/depic/depic-tooling/src/main/java/at/ac/tuwien/dsg/depic/depictool/generator/DaaSGenerator.java) to generate the eDaaS based on the [eDaaS Template](https://github.com/tuwiendsg/EPICS/tree/master/depic/depic-tooling/src/main/resources)

2. eDaaS is running. When receiving a request from a customer, the eDaaS calls the "DataAssetRequestHandler.java" (see in project.zip). It activate the [DataElasticityMonitor](https://github.com/tuwiendsg/EPICS/blob/aa2521dfc706861752b11cf48ee3563e63452a9b/depic/orchestrator/src/main/java/at/ac/tuwien/dsg/orchestrator/dataelasticitycontroller/DataElasticityMonitor.java)

According to the [Elastic Process](https://github.com/tuwiendsg/EPICS/blob/master/depic/examples/experiment1/case1/output/elastic_process.yml), DEPIC will check the list of the data column in the *listOfParameters* (the *longtitudeIndex, latitude and speedIndex*):
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
The primitive actions based on these parameters to get data via the [DataAssetLoader](https://github.com/tuwiendsg/EPICS/blob/master/depic/data-asset-loader/src/main/java/at/ac/tuwien/dsg/dataassetloader/restws/DataassetResource.java). The primitive action follows below procedure to enrich the data:

- API *repo/datacopy*: Clone a copy of the data from the table *DataAsset* to the table *ProcessingDataAsset*

- API *repo/getpartition*: Load the cloned data and enrich it.

- API *repo/savepartition*: Save the enriched data back *ProcessingDataAsset* 

- When the elastic process execution is done, the data is send back to customer

