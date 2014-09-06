/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.tooling.edo.metric;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jun
 */
public class GeneralInfo {
    String name;
    List listOfRanges;

    public GeneralInfo() {
        
    }

    public GeneralInfo(String name, List listOfRanges) {
        this.name = name;
        this.listOfRanges = listOfRanges;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getListOfRanges() {
        return listOfRanges;
    }

    public void setListOfRanges(List listOfRanges) {
        this.listOfRanges = listOfRanges;
    }
    
    
    
    
    
}
