package at.ac.tuwien.dsg.depictool.util;





import at.ac.tuwien.dsg.common.deployment.DeployAction;
import at.ac.tuwien.dsg.common.deployment.DeploymentDescription;
import at.ac.tuwien.dsg.depictool.util.Configuration;

import generated.oasis.tosca.Definitions;
import generated.oasis.tosca.TArtifactReference;
import generated.oasis.tosca.TArtifactTemplate;
import generated.oasis.tosca.TDeploymentArtifact;
import generated.oasis.tosca.TNodeTemplate;
import generated.oasis.tosca.TServiceTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class SALSAConnector {

    String DEPIC_SERVICE_NAME;
    String DEPIC_TOPOLOGY_NAME;
    List<DeployAction> deployActions;
    String salsaDEPICEndpoint;
    String salsaRESTfulEndpoint;

    

    public SALSAConnector(DeploymentDescription deploymentDescription) {
        deployActions = deploymentDescription.getListOfDeployActions();
      
    }

    public SALSAConnector(DeploymentDescription deploymentDescription, String salsaRESTfulEndpoint) {
        deployActions = deploymentDescription.getListOfDeployActions();
        this.salsaRESTfulEndpoint = salsaRESTfulEndpoint;
    }
    
    public void config() {
        Configuration config = new Configuration();
        DEPIC_SERVICE_NAME = config.getConfig(DEPIC_SERVICE_NAME);
        DEPIC_TOPOLOGY_NAME = config.getConfig(DEPIC_TOPOLOGY_NAME);
        salsaDEPICEndpoint = config.getConfig(salsaDEPICEndpoint);
        salsaRESTfulEndpoint = config.getConfig(salsaRESTfulEndpoint);
        
    }

    public String newServicesInstance() {
        String url = salsaRESTfulEndpoint + "/services/xml";
        String toscaString = this.toToscaString();
        String serviceId = sendRESTfulRequest(url, HTTPVerb.PUT, toscaString);
        updateListWithDeploymentEndpoint();
        return serviceId;
    }

    private void updateListWithDeploymentEndpoint() {
        for (DeployAction action : this.deployActions) {
            action.setDeploymentEndpoint(salsaDEPICEndpoint + action.getActionID() + "/instance-count/1");
        }
    }

    // this will accept only war file
    public String toToscaString() {
        Definitions toscaDef = new Definitions();
        toscaDef.setId(DEPIC_SERVICE_NAME);
        TServiceTemplate toscaServiceTemplate = new TServiceTemplate();
        toscaServiceTemplate.setId(DEPIC_TOPOLOGY_NAME);
        toscaDef.getServiceTemplateOrNodeTypeOrNodeTypeImplementation().add(toscaServiceTemplate);
        for (DeployAction action : this.deployActions) {
			//System.out.println("Generating node for action: " + action.getActionID() + "/" + action.getActionName());
            // create an artifact reference
            TArtifactTemplate artTemplate = new TArtifactTemplate();
            artTemplate.setId(action.getActionID() + "_artifact");
            artTemplate.setName(action.getActionName() + "_artifact");
            artTemplate.setType(new QName(action.getArtifactType()));
            TArtifactReference artRef = new TArtifactReference();
            artRef.setReference(action.getArtifact());
            artTemplate.getArtifactReferences().getArtifactReference().add(artRef);
            toscaDef.getServiceTemplateOrNodeTypeOrNodeTypeImplementation().add(artTemplate);

            TNodeTemplate toscaNode = new TNodeTemplate();
            TDeploymentArtifact nodeDeploymentArtifact = new TDeploymentArtifact();
            nodeDeploymentArtifact.setArtifactType(new QName(action.getArtifactType()));
            nodeDeploymentArtifact.setArtifactRef(new QName(artTemplate.getId()));
            toscaNode.getDeploymentArtifacts().getDeploymentArtifact().add(nodeDeploymentArtifact);
            toscaNode.setType(new QName("war"));
            toscaNode.setId(action.getActionID());
            toscaNode.setName(action.getActionName());
            toscaNode.setMinInstances(0);
            toscaNode.setMaxInstances("1");
            toscaServiceTemplate.getTopologyTemplate().getNodeTemplateOrRelationshipTemplate().add(toscaNode);
        }

        try {
            StringWriter objWriter = new StringWriter();

            JAXBContext jaxbContext = JAXBContext.newInstance(Definitions.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(toscaDef, objWriter);

            // jaxbMarshaller.marshal(eState, System.out);
            String toscaString = objWriter.toString();

            return toscaString;
        } catch (JAXBException e) {
            e.printStackTrace();
            return "Error when generating TOSCA description";
        }
    }

    public enum HTTPVerb {

        GET, POST, PUT, DELETE
    }

    public String sendRESTfulRequest(String url, HTTPVerb method, String data) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            //HttpGet method = new HttpGet(url);
            StringEntity inputKeyspace = new StringEntity(data);

            HttpResponse methodResponse = null;
            switch (method) {
                case GET: {
                    HttpGet request = new HttpGet(url);
                    methodResponse = httpClient.execute(request);
                    break;
                }
                case POST: {
                    HttpPost request = new HttpPost(url);
                    request.addHeader("content-type", "application/xml; charset=utf-8");
                    request.addHeader("Accept", "application/xml, multipart/related");
                    request.setEntity(inputKeyspace);
                    methodResponse = httpClient.execute(request);
                    break;
                }
                case PUT: {
                    HttpPut request = new HttpPut(url);
                    request.addHeader("content-type", "application/xml; charset=utf-8");
                    request.addHeader("Accept", "application/xml, multipart/related");
                    request.setEntity(inputKeyspace);
                    methodResponse = httpClient.execute(request);
                    break;
                }
                case DELETE: {
                    HttpDelete request = new HttpDelete(url);
                    request.addHeader("content-type", "application/xml; charset=utf-8");
                    request.addHeader("Accept", "application/xml, multipart/related");
                    methodResponse = httpClient.execute(request);
                    break;
                }
            }

            if (methodResponse == null) {
                return "Error when sending RESTful service to: " + url;
            }
            int statusCode = methodResponse.getStatusLine().getStatusCode();

            //  System.out.println("Status Code: " + statusCode);
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(methodResponse.getEntity().getContent()));

            StringBuilder result = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
            // System.out.println("Response String: " + result.toString());
        } catch (Exception ex) {
            System.out.println("Error when processing RESTful request: " + ex.toString());
        }
        return "Unknown error";

    }
}
