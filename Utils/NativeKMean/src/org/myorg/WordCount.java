/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.myorg;

/**
 *
 * @author junnguyen //
 */
import java.io.*;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
//import org.apache.hadoop.mapreduce.lib.input.NLineInputFormat;
import org.apache.hadoop.mapred.lib.NLineInputFormat;
import org.apache.hadoop.util.*;

import weka.core.*;
import weka.clusterers.*;

import weka.filters.Filter;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;

public class WordCount extends Configured implements Tool {

    public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {

        static enum Counters {

            INPUT_WORDS
        }
        private boolean caseSensitive = true;
        private Set<String> patternsToSkip = new HashSet<String>();
        private long numRecords = 0;
        private String inputFile;
        String centersStr;

        public void configure(JobConf job) {
            
             Utilities util = new Utilities();
             
             util.printLogData("CHECK A");
            
            caseSensitive = job.getBoolean("wordcount.case.sensitive", true);
            
            util.printLogData("CHECK B");
            
            
            inputFile = job.get("map.input.file");
            
            util.printLogData("CHECK C");
            
            centersStr = "";
            centersStr = job.get("centersString");
            
            
            util.printLogData("CHECK D");

            job.set("OK", "IT WORKS");
            if (job.getBoolean("wordcount.skip.patterns", false)) {
                Path[] patternsFiles = new Path[0];
                try {
                    patternsFiles = DistributedCache.getLocalCacheFiles(job);
                } catch (IOException ioe) {
                    System.err.println("Caught exception while getting cached files: " + StringUtils.stringifyException(ioe));
                }
                for (Path patternsFile : patternsFiles) {
                    parseSkipFile(patternsFile);
                }
            }
        }

        // READ DATA line by line from data source
        private void parseSkipFile(Path patternsFile) {
            try {
                BufferedReader fis = new BufferedReader(new FileReader(patternsFile.toString()));
                String pattern = null;
                while ((pattern = fis.readLine()) != null) {
                    patternsToSkip.add(pattern);
                }
            } catch (IOException ioe) {
                System.err.println("Caught exception while parsing the cached file '" + patternsFile + "' : " + StringUtils.stringifyException(ioe));
            }
        }

        public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {

            // get each line from data for processing -> to lowercase  
            Utilities util = new Utilities();
            String line = (caseSensitive) ? value.toString() : value.toString().toLowerCase();
         //   util.printLogData2(line);


            String[] atts = line.split(",");
            int numOfAttributes = atts.length;

            Utilities utility = new Utilities();






            double[] d_atts = new double[numOfAttributes];

            for (int i = 0; i < numOfAttributes; i++) {
                d_atts[i] = Double.parseDouble(atts[i]);

            }

            Instance newDataInstance = new DenseInstance(1.0, d_atts);


            //  utility.printLogData3("Max: "+AppConst.InterationNumber);


            int nearestClusterIndex = utility.findNearestClusterIndex(newDataInstance, centersStr);




            Text outputValue = new Text(line);
            Text outputKey = new Text(String.valueOf(nearestClusterIndex));
            output.collect(outputKey, outputValue);




            for (String pattern : patternsToSkip) {
                line = line.replaceAll(pattern, "");
            }

            if ((++numRecords % 100) == 0) {
                reporter.setStatus("Finished processing " + numRecords + " records " + "from the input file: " + inputFile);
            }




        }
    }

    public static class Combine extends MapReduceBase implements Reducer<Text, Text, Text, Text> {

        public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {



            int num = 0;


            double[] subSum = null;
            if (values.hasNext()) {

                Text value = values.next();
                String line = value.toString();
                String[] atts = line.split(",");
                int numOfAttributes = atts.length;

                subSum = new double[numOfAttributes];

                for (int i = 0; i < numOfAttributes; i++) {
                    subSum[i] = Double.parseDouble(atts[i]);

                }
                num++;

                while (values.hasNext()) {

                    value = values.next();
                    line = value.toString();
                    atts = line.split(",");

                    subSum = new double[numOfAttributes];

                    for (int i = 0; i < numOfAttributes; i++) {
                        subSum[i] += Double.parseDouble(atts[i]);

                    }


                    num++;


                }

            }

            String outputStr = String.valueOf(num) + ";";

            for (int i = 0; i < subSum.length; i++) {
                if (i != subSum.length - 1) {
                    outputStr += String.valueOf(subSum[i]) + ",";
                } else {
                    outputStr += String.valueOf(subSum[i]);
                }
            }


            Text outputValue = new Text(outputStr);






            output.collect(key, outputValue);

        }
    }

    public static class Reduce extends MapReduceBase implements Reducer<Text, Text, Text, Text> {

        String centersStr;

        public void configure(JobConf job) {

            centersStr = "";
            centersStr = job.get("centersString");


        }

