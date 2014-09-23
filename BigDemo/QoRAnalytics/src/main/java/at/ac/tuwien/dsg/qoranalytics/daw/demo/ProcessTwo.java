/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.qoranalytics.daw.demo;

/**
 *
 * @author Jun
 */
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class ProcessTwo implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("In ProcessTwo");
    }
}