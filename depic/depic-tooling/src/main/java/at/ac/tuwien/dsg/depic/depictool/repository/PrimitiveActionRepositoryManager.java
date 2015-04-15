/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.depictool.repository;

import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.PrimitiveActionRepository;
import at.ac.tuwien.dsg.depic.common.utils.YamlUtils;
import at.ac.tuwien.dsg.depic.depictool.utils.Configuration;

/**
 *
 * @author Jun
 */
public class PrimitiveActionRepositoryManager {

    PrimitiveActionRepository primitiveActionRepository;
    
    public PrimitiveActionRepositoryManager() {
        
    }
    
    private void loadPrimitiveActionRepository(){
        Configuration cfg = new Configuration();
        
        String filePath = cfg.getConfigPath()+ "/primitiveActionRepository.yml";       
        PrimitiveActionRepository primitiveActionRepository = YamlUtils.fromYaml(PrimitiveActionRepository.class, filePath);
    }
    
    
}
