Application as examples: [GPSAnalytics](https://github.com/tuwiendsg/EPICS/tree/master/depic/examples/GPSAnalytics)

Main flow of DEPIC:

1. User submit [DAF and QOR](https://github.com/tuwiendsg/EPICS/tree/master/depic/examples/experiment1/case1/inputs). The PrimitiveActionRepository is stored in the database. The [Generator](https://github.com/tuwiendsg/EPICS/blob/master/depic/depic-tooling/src/main/java/at/ac/tuwien/dsg/depic/depictool/generator/Generator.java) generate 2 things:

- Call [ElasticProcessGenerator](https://github.com/tuwiendsg/EPICS/tree/master/depic/depic-elastic-process-generator/src/main/java/at/ac/tuwien/dsg/depic/elastic/process/generator) 
will generate a [DataElasticityManagementProcess](https://github.com/tuwiendsg/EPICS/blob/master/depic/depic-common/src/main/java/at/ac/tuwien/dsg/depic/common/entity/eda/elasticprocess/DataElasticityManagementProcess.java).
An example of process is in the [output folder](https://github.com/tuwiendsg/EPICS/tree/master/depic/examples/experiment1/case1/output)

- Call [DaaSGenerator](https://github.com/tuwiendsg/EPICS/blob/master/depic/depic-tooling/src/main/java/at/ac/tuwien/dsg/depic/depictool/generator/DaaSGenerator.java) to generate the eDaaS based on the [eDaaS Template](https://github.com/tuwiendsg/EPICS/tree/master/depic/depic-tooling/src/main/resources)

2. eDaaS is running. When receiving a request from user, the eDaaS calls the "DataAssetRequestHandler.java" (see in project.zip). It activate the [DataElasticityMonitor](https://github.com/tuwiendsg/EPICS/blob/aa2521dfc706861752b11cf48ee3563e63452a9b/depic/orchestrator/src/main/java/at/ac/tuwien/dsg/orchestrator/dataelasticitycontroller/DataElasticityMonitor.java)

 
