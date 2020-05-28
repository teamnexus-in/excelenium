package in.teamnexus.excelenium.suite;

import java.util.List;

import in.teamnexus.excelenium.service.ServiceResponse;
import in.teamnexus.excelenium.suite.script.Script;

/**
 * The Class that holds the suite information including scripts and settings
 */
public class SuiteConfig
{
    
    /** The name. */
    String name;
    
    /** The settings. */
    SuiteSettings settings;
    
    /** The scripts. */
    List<Script> scripts;
    
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
     * Gets the settings.
     *
     * @return the settings
     */
    public SuiteSettings getSettings()
    {
        return settings;
    }

    /**
     * Sets the settings.
     *
     * @param settings the new settings
     */
    public void setSettings(SuiteSettings settings)
    {
        this.settings = settings;
    }

    /**
     * Gets the scripts.
     *
     * @return the scripts
     */
    public List<Script> getScripts()
    {
        return scripts;
    }

    /**
     * Sets the scripts.
     *
     * @param scripts the new scripts
     */
    public void setScripts(List<Script> scripts)
    {
        this.scripts = scripts;
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

    public ServiceResponse validate()
    {
        ServiceResponse response = new ServiceResponse();
        response.setStatus(ServiceResponse.STATUS_SUCCESS);
        
        if(name == null || name.isEmpty())
        {
           response.setStatus(ServiceResponse.STATUS_FAILURE);
        }
        
        settings.validate(response);
        
        userAgent.validate(response);
        
        for (Script script : scripts)
        {
            script.validate(response);
        }
        return response;
    }

}
