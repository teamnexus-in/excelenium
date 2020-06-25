/*
 * Copyright (C) 2017 - Present. NEXUS. All rights reserved.
 * 
 * https://teamnexus.in
 * 
 * This software is released under the Apache 2.0 License.
 * 
 * 
 */
package in.teamnexus.excelenium.suite;

import java.util.List;

import in.teamnexus.excelenium.service.ServiceResponse;
import in.teamnexus.excelenium.service.ValidationMessage;

/**
 * The Class that holds the Suite Settings - suite name, serverUrl and the browser configs.
 */
public class SuiteSettings
{

    /** The name. */
    String name;

    /** The server url. */
    String serverUrl;

    /** The browsers. */
    List<BrowserConfig> browsers;

    /** The is run concurrent. */
    boolean isRunConcurrent;

    /** The user agent. */
    UserAgentConfig userAgent;

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Gets the server url.
     *
     * @return the server url
     */
    public String getServerUrl()
    {
        return serverUrl;
    }

    /**
     * Sets the server url.
     *
     * @param serverUrl the new server url
     */
    public void setServerUrl(String serverUrl)
    {
        this.serverUrl = serverUrl;
    }

    /**
     * Gets the browsers.
     *
     * @return the browsers
     */
    public List<BrowserConfig> getBrowsers()
    {
        return browsers;
    }

    /**
     * Sets the browsers.
     *
     * @param browsers the new browsers
     */
    public void setBrowsers(List<BrowserConfig> browsers)
    {
        this.browsers = browsers;
    }

    /**
     * Checks if is run concurrent.
     *
     * @return true, if is run concurrent
     */
    public boolean isRunConcurrent()
    {
        return isRunConcurrent;
    }

    /**
     * Sets the run concurrent.
     *
     * @param isRunConcurrent the new run concurrent
     */
    public void setRunConcurrent(boolean isRunConcurrent)
    {
        this.isRunConcurrent = isRunConcurrent;
    }

    /**
     * Gets the user agent.
     *
     * @return the user agent
     */
    public UserAgentConfig getUserAgent()
    {
        return userAgent;
    }

    /**
     * Sets the user agent.
     *
     * @param userAgent the new user agent
     */
    public void setUserAgent(UserAgentConfig userAgent)
    {
        this.userAgent = userAgent;
    }

    public void validate(ServiceResponse response)
    {
        if (this.name == null || this.name.isEmpty())
        {
            response.setStatus(ServiceResponse.STATUS_FAILURE);
            response.addMessage(ValidationMessage.TYPE_ERROR, "Suite name is empty");
        }

        if (this.serverUrl == null || this.serverUrl.isEmpty())
        {
            response.setStatus(ServiceResponse.STATUS_FAILURE);
            response.addMessage(ValidationMessage.TYPE_ERROR, "Server Url is empty. Please enter a valid url");
        }
        
        for (BrowserConfig browserConfig : this.browsers)
        {
            browserConfig.validate(response);
        }
        
        userAgent.validate(response);

    }

}
