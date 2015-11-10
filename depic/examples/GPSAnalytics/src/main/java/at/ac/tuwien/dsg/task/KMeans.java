/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.task;

import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAsset;
import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAttribute;
import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataItem;
import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataItemCluster;
import at.ac.tuwien.dsg.mlr.util.Configuration;
import at.ac.tuwien.dsg.mlr.util.JAXBUtils;
import at.ac.tuwien.dsg.mlr.util.IOUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */
public class KMeans implements Runnable {

    private Thread t;
    int dataAssetCounter;
    int k;
    int stopCondition;
    double epxilon;
    int speedIndex;
    List<DataItem> listOfCenters;
    List<Integer> listOfAttributeIndice;
    String dataDir;
    String eDaaSName;

    public KMeans(int k, int stopCondition, double epxilon, int speedIndex, List<Integer> listOfAttributeIndice) {
        this.k = k;
        this.stopCondition = stopCondition;
        this.epxilon = epxilon;
        this.speedIndex = speedIndex;
        this.listOfAttributeIndice = listOfAttributeIndice;
        dataAssetCounter = 0;
        //rootPath = "/home/ubuntu/log/temp";
        dataDir = Configuration.getDataDir();
        eDaaSName="edaas1";
    }

    public void run() {

        do {
            String filePathString = dataDir + "/mom-daf-gps-" + String.valueOf(dataAssetCounter) + ".data";
            File f = new File(filePathString);
            if (f.exists() && !f.isDirectory()) {

                System.out.println("KMEANS-FOUND");
                IOUtils iou = new IOUtils(dataDir);
                
                
                String dataAssetXML = iou.readData("mom-daf-gps-" + String.valueOf(dataAssetCounter) + ".data");

                DataAsset dataAsset = null;
                try {
                    dataAsset = JAXBUtils.unmarshal(dataAssetXML, DataAsset.class);

                } catch (JAXBException ex) {
                    Logger.getLogger(KMeans.class.getName()).log(Level.SEVERE, null, ex);
                }

                // clustering
                List<Integer> listOfAttibuteIndice = new ArrayList<Integer>();
                listOfAttibuteIndice.add(1);
                listOfAttibuteIndice.add(2);

                KMeans kMeans = new KMeans(10, 20, 0.00001, 3, listOfAttibuteIndice);
                kMeans.clustering(dataAsset);

                //print result
                List<DataItem> listOfDataItems = dataAsset.getListOfDataItems();
                int i = 0;
                for (DataItem dataItem : listOfDataItems) {
                    List<DataAttribute> listOfDataAttributes = dataItem.getListOfAttributes();
                    i++;
//                    System.out.println(
//                            i + ":\t"
//                            + listOfDataAttributes.get(1).getAttributeValue() + "\t"
//                            + listOfDataAttributes.get(2).getAttributeValue() + "\t"
//                            + listOfDataAttributes.get(3).getAttributeValue() + "\t"
//                            + listOfDataAttributes.get(listOfDataAttributes.size() - 1).getAttributeValue() + "\t");

                }

                try {
                    dataAsset.setDataAssetID(eDaaSName);
                    dataAssetXML = JAXBUtils.marshal(dataAsset, DataAsset.class);

                    iou.writeData(dataAssetXML, "kmeans-daf-gps-" + String.valueOf(dataAssetCounter) + ".data");
                } catch (JAXBException ex) {
                    Logger.getLogger(KMeans.class.getName()).log(Level.SEVERE, null, ex);
                }

                dataAssetCounter++;
            } else {
                System.out.println("KMEANS-WAITING");
            }

            try {
                Thread.sleep(1000);

            } catch (InterruptedException ex) {

            }
        } while (true);
    }
    
    public void start() {
        if (t == null) {
            t = new Thread(this, "kmeans");
            t.start();
        }
    }

