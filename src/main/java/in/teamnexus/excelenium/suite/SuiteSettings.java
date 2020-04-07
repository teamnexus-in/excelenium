package in.teamnexus.excelenium.suite;

import java.util.List;

public class SuiteSettings
{
    String name;
    String serverUrl;
    List<BrowserConfig> browsers;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getServerUrl()
    {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl)
    {
        this.serverUrl = serverUrl;
    }

    public List<BrowserConfig> getBrowsers()
    {
        return browsers;
    }

    public void setBrowsers(List<BrowserConfig> browsers)
    {
        this.browsers = browsers;
    }

}
