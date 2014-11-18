/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.orchestrator.elasticityprocessesstore;

import at.ac.tuwien.dsg.common.utils.MySqlConnectionManager;
import at.ac.tuwien.dsg.orchestrator.configuration.Configuration;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Jun
 */
public class ElasticityProcessesStore {
    
    MySqlConnectionManager connectionManager;
    
    public ElasticityProcessesStore() {
        Configuration config = new Configuration();
        String ip = config.getConfig("DB.ELASTICITY.PROCESSES.REPO.IP");
        String port = config.getConfig("DB.ELASTICITY.PROCESSES.REPO.PORT");
        String database = config.getConfig("DB.ELASTICITY.PROCESSES.REPO.DATABASE");
        String username = config.getConfig("DB.ELASTICITY.PROCESSES.REPO.USERNAME");
        String password = config.getConfig("DB.ELASTICITY.PROCESSES.REPO.PASSWORD");
        connectionManager = new MySqlConnectionManager(ip, port, database, username, password);

    }
    
    
    
    public String getDeployementDescription(String edaas) {

        String deploymentDescription = "";
        try {
            InputStream inputStream = null;

            String sql = "Select * from DeploymentDescription where edaas='" + edaas + "'";

            ResultSet rs = connectionManager.ExecuteQuery(sql);

            try {
                while (rs.next()) {
                    inputStream = rs.getBinaryStream("specs");
                }
            } catch (SQLException ex) {
                Logger.getLogger(ElasticityProcessesStore.class.getName()).log(Level.SEVERE, null, ex);
            }

            StringWriter writer = new StringWriter();
            String encoding = StandardCharsets.UTF_8.name();

            IOUtils.copy(inputStream, writer, encoding);
            deploymentDescription = writer.toString();
          

        } catch (IOException ex) {
            Logger.getLogger(ElasticityProcessesStore.class.getName()).log(Level.SEVERE, null, ex);
        }

        return deploymentDescription;
    }
    
}
