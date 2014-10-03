/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.edasich.configuration;

/**
 *
 * @author Jun
 */
public class AnalyticEngineConfiguration {
    String analyticEngineID;
    String analyticEngineName;
    String ip;
    String port;
    String api;

    public AnalyticEngineConfiguration() {
    }

    public AnalyticEngineConfiguration(String analyticEngineID, String analyticEngineName, String ip, String port, String api) {
        this.analyticEngineID = analyticEngineID;
        this.analyticEngineName = analyticEngineName;
        this.ip = ip;
        this.port = port;
        this.api = api;
    }

    public String getAnalyticEngineID() {
        return analyticEngineID;
    }

    public void setAnalyticEngineID(String analyticEngineID) {
        this.analyticEngineID = analyticEngineID;
    }

    public String getAnalyticEngineName() {
        return analyticEngineName;
    }

    public void setAnalyticEngineName(String analyticEngineName) {
        this.analyticEngineName = analyticEngineName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }
    
    
    
    
    
}
