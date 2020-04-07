package in.teamnexus.excelenium.suite;

import java.util.List;

import in.teamnexus.excelenium.suite.script.Script;

public class SuiteConfig
{
    String name;
    SuiteSettings settings;
    List<Script> scripts;
    boolean isRunConcurrent;
    UserAgentConfig userAgent;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public SuiteSettings getSettings()
    {
        return settings;
    }

    public void setSettings(SuiteSettings settings)
    {
        this.settings = settings;
    }

    public List<Script> getScripts()
    {
        return scripts;
    }

    public void setScripts(List<Script> scripts)
    {
        this.scripts = scripts;
    }

    public boolean isRunConcurrent()
    {
        return isRunConcurrent;
    }

    public void setRunConcurrent(boolean isRunConcurrent)
    {
        this.isRunConcurrent = isRunConcurrent;
    }

    public UserAgentConfig getUserAgent()
    {
        return userAgent;
    }

    public void setUserAgent(UserAgentConfig userAgent)
    {
        this.userAgent = userAgent;
    }

}
