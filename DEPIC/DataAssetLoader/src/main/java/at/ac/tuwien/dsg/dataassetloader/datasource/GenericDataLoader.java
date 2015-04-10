/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataassetloader.datasource;

import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.DataAnalyticsFunction;
import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.EDaaSType;
import at.ac.tuwien.dsg.depic.common.entity.eda.da.DataAsset;
import at.ac.tuwien.dsg.depic.common.entity.eda.da.DataPartitionRequest;

/**
 *
 * @author Jun
 */
public class GenericDataLoader implements DataLoader{

    private EDaaSType eDaaSType;
    
    public GenericDataLoader() {
    }

    public GenericDataLoader(EDaaSType eDaaSType) {
        this.eDaaSType = eDaaSType;
    }

    
    @Override
    public String loadDataAsset(DataAnalyticsFunction dataAssetFunction) {
        String xml="";
        
        if (eDaaSType.equals(EDaaSType.MYSQL)) {
            MySQLDataLoader msqldl = new MySQLDataLoader();
            xml = msqldl.loadDataAsset(dataAssetFunction);
        } else if (eDaaSType.equals(EDaaSType.CASSANDRA)) {
            CassandraDataLoader cdl = new CassandraDataLoader();
            xml = cdl.loadDataAsset(dataAssetFunction);
        } else if (eDaaSType.equals(EDaaSType.CASSANDRA_NEAR_REAL_TIME)) {
            throw new UnsupportedOperationException("Not supported yet.");
        } else if (eDaaSType.equals(EDaaSType.MONGODB)) {
            throw new UnsupportedOperationException("Not supported yet.");
        } else if (eDaaSType.equals(EDaaSType.MONGODB_NEAR_REAL_TIME)) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        return xml;
    }

    @Override
    public String copyDataAssetRepo(DataPartitionRequest request) {
        String xml="";
        
        if (eDaaSType.equals(EDaaSType.MYSQL)) {
            MySQLDataLoader msqldl = new MySQLDataLoader();
            xml = msqldl.copyDataAssetRepo(request);
        } else if (eDaaSType.equals(EDaaSType.CASSANDRA)) {
            CassandraDataLoader cdl = new CassandraDataLoader();
            xml = cdl.copyDataAssetRepo(request);
        } else if (eDaaSType.equals(EDaaSType.CASSANDRA_NEAR_REAL_TIME)) {
            throw new UnsupportedOperationException("Not supported yet.");
        } else if (eDaaSType.equals(EDaaSType.MONGODB)) {
            throw new UnsupportedOperationException("Not supported yet.");
        } else if (eDaaSType.equals(EDaaSType.MONGODB_NEAR_REAL_TIME)) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        return xml;
    }

    @Override
    public String getNoOfParitionRepo(DataPartitionRequest request) {
        String xml="";
        
        if (eDaaSType.equals(EDaaSType.MYSQL)) {
            MySQLDataLoader msqldl = new MySQLDataLoader();
            xml = msqldl.getNoOfParitionRepo(request);
        } else if (eDaaSType.equals(EDaaSType.CASSANDRA)) {
            CassandraDataLoader cdl = new CassandraDataLoader();
            xml = cdl.getNoOfParitionRepo(request);
        } else if (eDaaSType.equals(EDaaSType.CASSANDRA_NEAR_REAL_TIME)) {
            throw new UnsupportedOperationException("Not supported yet.");
        } else if (eDaaSType.equals(EDaaSType.MONGODB)) {
            throw new UnsupportedOperationException("Not supported yet.");
        } else if (eDaaSType.equals(EDaaSType.MONGODB_NEAR_REAL_TIME)) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        return xml;
    }

    @Override
    public String getDataPartitionRepo(DataPartitionRequest request) {
        String xml="";
        
        if (eDaaSType.equals(EDaaSType.MYSQL)) {
            MySQLDataLoader msqldl = new MySQLDataLoader();
            xml = msqldl.getDataPartitionRepo(request);
        } else if (eDaaSType.equals(EDaaSType.CASSANDRA)) {
            CassandraDataLoader cdl = new CassandraDataLoader();
            xml = cdl.getDataPartitionRepo(request);
        } else if (eDaaSType.equals(EDaaSType.CASSANDRA_NEAR_REAL_TIME)) {
            throw new UnsupportedOperationException("Not supported yet.");
        } else if (eDaaSType.equals(EDaaSType.MONGODB)) {
            throw new UnsupportedOperationException("Not supported yet.");
        } else if (eDaaSType.equals(EDaaSType.MONGODB_NEAR_REAL_TIME)) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        return xml;
    }

    public void saveDataPartitionRepo(DataAsset dataAsset) {
        
       
        if (eDaaSType.equals(EDaaSType.MYSQL)) {
            MySQLDataLoader msqldl = new MySQLDataLoader();
            msqldl.saveDataPartitionRepo(dataAsset);
        } else if (eDaaSType.equals(EDaaSType.CASSANDRA)) {  
            CassandraDataLoader cdl = new CassandraDataLoader();   
            cdl.saveDataPartitionRepo(dataAsset);
        } else if (eDaaSType.equals(EDaaSType.CASSANDRA_NEAR_REAL_TIME)) {
            throw new UnsupportedOperationException("Not supported yet.");
        } else if (eDaaSType.equals(EDaaSType.MONGODB)) {
            throw new UnsupportedOperationException("Not supported yet.");
        } else if (eDaaSType.equals(EDaaSType.MONGODB_NEAR_REAL_TIME)) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

   
    
}
