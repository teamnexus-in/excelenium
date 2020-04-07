package in.teamnexus.excelenium.suite;

public class BrowserConfig
{
    String name;
    boolean enabled;
    String driverPath;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public String getDriverPath()
    {
        return driverPath;
    }

    public void setDriverPath(String driverPath)
    {
        this.driverPath = driverPath;
    }

}
