/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.qoranalytics.connector;

/**
 *
 * @author Jun
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import at.ac.tuwien.dsg.smartcom.adapter.exception.AdapterException;
import at.ac.tuwien.dsg.smartcom.adapters.RESTOutputAdapter;
import at.ac.tuwien.dsg.smartcom.adapters.rest.ObjectMapperProvider;
import at.ac.tuwien.dsg.smartcom.model.Identifier;
import at.ac.tuwien.dsg.smartcom.model.Message;
import at.ac.tuwien.dsg.smartcom.model.PeerChannelAddress;
//import at.ac.tuwien.dsg.smartcom.adapters.rest.TestSynchronizer;
//import at.ac.tuwien.dsg.smartcom.adapters.rest.RESTResource;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author dsg
 */
public class SmartComConnector {
    


    private HttpServer server;
    private ExecutorService executor;

    @Before
    public void setUp() throws Exception {
        final ResourceConfig application = new ResourceConfig(
                TestRESTResource.class,
                ObjectMapperProvider.class,
                JacksonFeature.class
        );

        server = GrizzlyHttpServerFactory.createHttpServer(URI.create("http://localhost:8080/"), application);
        server.start();

        executor = Executors.newFixedThreadPool(5);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
        executor.shutdown();
    }

    @Test(timeout = 10000l)
    public void testRESTAdapter(String testId, String testContent, String testType, String testSubType,
            String sender, String receiver, String conversationId, long ttl, String testLanguage, String securityToken) throws Exception {
        TestSynchronizer.initSynchronizer(20);       //for thread related service

        final Message message = new Message.MessageBuilder()
                .setId(Identifier.message(testId))
                .setContent(testContent)
                .setType(testType)
                .setSubtype(testSubType)
                .setSenderId(Identifier.peer(sender))
                .setReceiverId(Identifier.peer(receiver))
                .setConversationId(conversationId)
                .setTtl(ttl)
                .setLanguage(testLanguage)
                .setSecurityToken(securityToken)
                .create();

        final RESTOutputAdapter adapter = new RESTOutputAdapter();
        final List<Serializable> list = new ArrayList<>();
        list.add("http://localhost:8080/message/");               //here "/message" is the same name provided in @Path 

        for (int i = 0; i < 20; i++) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        adapter.push(message, new PeerChannelAddress(Identifier.peer("peer"), Identifier.adapter("adapter"), list));
                    } catch (AdapterException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        TestSynchronizer.await();
    }
    //////////change/////////
    public static void main(String []p)
    {
        System.out.println("run the rest output");
        SmartComConnector test=new SmartComConnector();
        String testId="messageId"; //unique identifier of message
        String testcontent="messagecontent";  //unique identifier of message content
        String testType="control message";      //the type of message
        String testSubType="Analytic";          //component specific
        String sender="senderId";               //unique identifier of sender
        String receiver="receiverId";           //unique identifier of receiver
        String conversationId="conversationId"; //OPTIONAL  //If any two process run parallaly then they communicate with each other using this id
        long ttl=3;                             //OPTIONAL  //the time duration to monitor the message
        String testLanguage="English";          //OPTIONAL //the language of the message
        String securityToken="SecurityToken";   //OPTIONAL //information about the authenticity of the message
        
        try
        {
        test.setUp();
        test.testRESTAdapter(testId, testcontent, testType,testSubType, sender, receiver, conversationId, ttl, testLanguage, securityToken);
        test.tearDown();
        }
        catch(Exception e)
        {
            
        }
    }
}

