/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataresourceaccess.demo;

import at.ac.tuwien.dsg.common.entity.DataObject;
import at.ac.tuwien.dsg.common.entity.others.EDORepo;
import at.ac.tuwien.dsg.dataresourceaccess.Configuration;
import at.ac.tuwien.dsg.dataresourceaccess.edorepository.EDODeliveryAccessManagement;
import at.ac.tuwien.dsg.dataresourceaccess.edorepository.EDORepoAccessManagement;
import at.ac.tuwien.dsg.dataresourceaccess.powerconsumption.ICU_DB;
import at.ac.tuwien.dsg.dataresourceaccess.powerconsumption.PowerConsumptionDB;

/**
 *
 * @author Jun
 */
public class App {

    public static void main(String[] args) {

        //Configuration conf = new Configuration();
       // conf.setConf();
        //conf.getConf();
       // conf.printThemAll();
        
       // DataObject dataObject = PowerConsumptionDB.getDataItems();
     /*
        EDORepo edo = new EDORepo(113, 911, "adasdasdasdas", 0.99, 0.0001);
       
        EDODeliveryAccessManagement eDODeliveryAccessManagement = new EDODeliveryAccessManagement();
        eDODeliveryAccessManagement.save2DeliveryEDORepo(edo);
*/
        
        
        String content="2;2";
        System.out.println("DataAccessManagement: get EDO index " + content);
        
        EDORepoAccessManagement repoAccessManagement = new EDORepoAccessManagement();
        String xmlStr = repoAccessManagement.getRawEDO(content);
        System.out.println("DataAccessManagement: xmlStr " + xmlStr);
        
    }
}
