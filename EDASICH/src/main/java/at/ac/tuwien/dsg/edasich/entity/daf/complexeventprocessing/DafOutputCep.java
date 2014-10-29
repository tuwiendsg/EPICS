/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.edasich.entity.daf.complexeventprocessing;

import at.ac.tuwien.dsg.edasich.entity.daf.DafOutput;
import at.ac.tuwien.dsg.edasich.entity.daf.dataformat.TableSchema;
import at.ac.tuwien.dsg.edasich.entity.daf.datasource.DataSourceMySQL;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author Jun
 */

@XmlRootElement(name = "dafOutput")
@XmlAccessorType(XmlAccessType.FIELD)
public class DafOutputCep {
    
    @XmlElement(name = "outputDataFormat", required = true)
    TableSchema outputDataFormat;
    
    @XmlElement(name = "outputDataSource", required = true)
    DataSourceMySQL outputDataSource;

    public DafOutputCep() {
    }

    public DafOutputCep(TableSchema outputDataFormat, DataSourceMySQL outputDataSource) {
        this.outputDataFormat = outputDataFormat;
        this.outputDataSource = outputDataSource;
    }

    public TableSchema getOutputDataFormat() {
        return outputDataFormat;
    }

    public void setOutputDataFormat(TableSchema outputDataFormat) {
        this.outputDataFormat = outputDataFormat;
    }

    public DataSourceMySQL getOutputDataSource() {
        return outputDataSource;
    }

    public void setOutputDataSource(DataSourceMySQL outputDataSource) {
        this.outputDataSource = outputDataSource;
    }
    
    
    
    
}
