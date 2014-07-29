/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.rest;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 *
 * @author Jun
 */



    public  class QualityEvaluatorREST {

        private WebTarget webTarget;
        private Client client;
        private static final String BASE_URI = "http://localhost:8080/QualityEvaluator/webresources";

        public QualityEvaluatorREST() {
            client = javax.ws.rs.client.ClientBuilder.newClient();
            webTarget = client.target(BASE_URI).path("QualityEvaluator");
        }

        public String getXml() throws ClientErrorException {
            WebTarget resource = webTarget;
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(String.class);
        }

        public String putXml(Object requestEntity) throws ClientErrorException {
            return webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), String.class);
        }

        public void close() {
            client.close();
        }
    }
    

    
    


    
    
    
    
