/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction;

/**
 *
 * @author Jun
 */
public enum EDaaSType {

    MYSQL("MYSQL"), CASSANDRA("CASSANDRA"), MONGODB("MONGODB"), MONGODB_NEAR_REAL_TIME("MONGODB_NEAR_REAL_TIME"), CASSANDRA_NEAR_REAL_TIME("CASSANDRA_NEAR_REAL_TIME");

    private String eDaaSType;

    private EDaaSType(String eDaaSType) {
        this.eDaaSType = eDaaSType;
    }

    public String geteDaaSType() {
        return eDaaSType;
    }

    
    
    

}
