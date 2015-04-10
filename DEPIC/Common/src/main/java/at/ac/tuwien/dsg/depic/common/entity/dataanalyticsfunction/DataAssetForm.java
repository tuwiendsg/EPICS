/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction;

/**
 *
 * @author Jun
 */
public enum DataAssetForm {
    CSV("CSV"), FIGURE("FIGURE");

    private String dataAssetForm;

    private DataAssetForm(String dataAssetForm) {
        this.dataAssetForm = dataAssetForm;
    }

    public String getDataAssetForm() {
        return dataAssetForm;
    }
}
