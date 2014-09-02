/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.tooling.edo;

import java.util.List;

/**
 *
 * @author Jun
 */
public class DepicConfiguration {
    String edoID;
    List listOfDataElasticityMetricConfs;

    public DepicConfiguration() {
    }

    public DepicConfiguration(String edoID, List listOfDataElasticityMetricConfs) {
        this.edoID = edoID;
        this.listOfDataElasticityMetricConfs = listOfDataElasticityMetricConfs;
    }

    public String getEdoID() {
        return edoID;
    }

    public void setEdoID(String edoID) {
        this.edoID = edoID;
    }

    public List getListOfDataElasticityMetricConfs() {
        return listOfDataElasticityMetricConfs;
    }

    public void setListOfDataElasticityMetricConfs(List listOfDataElasticityMetricConfs) {
        this.listOfDataElasticityMetricConfs = listOfDataElasticityMetricConfs;
    }
   
    
}
