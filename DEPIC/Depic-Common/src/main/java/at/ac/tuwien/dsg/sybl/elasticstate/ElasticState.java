/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.sybl.elasticstate;



import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author Jun
 */
@XmlSeeAlso({CategoricalElCapState.class, NumericalElCapState.class})
@XmlRootElement
public class ElasticState {
    
    List elCapStates;
    double totalCost;
    double currentBudget;
    String status;

  
    
   
    public ElasticState() {
        elCapStates = new ArrayList();
        totalCost=0;
        status="INITIAL";
    }

    public double getTotalCost() {
        return totalCost;
    }

    @XmlElement
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getStatus() {
        return status;
    }

    @XmlElement
    public void setStatus(String status) {
        this.status = status;
    }

    public List getElCapStates() {
        return elCapStates;
    }
    
    
    
    
    @XmlElement
    public void setElCapStates(List elCapStates) {
        this.elCapStates = elCapStates;
    }

    public double getCurrentBudget() {
        return currentBudget;
    }
    
    @XmlElement
    public void setCurrentBudget(double currentBudget) {
        this.currentBudget = currentBudget;
    }

  
    
    
    
    public void addCost(double cost) {
        this.totalCost += cost;
    }
    
    public void subtractCost(double cost) {
        this.totalCost -= cost;
    }
    
    public void addElCapValue(CategoricalElCapState elCapState){
        elCapStates.add(elCapState);
    }
    
    public void addElCapValue(NumericalElCapState elCapState){
        elCapStates.add(elCapState);
    }

 
    
    

    public double getCurrentElCapValueFromElCapName(String elCapName) {
        
        double currentValue=0;
        
         for (int i = 0; i < elCapStates.size(); i++) {

            if (elCapStates.get(i) instanceof CategoricalElCapState) {
                CategoricalElCapState elState = (CategoricalElCapState)elCapStates.get(i);
                
                if (elState.name.equals(elCapName)) {
                    currentValue = elState.getCurrentValue();
                }
            
            } else if (elCapStates.get(i) instanceof NumericalElCapState) {
                NumericalElCapState elState = (NumericalElCapState)elCapStates.get(i);
                if (elState.name.equals(elCapName)) {
                    currentValue = elState.getCurrentValue();
                }
            }
         }
         
         
        return currentValue;
    }
    
    public double getCurrentElCapCostFromElCapName(String elCapName) {
        
        double currentCost=0;
        
         for (int i = 0; i < elCapStates.size(); i++) {

            if (elCapStates.get(i) instanceof CategoricalElCapState) {
                CategoricalElCapState elState = (CategoricalElCapState)elCapStates.get(i);
                
                if (elState.name.equals(elCapName)) {
                    currentCost = elState.getCost();
                }
            
            } else if (elCapStates.get(i) instanceof NumericalElCapState) {
                NumericalElCapState elState = (NumericalElCapState)elCapStates.get(i);
                if (elState.name.equals(elCapName)) {
                    currentCost = elState.getCost();
                }
            }
         }
         
         
        return currentCost;
    }
    
    
     public void setCurrentElValFromElCapName(String elCapName, int elValue) {
        
     
        
         for (int i = 0; i < elCapStates.size(); i++) {

            if (elCapStates.get(i) instanceof CategoricalElCapState) {
                CategoricalElCapState elState = (CategoricalElCapState)elCapStates.get(i);
                
                if (elState.name.equals(elCapName)) {
                    elState.setCurrentValue(elValue);
                }
            
            } 
         }
         
         
       
    }
    
     public void setCurrentElValFromElCapName(String elCapName, double elValue) {
        
     
        
         for (int i = 0; i < elCapStates.size(); i++) {

          if (elCapStates.get(i) instanceof NumericalElCapState) {
                NumericalElCapState elState = (NumericalElCapState)elCapStates.get(i);
                if (elState.name.equals(elCapName)) {
                     elState.setCurrentValue(elValue);
                }
            }
         }
         
         
       
    }
     
     
      public void setCurrentValCostFromElCapName(String elCapName, double elCost) {
        
     
        for (int i = 0; i < elCapStates.size(); i++) {

            if (elCapStates.get(i) instanceof CategoricalElCapState) {
                CategoricalElCapState elState = (CategoricalElCapState)elCapStates.get(i);
                
                if (elState.name.equals(elCapName)) {
                    elState.setCost(elCost);
                }
            
            } else if (elCapStates.get(i) instanceof NumericalElCapState) {
                NumericalElCapState elState = (NumericalElCapState)elCapStates.get(i);
                if (elState.name.equals(elCapName)) {
                     elState.setCost(elCost);
                }
            }
         }
         
       
    }
      
      
      public String resetStatus(){
          
          if (totalCost<=currentBudget) {
              status = "READY";
          } else {
              status = "STOP";
          }
          
          
          return status;
      }
    
    

    
    
}
