The GPSAnalytics application read a streaming GPS data from an ActiveMQ, enrich the data with estimatedSpeed and output to a MySQL.

Here is the steps to run the GPSAnalytics:
1. Deploy an ActiveMQ.
2. Deploy a MySQL server and import the schema (at []) to store the analytics output.
3. Setup a Tomcat, build and deploy the .war artifact of the project.
3. Edit the configuration file "GPSAnalytics.properties". This can be put in: (1) the Tomcat folder (if running with Tomcat), or (2) in the webapp/WEB-INF.
3. Activate the analytics tasks. See the TasksActivation.java in the src/test.
4. Activate the producer to send the data to the MOM. See ProducerActivation.java in the src/test

The data will be output in the WORKING.DIR (set in the configuration file), also store in MySQL for further process.
Note: (1), (2), (3) can be deployed on the same or seperate machines, so:
 - If all components are on the same VM, no configuration file is needed, or just leave the parameters as default.
 - If the MySQL is on separate VM, please configure the previlege to access it remotely.

