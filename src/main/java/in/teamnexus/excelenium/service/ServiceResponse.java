/**
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
    List<String> messages;

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
    public List<String> getMessages()
    {
        return messages;
    }

    /**
     * @param messages the messages to set
     */
    public void setMessages(List<String> messages)
    {
        this.messages = messages;
    }
    
    public void addMessage(String message)
    {
        if(messages == null)
        {
            messages = new ArrayList<>();
        }
        messages.add(message);
    }

}
