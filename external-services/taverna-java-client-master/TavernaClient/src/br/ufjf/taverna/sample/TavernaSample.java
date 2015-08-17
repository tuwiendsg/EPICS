/*
 * The MIT License
 *
 * Copyright 2014 Pós-Graduação em Ciência da Computação UFJF.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package br.ufjf.taverna.sample;

import br.ufjf.taverna.core.TavernaClient;
import br.ufjf.taverna.model.input.TavernaInput;
import br.ufjf.taverna.model.output.TavernaOutput;
import br.ufjf.taverna.model.run.TavernaRun;
import java.util.ArrayList;


/**
 *
 * @author vitorfs
 */
public class TavernaSample {
    
    public static void main(String[] args) {
        
        TavernaClient client = new TavernaClient();
        
        //client.setBaseUri("http://localhost:8080/TavernaServer-2.5.4/rest");
        client.setBaseUri("http://128.130.172.214:8080/TavernaServer-2.5.4/rest");
        client.setAuthorization("taverna", "taverna");

        try {
            
            String uuid = "266561b2-d56d-428c-a814-328dae5cbd61";
            String status = "";
            
       
            uuid = client.create("/Volumes/DATA/Temp/daf_gps_1.t2flow");
            System.out.println(uuid);
            
            
//            
//            ArrayList<TavernaRun> runs = client.getRuns();
//            for (TavernaRun run : runs) {
//                //client.destroy(run.getUuid());
//                System.out.println("Run: " + run.getUuid());
//            }
            
//            status = client.getStatus(uuid);
//            System.out.println(status);
            
            
  //          ArrayList<TavernaInput> inputs = client.getExpectedInputs(uuid);
//            for (TavernaInput input : inputs) {
//                if (input != null) {
//                    System.out.println("Required Inputs: " + input.getName());
//                }
//            }
            
//            System.out.println("inputing data");
//            //System.out.println(client.setInputValue(uuid, "id", "Jun"));
       //     client.setInputValue(uuid, "id", "hello");
            
            
           client.start(uuid);

            do {
                status = client.getStatus(uuid);
                System.out.println(status);
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
            } while (!"Finished".equals(status));
            
            
            
            ArrayList<TavernaOutput> tavernaOutput = client.getOutput(uuid);
            for (TavernaOutput output : tavernaOutput) {
                System.out.println(output.getName());
            }
            
            
            client.destroy(uuid);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
}