        public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {



            //REDUCE PHASE - Update New  Centers
            int num = 0;

            double[] subSum = null;
            if (values.hasNext()) {

                Text value = values.next();
                String line = value.toString();

                String[] rawData = line.split(";");

                int numOfObjects = Integer.parseInt(rawData[0]);
                String[] atts = rawData[1].split(",");
                int numOfAttributes = atts.length;

                subSum = new double[numOfAttributes];

                for (int i = 0; i < numOfAttributes; i++) {
                    subSum[i] = Double.parseDouble(atts[i]);

                }
                num += numOfObjects;

                while (values.hasNext()) {

                    value = values.next();
                    line = value.toString();

                    rawData = line.split(";");

                    numOfObjects = Integer.parseInt(rawData[0]);
                    atts = rawData[1].split(",");


                    subSum = new double[numOfAttributes];

                    for (int i = 0; i < numOfAttributes; i++) {
                        subSum[i] += Double.parseDouble(atts[i]);

                    }


                    num += numOfObjects;


                }

            }



            for (int i = 0; i < subSum.length; i++) {
                subSum[i] /= num;


            }

            Instance newCenterI = new DenseInstance(1.0, subSum);

            String outputStr = "";
            for (int i = 0; i < newCenterI.numAttributes(); i++) {

                if (i != newCenterI.numAttributes() - 1) {
                    outputStr = outputStr + newCenterI.value(i) + ";";
                } else {
                    outputStr = outputStr + newCenterI.value(i);
                }
            }


            Text outputValue = new Text(outputStr);
            output.collect(key, outputValue);

        }
    }

    public int run(String[] args) throws Exception {


        AppConst.initializeCenters();
        Utilities util = new Utilities();

        String centers = "";

        for (int i = 0; i < AppConst.Centers.size(); i++) {
            Instance cI = AppConst.Centers.get(i);

            for (int j = 0; j < cI.numAttributes(); j++) {

                if (j != cI.numAttributes() - 1) {
                    centers = centers + cI.value(j) + ",";
                } else {
                    centers += cI.value(j);
                }


            }

            if (i != (AppConst.Centers.size() - 1)) {
                centers += ";";
            }
        }

        util.printLogData("Check Data: " + centers);


        for (int z = 0; z < AppConst.m_MaxIterations; z++) {

            util.printLogData("LOOP " + z);

            JobConf conf = new JobConf(getConf(), WordCount.class);
            conf.setJobName("wordcount");


            conf.set("centersString", centers);
            conf.setOutputKeyClass(Text.class);
            conf.setOutputValueClass(Text.class);

            conf.setMapperClass(Map.class);
            conf.setCombinerClass(Combine.class);
            conf.setReducerClass(Reduce.class);


            //   conf.setInt("mapred.line.input.format.linespermap", 4);

            conf.setInputFormat(TextInputFormat.class);
            //  conf.setInputFormat(NLineInputFormat.class);

            conf.setOutputFormat(TextOutputFormat.class);


            List<String> other_args = new ArrayList<String>();
            for (int i = 0; i < args.length; ++i) {
                if ("-skip".equals(args[i])) {
                    DistributedCache.addCacheFile(new Path(args[++i]).toUri(), conf);
                    conf.setBoolean("wordcount.skip.patterns", true);
                } else {
                    other_args.add(args[i]);
                }
            }

            //set path for input and output here

            FileInputFormat.setInputPaths(conf, new Path(other_args.get(0)));
            FileOutputFormat.setOutputPath(conf, new Path(other_args.get(1) + z));



            JobClient.runJob(conf);



            ///////// READ FROM HDFS



            FileSystem fs = FileSystem.get(conf);
            FileStatus[] files = fs.globStatus(new Path(other_args.get(1) + z + "/part-*"));
            for (FileStatus file : files) {
                if (file.getLen() > 0) {
                    FSDataInputStream in = fs.open(file.getPath());
                    BufferedReader bin = new BufferedReader(new InputStreamReader(
                            in));
                    String s = bin.readLine();
                    while (s != null) {


                        System.out.println("---------------------");
                        System.out.println(s);

                        s = s.replaceAll("\\s+", " ");
                        String[] strs = s.split(" ");

                        for (int k = 0; k < strs.length; k++) {
                            System.out.println(strs[k]);
                        }

                        int clusterIndexI = Integer.parseInt(strs[0]);

                        String[] atts = strs[1].split(";");
                        double[] d_atts = new double[atts.length];

                        for (int a = 0; a < atts.length; a++) {
                            d_atts[a] = Double.parseDouble(atts[a]);
                        }

                        Instance i_center = new DenseInstance(1.0, d_atts);

                        AppConst.newCenters.set(clusterIndexI, i_center);
                        s = bin.readLine();
                    }
                    in.close();
                }
            }





            ////// END READ FROM HDFS




            if (util.isCenterConverged()) {
                //  break;
            } else {
                util.copyCenters();

                for (int i = 0; i < AppConst.newCenters.size(); i++) {

                    Instance centerI = AppConst.newCenters.get(i);

                    for (int j = 0; j < centerI.numAttributes(); j++) {
                        util.printLogData("CENTER: " + centerI.value(j));
                    }
                }

                util.printLogData("----------------------"); ////////////////////////

            }




        }




        return 0;
    }

    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new Configuration(), new WordCount(), args);
        System.exit(res);
    }
}
