/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.common.entity.eda;

/**
 *
 * @author Jun
 */
public enum EDaaSType {

    MYSQL("mysql"), CASSANDRA("cassandra"), MONGODB("mongodb"), MONGODB_NEAR_REAL_TIME("mongodb_n"), CASSANDRA_NEAR_REAL_TIME("cassandra_n");

    private String eDaaSType;

    private EDaaSType(String eDaaSType) {
        this.eDaaSType = eDaaSType;
    }

    public String geteDaaSType() {
        return eDaaSType;
    }

    

}
