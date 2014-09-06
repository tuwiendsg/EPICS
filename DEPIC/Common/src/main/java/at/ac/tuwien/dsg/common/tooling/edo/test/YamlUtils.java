/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.tooling.edo.test;


import com.esotericsoftware.yamlbeans.YamlWriter;
import at.ac.tuwien.dsg.common.tooling.edo.DepicConfiguration;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jun
 */
public class YamlUtils {
    
    
    public void convertObject2Yaml(DepicConfiguration obj) {
        try {
            YamlWriter writer = new YamlWriter(new FileWriter("configs_final.yml"));
            writer.write(obj);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(YamlUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
