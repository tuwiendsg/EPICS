/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataenrichment;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jun
 */
public class Utils {
    
    
    public String getWebFilePath(){
        
        
        String path = Utils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        
      //  System.out.println("path: " + path);
        
        int index = path.indexOf("/WEB-INF/classes/at/ac");
        path = path.substring(0, index);
        try {
            path = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return  path;
        
    }
    
    
    public void writeToHTMLFile(String htmlData,String fileName) {
        
        String filePath = getWebFilePath();
        filePath = filePath + "/" + fileName;
        IOUtils iou = new IOUtils();
        iou.writeData(htmlData, filePath);
    }
    
}
