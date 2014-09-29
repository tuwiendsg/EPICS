/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.qoranalytics.streamprocessing.entity.event;

import at.ac.tuwien.dsg.smartcom.model.Identifier;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */

@XmlRootElement(name = "EventMessage")
@XmlAccessorType(XmlAccessType.FIELD)
public class EventMessage {
    
    
    @XmlElement(name = "id", required = true)
    private String id;
    
    @XmlElement(name = "content", required = true)
    private String content;
    
    @XmlElement(name = "type", required = true)
    private String type;
    
    @XmlElement(name = "subtype", required = true)
    private String subtype;
    
    @XmlElement(name = "senderId", required = true)
    private String senderId;
    
    @XmlElement(name = "receiverId", required = true)
    private String receiverId;
    
    @XmlElement(name = "conversationId", required = true)
    private String conversationId;
    
    @XmlElement(name = "ttl", required = true)
    private long ttl;
    
    @XmlElement(name = "language", required = true)
    private String language;
    
    @XmlElement(name = "securityToken", required = true)
    private String securityToken;

    public EventMessage() {
    }

    public EventMessage(String id, String content, String type, String subtype, String senderId, String receiverId, String conversationId, long ttl, String language, String securityToken) {
        this.id = id;
        this.content = content;
        this.type = type;
        this.subtype = subtype;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.conversationId = conversationId;
        this.ttl = ttl;
        this.language = language;
        this.securityToken = securityToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }
   
    
}
