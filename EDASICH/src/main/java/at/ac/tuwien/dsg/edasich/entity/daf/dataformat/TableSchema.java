/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.edasich.entity.daf.dataformat;

import static java.util.Collections.list;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */

@XmlRootElement(name = "tableSchema")
@XmlAccessorType(XmlAccessType.FIELD)
public class TableSchema {
    
    @XmlElement(name = "listOfTables", required = true)
    List<Table> listOfTables;

    public TableSchema() {
    }

    public TableSchema(List<Table> listOfTables) {
        this.listOfTables = listOfTables;
    }

    public List<Table> getListOfTables() {
        return listOfTables;
    }

    public void setListOfTables(List<Table> listOfTables) {
        this.listOfTables = listOfTables;
    }
    
}
