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

@XmlRootElement(name = "tableRow")
@XmlAccessorType(XmlAccessType.FIELD)
public class TableRow {
    
    @XmlElement(name = "listOfTableCells", required = true)
    List<TableCell> listOfTableCells;

    public TableRow() {
    }

    public TableRow(List<TableCell> listOfTableCells) {
        this.listOfTableCells = listOfTableCells;
    }

    public List<TableCell> getListOfTableCells() {
        return listOfTableCells;
    }

    public void setListOfTableCells(List<TableCell> listOfTableCells) {
        this.listOfTableCells = listOfTableCells;
    }

}
