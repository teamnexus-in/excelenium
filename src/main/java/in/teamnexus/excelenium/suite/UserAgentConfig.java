package in.teamnexus.excelenium.suite;

import in.teamnexus.excelenium.service.ServiceResponse;
import in.teamnexus.excelenium.service.ValidationMessage;

/**
 * The Class that holds the information about User Agent settings that has to be applied to the browser to spoof client like mobile devices etc
 */
public class UserAgentConfig
{
    
    /** The enabled. */
    boolean enabled;
    
    /** The user agent. */
    String userAgent;

    /**
     * Checks if is enabled.
     *
     * @return true, if is enabled
     */
    public boolean isEnabled()
    {
        return this.enabled;
    }

    /**
     * Sets the enabled.
     *
     * @param enabled the new enabled
     */
    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    /**
     * Gets the user agent.
     *
     * @return the user agent
     */
    public String getUserAgent()
    {
        return this.userAgent;
    }

    /**
     * Sets the user agent.
     *
     * @param userAgent the new user agent
     */
    public void setUserAgent(String userAgent)
    {
        this.userAgent = userAgent;
    }

    public void validate(ServiceResponse response)
    {
        if(isEnabled())
        {
            if(this.userAgent == null || this.userAgent.isEmpty())
            {
                response.setStatus(ServiceResponse.STATUS_FAILURE);
                response.addMessage(ValidationMessage.TYPE_ERROR, "Custom user agent has been enabled but user-agent is empty");
            }
        }
        
    }
}
