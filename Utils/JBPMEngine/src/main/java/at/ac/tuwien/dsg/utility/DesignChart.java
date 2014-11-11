/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.utility;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
//import org.jfree.ui.ApplicationFrame;
/**
 *
 * @author dsg
 */
public class DesignChart {
    LinkedList<String> row1=new LinkedList<String>();
   
    public void chart(LinkedList<String> xValue, LinkedList<String> yValue) throws Exception
    {
        
   XYSeries series = new XYSeries("Random Data");
   for(int i=0;i<xValue.size();i++) 
   {
       series.add(Double.parseDouble(xValue.get(i)), Double.parseDouble(yValue.get(i)));
   }
    /*series.add(1.0, 500.2);
    series.add(5.0, 694.1);
    series.add(4.0, 100.0);
    series.add(12.5, 734.4);
    series.add(17.3, 453.2);
    series.add(21.2, 500.2);
    series.add(21.9, 400);
    series.add(25.6, 734.4);
    series.add(30.0, 453.2);*/
        final XYSeriesCollection data = new XYSeriesCollection(series);
    final JFreeChart chart = ChartFactory.createXYLineChart(
        "XY Series Demo",
        "X", 
        "Y", 
        data,
        PlotOrientation.VERTICAL,
        true,
        true,
        false
    );
    System.out.println("chart is generated");
        BufferedImage img=chart.createBufferedImage( 300, 200);
    File outputfile = new File("./example/Sample.png");
    ImageIO.write(img, "png", outputfile);
    
    
    }

   /*public static void main(String []p)
   {
       DesignChart chart12=new DesignChart();
        try {
            chart12.chart();
            
        } catch (Exception e) {
        }
   }*/
    }

