/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.dataresourceaccess;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Jun
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(at.ac.tuwien.dsg.dataresourceaccess.EDODelivery2Resource.class);
        resources.add(at.ac.tuwien.dsg.dataresourceaccess.EDODelivery3Resource.class);
        resources.add(at.ac.tuwien.dsg.dataresourceaccess.EDODeliveryResource.class);
        resources.add(at.ac.tuwien.dsg.dataresourceaccess.EDORepoCleanResource.class);
        resources.add(at.ac.tuwien.dsg.dataresourceaccess.EDORepoMeasurementResource.class);
        resources.add(at.ac.tuwien.dsg.dataresourceaccess.EDORepoResource.class);
        resources.add(at.ac.tuwien.dsg.dataresourceaccess.EDOResponseTimeResource.class);
        resources.add(at.ac.tuwien.dsg.dataresourceaccess.PowerconsumptionResource.class);
    }
    
}
