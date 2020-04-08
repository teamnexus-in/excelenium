package in.teamnexus.excelenium.service;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.teamnexus.excelenium.suite.SuiteConfig;
import in.teamnexus.excelenium.suite.exception.ConfigException;
import in.teamnexus.excelenium.suite.executor.ScriptExecutor;

public class ExceleniumSuiteService implements SuiteService
{
    private HashMap<String, SuiteConfig> suiteMap = new HashMap<>();  
    private ScriptExecutor executor;
    
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void runSuite(String suiteName) throws ConfigException
    {
        SuiteConfig config = suiteMap.get(suiteName);
        this.executor.setSuiteConfig(config);
        this.executor.executeAutomation();

    }


    @Override
    public void saveConfig(SuiteConfig config)
    {
        suiteMap.put(config.getName(), config);
        logger.debug(suiteMap.toString());
    }

    public ScriptExecutor getExecutor()
    {
        return executor;
    }
    
    public void setExecutor(ScriptExecutor executor)
    {
        this.executor = executor;
    }
}
