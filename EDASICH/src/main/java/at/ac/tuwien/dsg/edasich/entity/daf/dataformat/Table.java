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
    
    @XmlElement(name = "listOfTableRows", required = true)
    List<TableRow> listOfTableRows;

    public Table() {
    }

    public Table(List<TableRow> listOfTableRows) {
        this.listOfTableRows = listOfTableRows;
    }

    public List<TableRow> getListOfTableRows() {
        return listOfTableRows;
    }

    public void setListOfTableRows(List<TableRow> listOfTableRows) {
        this.listOfTableRows = listOfTableRows;
    }
    
    
}
