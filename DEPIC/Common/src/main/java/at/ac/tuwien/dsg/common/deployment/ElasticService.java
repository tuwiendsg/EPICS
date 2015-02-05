/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.common.deployment;

/**
 *
 * @author Jun
 */
public class ElasticService {
    private String actionID;
    private String serviceID;
    private String uri;

    public ElasticService() {
    }

    public ElasticService(String actionID, String serviceID, String uri) {
        this.actionID = actionID;
        this.serviceID = serviceID;
        this.uri = uri;
    }

    public String getActionID() {
        return actionID;
    }

    public void setActionID(String actionID) {
        this.actionID = actionID;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
    
    
    
    
}
