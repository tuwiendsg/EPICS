/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.dataassetfunctionmanagement;

import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.DataAnalyticsFunction;
import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.DataAssetForm;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DBType;
import at.ac.tuwien.dsg.depic.common.utils.IOUtils;
import at.ac.tuwien.dsg.depic.dataassetfunctionmanagement.engine.WorkflowEngine;
import at.ac.tuwien.dsg.depic.dataassetfunctionmanagement.engine.WorkflowEngine;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jun
 */
public class TestDaf {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        IOUtils iou = new IOUtils("/Users/Jun/Desktop/DEPIC Demo/eDaaS_power");
        String daf = iou.readData("daf3.bpmn");

        System.out.println("daf: " + daf);
        DataAnalyticsFunction dataAnalyticsFunction = new DataAnalyticsFunction("daf3", DataAssetForm.CSV, DBType.MYSQL, daf);
        WorkflowEngine wf = new WorkflowEngine(dataAnalyticsFunction, "daf3");
        wf.startWFEngine();

    }

}
