/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depictool.entity.eda;

import java.util.List;

/**
 *
 * @author Jun
 */
public class DataAsset {
    DataAssetFunction daf;

    public DataAsset() {
    }
    
    public DataAsset(DataAssetFunction daf) {
        this.daf = daf;
    }

    public DataAssetFunction getDaf() {
        return daf;
    }

    public void setDaf(DataAssetFunction daf) {
        this.daf = daf;
    }
    
    
    
}
