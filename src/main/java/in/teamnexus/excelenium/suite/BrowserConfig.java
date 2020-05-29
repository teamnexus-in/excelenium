package in.teamnexus.excelenium.suite;

import java.io.File;

import in.teamnexus.excelenium.service.ServiceResponse;
import in.teamnexus.excelenium.service.ValidationMessage;

/**
 * Defines the configuration of the browser to be used in the test - enabled, the driver path etc
 */
public class BrowserConfig
{
    
    /** The name. */
    String name;
    
    /** The enabled. */
    boolean enabled;
    
    /** Absolute path to the web driver executable of the browser */
    String driverPath;

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
     * Checks if is enabled.
     *
     * @return true, if is enabled
     */
    public boolean isEnabled()
    {
        return enabled;
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
     * Gets the driver path.
     *
     * @return the driver path
     */
    public String getDriverPath()
    {
        return driverPath;
    }

    /**
     * Sets the driver path.
     *
     * @param driverPath the new driver path
     */
    public void setDriverPath(String driverPath)
    {
        this.driverPath = driverPath;
    }

    public void validate(ServiceResponse response)
    {
        if(isEnabled())
        {
            File file = new File(driverPath);
            if(!file.exists())
            {
                response.setStatus(ServiceResponse.STATUS_FAILURE);
                response.addMessage(ValidationMessage.TYPE_ERROR, String.format("%s %s %s - %s - %s ", "Browser driver path for", name, "is invalid", driverPath, "does not exist"));
            }
        }
    }

}
