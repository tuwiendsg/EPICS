/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.edasich.entity.daf.complexeventprocessing;

import at.ac.tuwien.dsg.edasich.entity.daf.datasource.DataSourceMOM;
import at.ac.tuwien.dsg.edasich.entity.daf.DafInput;
import at.ac.tuwien.dsg.edasich.entity.daf.dataformat.TableSchema;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */

@XmlRootElement(name = "dafInput")
@XmlAccessorType(XmlAccessType.FIELD)
public class DafInputCep {
    
    @XmlElement(name = "inputDataFormat", required = true)
    TableSchema inputDataFormat;
    
    @XmlElement(name = "inputDataSource", required = true)
    DataSourceMOM inputDataSource;

    public DafInputCep() {
    }

    public DafInputCep(TableSchema inputDataFormat, DataSourceMOM inputDataSource) {
        this.inputDataFormat = inputDataFormat;
        this.inputDataSource = inputDataSource;
    }

    public TableSchema getInputDataFormat() {
        return inputDataFormat;
    }

    public void setInputDataFormat(TableSchema inputDataFormat) {
        this.inputDataFormat = inputDataFormat;
    }

    public DataSourceMOM getInputDataSource() {
        return inputDataSource;
    }

    public void setInputDataSource(DataSourceMOM inputDataSource) {
        this.inputDataSource = inputDataSource;
    }
    
}
