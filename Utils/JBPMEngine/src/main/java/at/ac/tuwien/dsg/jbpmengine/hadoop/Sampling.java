/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.jbpmengine.hadoop;

import at.ac.tuwien.dsg.utility.DesignChart;
import at.ac.tuwien.dsg.utility.CassandraConnection;
import java.util.LinkedList;
/**
 *
 * @author Jun
 */
public class Sampling {
    private static final Sampling INSTANCE = new Sampling();
    CassandraConnection client=new CassandraConnection();
    
    String ipAddress="localhost";
    int port=9042;

    public static Sampling getInstance() {
        return INSTANCE;
    }

    public void start(String keySpaceName, String tableName, String x, String y, String condition) {
        
        
        client.connect(ipAddress, port);
        
        System.out.println("Sampling Starting ..."+keySpaceName+ "   table ="+tableName);
       //String xQuery="SELECT collection_data FROM sensor.sensor21 WHERE collection_date = '2010/12/10';";
       String xQuery= "SELECT "+x+" FROM "+keySpaceName+"."+tableName+" WHERE "+condition+";";
        LinkedList<String> xValue=client.readAll(xQuery);
        for(int i=0;i<xValue.size();i++)
        {
            System.out.println("from sampling   ====="+xValue.get(i));
        }
        
        String yQuery= "SELECT "+y+" FROM "+keySpaceName+"."+tableName+" WHERE "+condition+";";
       LinkedList<String> yValue=client.readAll(yQuery);
        
        
        
        //algorithm

        DesignChart chart12=new DesignChart();
        try {
            chart12.chart(xValue, yValue);
            
        } catch (Exception e) {
            System.out.println("Exception occured in sampling: "+e);
        }
        
        //System.out.println("ClassValue=" + samplingPercentage);
        System.out.println("Sampling Completed ...");
        
        client.close();
    }
}
