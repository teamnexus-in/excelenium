/*
 * Copyright (C) 2017 - Present. NEXUS. All rights reserved.
 * 
 * https://teamnexus.in
 * 
 * This software is released under the Apache 2.0 License.
 * 
 * 
 */
package in.teamnexus.excelenium.service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Prabhu
 *
 */
public class ServiceResponse
{
    public static final int STATUS_FAILURE = -1;
    public static final int STATUS_SUCCESS = 0;
    
    int status;
    String response;
    List<ValidationMessage> messages;

    /**
     * @return the status
     */
    public int getStatus()
    {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status)
    {
        this.status = status;
    }

    /**
     * @return the response
     */
    public String getResponse()
    {
        return response;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(String response)
    {
        this.response = response;
    }

    /**
     * @return the messages
     */
    public List<ValidationMessage> getMessages()
    {
        return messages;
    }

    /**
     * @param messages the messages to set
     */
    public void setMessages(List<ValidationMessage> messages)
    {
        this.messages = messages;
    }
    
    public void addMessage(int type, String message)
    {
        if(messages == null)
        {
            messages = new ArrayList<>();
        }
        messages.add(new ValidationMessage(type, message));
    }

}
