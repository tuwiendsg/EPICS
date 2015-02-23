/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataassetloader.datasource;

import at.ac.tuwien.dsg.common.entity.eda.da.DataAsset;
import at.ac.tuwien.dsg.common.entity.eda.da.DataPartitionRequest;
import at.ac.tuwien.dsg.dataassetloader.configuration.Configuration;
import at.ac.tuwien.dsg.dataassetloader.connector.CassandraManagementAPI;
import at.ac.tuwien.dsg.dataassetloader.connector.entities.Keyspace;
import at.ac.tuwien.dsg.dataassetloader.connector.entities.RowColumn;
import at.ac.tuwien.dsg.dataassetloader.connector.entities.Table;
import at.ac.tuwien.dsg.dataassetloader.connector.entities.TableRow;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jun
 */
public class CassandraDataLoader implements DataLoader{

    @Override
    public String loadDataAsset(String dataAssetFunctionStr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String copyDataAssetRepo(DataPartitionRequest request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNoOfParitionRepo(DataPartitionRequest request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDataPartitionRepo(DataPartitionRequest request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveDataPartitionRepo(DataAsset dataAsset) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void insearTable(){
        
        Configuration cfg = new Configuration();
        String ip = cfg.getConfig("CASSANDRA.DB.IP");
        int port = Integer.parseInt(cfg.getConfig("CASSANDRA.DB.PORT"));
        String key= cfg.getConfig("CASSANDRA.DB.KEY");
        String table = cfg.getConfig("CASSANDRA.DB.TABLE");
        
        CassandraManagementAPI api = new CassandraManagementAPI();
        api.setCassandraHostIP(ip);
        api.setCasandraPort(port);
        
        Keyspace keyspace = new Keyspace(key);
        Table tab = new Table(keyspace, table, "", "");
        
        TableRow row = new TableRow();
        RowColumn c1 = new RowColumn("attr1", "21");
        RowColumn c2 = new RowColumn("attr2", "22");
        RowColumn c3 = new RowColumn("attr3", "23");
        List<RowColumn> cs = new ArrayList<RowColumn>();
        cs.add(c1);
        cs.add(c2);
        cs.add(c3);
        row.setValues(cs);

        try {
            api.createKeyspace(keyspace);
            api.createTable(tab);
            api.insertRowsInTable(key, table, null);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CassandraDataLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
}
