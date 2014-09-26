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
//import at.ac.tuwien.dsg.smartcom.adapters.DropboxInputAdapter;
import at.ac.tuwien.dsg.smartcom.adapters.DropboxOutputAdapter;
import at.ac.tuwien.dsg.smartcom.model.Identifier;
import at.ac.tuwien.dsg.smartcom.model.Message;
import at.ac.tuwien.dsg.smartcom.model.PeerChannelAddress;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dsg
 */
/**
 * This test demonstrates the behaviour of the dropbox input/output adapter.
 * Note that this is not a JUnit test and can only be run by invoking the main
 * method.
 *
 * The test requires the user to add an access token of a dropbox account. This
 * can be generated by creating an app on <a
 * href='https://www.dropbox.com/developers/apps'>https://www.dropbox.com/developers/apps</a>
 * and by clicking the 'Generate' button below the 'Generate access token'
 * headline.
 *
 * The output adapter will create a new file 'task_[TIMESTAMP].task' in the
 * folder 'smartcom' of the linked Dropbox account. Afterwards the input adapter
 * will start looking for the file and will report its existence.
 */
public class SmartComConnector {

    
    
    public void sendMessage(Message message) throws Exception {

        List<Serializable> parameters = new ArrayList<>(2);
        String code="j1a_4Gu4eRAAAAAAAAAAQrWUlBUAlScSNfVNcfiEn1oDtEh-Bf-MMT4fbUDd2e1d";
        parameters.add(code);
        parameters.add("smartcom");     //the folder name inside of dropbox 
        PeerChannelAddress address = new PeerChannelAddress(Identifier.peer("test"), Identifier.adapter("Dropbox"), parameters);

        System.out.println("Output Adapter: Connecting...");
        DropboxOutputAdapter adapter = new DropboxOutputAdapter(address);
        adapter.push(message, null);
        System.out.println("File uploaded!");
        
    }

    }
