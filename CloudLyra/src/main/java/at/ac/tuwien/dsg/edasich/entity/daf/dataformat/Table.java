/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.edasich.entity.daf.dataformat;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */
@XmlRootElement(name = "table")
@XmlAccessorType(XmlAccessType.FIELD)
public class Table {
    
    @XmlElement(name = "tableName", required = true)
    String tableName;
    
    @XmlElement(name = "listOfTableAttributes", required = true)
    List<TableAttribute> listOfTableAttributes;

    public Table() {
    }

    public Table(String tableName, List<TableAttribute> listOfTableAttributes) {
        this.tableName = tableName;
        this.listOfTableAttributes = listOfTableAttributes;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<TableAttribute> getListOfTableAttributes() {
        return listOfTableAttributes;
    }

    public void setListOfTableAttributes(List<TableAttribute> listOfTableAttributes) {
        this.listOfTableAttributes = listOfTableAttributes;
    }

    
}