    public void clustering(DataAsset dataAsset) {

        initializeCenters(dataAsset);

        for (int loop = 0; loop < stopCondition; loop++) {
            //System.out.println("loop:" + loop);

            List<DataItemCluster> listOfDataItemClusters = new ArrayList<DataItemCluster>();
            List<DataItem> listOfDataItems = dataAsset.getListOfDataItems();

            for (int i = 0; i < listOfDataItems.size(); i++) {
                DataItem dataItem = listOfDataItems.get(i);
                int nearestClusterIndex = findNearestClusterIndex(dataItem);
                DataItemCluster dataItemCluster = new DataItemCluster(i, nearestClusterIndex);
                listOfDataItemClusters.add(dataItemCluster);
            }

            List<DataItem> listOfNewCenters = calculateNewCenters(listOfDataItems, listOfDataItemClusters);

            boolean coverged = isCenterConverged(listOfNewCenters);
            if (coverged) {
                extendRawDataWithEstimatedSpeed(listOfDataItems, listOfDataItemClusters);
                break;
            } else {
                updateNewCenters(listOfNewCenters);
            }

            if (loop == stopCondition - 1) {
                extendRawDataWithEstimatedSpeed(listOfDataItems, listOfDataItemClusters);
            }

        }

    }

    private void extendRawDataWithEstimatedSpeed(List<DataItem> listOfDataItems, List<DataItemCluster> listOfDataItemClusters) {

        for (int i = 0; i < listOfCenters.size(); i++) {
            double sumSpeed = 0;
            int objectCounter = 0;

            for (DataItemCluster dataItemCluster : listOfDataItemClusters) {

                if (dataItemCluster.getClusterIndex() == i) {
                    int dataItemIndex = dataItemCluster.getDataItemIndex();
                    DataItem dataItem = listOfDataItems.get(dataItemIndex);
                    List<DataAttribute> listOfDataItemAttributes = dataItem.getListOfAttributes();

                    DataAttribute dataAttributeL = listOfDataItemAttributes.get(speedIndex);
                    sumSpeed += Double.parseDouble(dataAttributeL.getAttributeValue());

                    objectCounter++;
                }

            }

            sumSpeed /= objectCounter;

            for (DataItemCluster dataItemCluster : listOfDataItemClusters) {

                if (dataItemCluster.getClusterIndex() == i) {
                    int dataItemIndex = dataItemCluster.getDataItemIndex();
                    DataItem dataItem = listOfDataItems.get(dataItemIndex);
                    List<DataAttribute> listOfDataItemAttributes = dataItem.getListOfAttributes();
                    DataAttribute estimatedSpeedAttribute = new DataAttribute("estimatedSpeed", String.valueOf(sumSpeed));
                    listOfDataItemAttributes.add(estimatedSpeedAttribute);
                }

            }

        }

    }

    private void updateNewCenters(List<DataItem> listOfNewCenters) {

        listOfCenters.clear();

        for (DataItem dataItemI : listOfNewCenters) {
            List<DataAttribute> listOfDataAttributesI = dataItemI.getListOfAttributes();

            List<DataAttribute> listOfDataAttributesCenter = new ArrayList<DataAttribute>();

            for (DataAttribute dataAttribute : listOfDataAttributesI) {
                DataAttribute dataAttributeCenter = new DataAttribute(dataAttribute.getAttributeName(), dataAttribute.getAttributeValue());
                listOfDataAttributesCenter.add(dataAttributeCenter);
            }

            DataItem centerI = new DataItem(listOfDataAttributesCenter);
            listOfCenters.add(centerI);

        }

    }

