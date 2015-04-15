/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataassetloader.datasource;

import at.ac.tuwien.dsg.dataassetloader.store.CassandraDataAssetStore;

/**
 *
 * @author Jun
 */
public class CassandraSession {
    
    String customerID;
    CassandraDataAssetStore cassandraDataAssetStore;

    public CassandraSession(String customerID, CassandraDataAssetStore cassandraDataAssetStore) {
        this.customerID = customerID;
        this.cassandraDataAssetStore = cassandraDataAssetStore;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public CassandraDataAssetStore getCassandraDataAssetStore() {
        return cassandraDataAssetStore;
    }

    public void setCassandraDataAssetStore(CassandraDataAssetStore cassandraDataAssetStore) {
        this.cassandraDataAssetStore = cassandraDataAssetStore;
    }
    
    
    
    
}
