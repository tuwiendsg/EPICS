
package at.ac.tuwien.dsg.edaas.requirement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement(name = "ConsumerRequirement")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConsumerRequirement {
    
    @XmlElement(name = "costConstraint", required = true)
    CostConstraint costConstraint;
    
    
@XmlElement(name = "SpeedArcConstraint", required = true)
SpeedArcConstraint speedarcconstraint;
@XmlElement(name = "VehicleArcConstraint", required = true)
VehicleArcConstraint vehiclearcconstraint;
@XmlElement(name = "DeliveryTimeConstraint", required = true)
DeliveryTimeConstraint deliverytimeconstraint;

    public ConsumerRequirement() {
    }

    public CostConstraint getCostConstraint() {
        return costConstraint;
    }

    public void setCostConstraint(CostConstraint costConstraint) {
        this.costConstraint = costConstraint;
    }

    
public SpeedArcConstraint getSpeedArcConstraint() {
return speedarcconstraint;
}
public VehicleArcConstraint getVehicleArcConstraint() {
return vehiclearcconstraint;
}
public DeliveryTimeConstraint getDeliveryTimeConstraint() {
return deliverytimeconstraint;
}

    
public void setSpeedArcConstraint(SpeedArcConstraint speedarcconstraint) {
this.speedarcconstraint = speedarcconstraint;
}
public void setVehicleArcConstraint(VehicleArcConstraint vehiclearcconstraint) {
this.vehiclearcconstraint = vehiclearcconstraint;
}
public void setDeliveryTimeConstraint(DeliveryTimeConstraint deliverytimeconstraint) {
this.deliverytimeconstraint = deliverytimeconstraint;
}
    

}