    private List<DataItem> calculateNewCenters(List<DataItem> listOfDataItems, List<DataItemCluster> listOfDataItemClusters) {

        List<DataItem> listOfNewCenters = new ArrayList<DataItem>();

        int noOfAttributes = listOfDataItems.get(0).getListOfAttributes().size();

        for (int i = 0; i < listOfCenters.size(); i++) {
            double[] sum = new double[noOfAttributes];
            int objectCounter = 0;

            for (int j = 0; j < noOfAttributes; j++) {
                sum[j] = 0;
            }

            for (DataItemCluster dataItemCluster : listOfDataItemClusters) {

                if (dataItemCluster.getClusterIndex() == i) {
                    int dataItemIndex = dataItemCluster.getDataItemIndex();
                    DataItem dataItem = listOfDataItems.get(dataItemIndex);
                    List<DataAttribute> listOfDataItemAttributes = dataItem.getListOfAttributes();

                    for (int l = 0; l < listOfDataItemAttributes.size(); l++) {
                        if (listOfAttributeIndice.contains(l)) {

                            DataAttribute dataAttributeL = listOfDataItemAttributes.get(l);
                            sum[l] += Double.parseDouble(dataAttributeL.getAttributeValue());
                        }
                    }

                    objectCounter++;
                }

            }

            List<DataAttribute> listOfDataAttributesForNewCenter = new ArrayList<DataAttribute>();

            for (int n = 0; n < sum.length; n++) {

                if (listOfAttributeIndice.contains(n)) {
                    double newVal = sum[n] / objectCounter;
                    DataAttribute dataAttributeNewCenter = new DataAttribute("", String.valueOf(newVal));
                    listOfDataAttributesForNewCenter.add(dataAttributeNewCenter);
                } else {
                    DataAttribute dataAttributeNewCenter = new DataAttribute("", "");
                    listOfDataAttributesForNewCenter.add(dataAttributeNewCenter);
                }

            }

            DataItem newCenter = new DataItem(listOfDataAttributesForNewCenter);
            listOfNewCenters.add(newCenter);
        }

        return listOfNewCenters;
    }

    private boolean isCenterConverged(List<DataItem> listOfNewCenters) {

        boolean converged = true;

        for (int i = 0; i < listOfCenters.size(); i++) {

            double diff = Math.sqrt(getDistanceBetweenInstances(listOfCenters.get(i), listOfNewCenters.get(i)));
            if (diff > epxilon) {
                converged = false;
                break;
            }
        }

        return converged;
    }

    private int findNearestClusterIndex(DataItem dataItem) {

        int nearestIndex = 0;
        double nearestDistance = getDistanceBetweenInstances(dataItem, listOfCenters.get(nearestIndex));

        for (int i = 1; i < listOfCenters.size(); i++) {
            double distance2centerI = getDistanceBetweenInstances(dataItem, listOfCenters.get(i));

            if (distance2centerI < nearestDistance) {
                nearestDistance = distance2centerI;
                nearestIndex = i;
            }

        }

        return nearestIndex;

    }

    public double getDistanceBetweenInstances(DataItem a, DataItem b) {

        List<DataAttribute> listOfAttributesA = a.getListOfAttributes();
        List<DataAttribute> listOfAttributesB = b.getListOfAttributes();

        double distance = 0;
        for (int i = 0; i < listOfAttributesA.size(); i++) {

            if (listOfAttributeIndice.contains(i)) {

                DataAttribute dataAttributeA = listOfAttributesA.get(i);
                DataAttribute dataAttributeB = listOfAttributesB.get(i);

                double difference = Double.parseDouble(dataAttributeA.getAttributeValue()) - Double.parseDouble(dataAttributeB.getAttributeValue());
                distance += Math.pow(difference, 2);
            }
        }

        return distance;
    }

    private void initializeCenters(DataAsset dataAsset) {

        listOfCenters = new ArrayList<DataItem>();
        List<DataItem> listOfDataItems = dataAsset.getListOfDataItems();

        for (int i = 0; i < k; i++) {
            DataItem dataItemI = listOfDataItems.get(i);
            List<DataAttribute> listOfDataAttributesI = dataItemI.getListOfAttributes();

            List<DataAttribute> listOfDataAttributesCenter = new ArrayList<DataAttribute>();

            for (DataAttribute dataAttribute : listOfDataAttributesI) {
                DataAttribute dataAttributeCenter = new DataAttribute(dataAttribute.getAttributeName(), dataAttribute.getAttributeValue());
                listOfDataAttributesCenter.add(dataAttributeCenter);
            }

            DataItem centerI = new DataItem(listOfDataAttributesCenter);
            listOfCenters.add(centerI);

        }

    }

}
