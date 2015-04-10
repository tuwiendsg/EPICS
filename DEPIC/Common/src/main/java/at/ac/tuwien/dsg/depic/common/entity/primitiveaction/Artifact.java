/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.common.entity.primitiveaction;

/**
 *
 * @author Jun
 */
public class Artifact {
    
    String name;
    String description;
    String location;
    String type;
    String restfulAPI;

    public Artifact() {
    }

    public Artifact(String name, String description, String location, String type, String restfulAPI) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.type = type;
        this.restfulAPI = restfulAPI;
    }
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRestfulAPI() {
        return restfulAPI;
    }

    public void setRestfulAPI(String restfulAPI) {
        this.restfulAPI = restfulAPI;
    }
    
    
    
}
