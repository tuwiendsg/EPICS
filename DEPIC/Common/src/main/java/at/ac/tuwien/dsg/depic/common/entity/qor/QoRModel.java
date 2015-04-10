/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depic.common.entity.qor;

import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.DataAssetForm;
import java.util.List;

/**
 *
 * @author Jun
 */
public class QoRModel {
    List<QoRMetric> listOfMetrics;
    List<QElement> listOfQElements;
    DataAssetForm dataAssetForm;

    public QoRModel() {
    }

    public QoRModel(List<QoRMetric> listOfMetrics, List<QElement> listOfQElements, DataAssetForm dataAssetForm) {
        this.listOfMetrics = listOfMetrics;
        this.listOfQElements = listOfQElements;
        this.dataAssetForm = dataAssetForm;
    }

    public List<QoRMetric> getListOfMetrics() {
        return listOfMetrics;
    }

    public void setListOfMetrics(List<QoRMetric> listOfMetrics) {
        this.listOfMetrics = listOfMetrics;
    }

    public List<QElement> getListOfQElements() {
        return listOfQElements;
    }

    public void setListOfQElements(List<QElement> listOfQElements) {
        this.listOfQElements = listOfQElements;
    }

    public DataAssetForm getDataAssetForm() {
        return dataAssetForm;
    }

    public void setDataAssetForm(DataAssetForm dataAssetForm) {
        this.dataAssetForm = dataAssetForm;
    }

    
}
