/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataassetloader.store;

import at.ac.tuwien.dsg.common.entity.eda.DataAssetFunction;
import at.ac.tuwien.dsg.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.common.utils.MySqlConnectionManager;
import at.ac.tuwien.dsg.dataassetloader.configuration.Configuration;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Jun
 */
public class DataAssetFunctionStore {

    MySqlConnectionManager connectionManager;
    
    public DataAssetFunctionStore() {
        Configuration config = new Configuration();
        String ip = config.getConfig("DB.ELASTICITY.PROCESSES.REPO.IP");
        String port = config.getConfig("DB.ELASTICITY.PROCESSES.REPO.PORT");
        String database = config.getConfig("DB.ELASTICITY.PROCESSES.REPO.DATABASE");
        String username = config.getConfig("DB.ELASTICITY.PROCESSES.REPO.USERNAME");
        String password = config.getConfig("DB.ELASTICITY.PROCESSES.REPO.PASSWORD");
        connectionManager = new MySqlConnectionManager(ip, port, database, username, password);
    }
    
    public String getDataAssetFunction(String dataAssetID){

        String dafXML = "";
        try {
            InputStream inputStream = null;

            String sql = "select * from DataAssetFunction WHERE dataAssetID='" + dataAssetID + "'";
            ResultSet rs = connectionManager.ExecuteQuery(sql);

            try {
                while (rs.next()) {
                    inputStream = rs.getBinaryStream("dataAssetFunction");
                }
            } catch (SQLException ex) {
                Logger.getLogger(DataAssetFunctionStore.class.getName()).log(Level.SEVERE, null, ex);
            }

            StringWriter writer = new StringWriter();
            String encoding = StandardCharsets.UTF_8.name();

            IOUtils.copy(inputStream, writer, encoding);
            dafXML = writer.toString();
            Logger.getLogger(DataAssetFunctionStore.class.getName()).log(Level.INFO, null, "DAF XML: " + dafXML);
                

        } catch (IOException ex) {
            Logger.getLogger(DataAssetFunctionStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return dafXML;
    }
    
    
    
}
