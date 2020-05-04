package in.teamnexus.excelenium.service;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.teamnexus.excelenium.suite.SuiteConfig;
import in.teamnexus.excelenium.suite.exception.ConfigException;
import in.teamnexus.excelenium.suite.executor.ScriptExecutor;

// TODO: Auto-generated Javadoc
/**
 * The Class ExceleniumSuiteService.
 */
public class ExceleniumSuiteService implements SuiteService
{
    
    /** The suite map. */
    private HashMap<String, SuiteConfig> suiteMap = new HashMap<>();  
    
    /** The executor. */
    private ScriptExecutor executor;
    
    /** The logger. */
    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Run suite.
     *
     * @param suiteName the suite name
     * @throws ConfigException the config exception
     */
    @Override
    public void runSuite(String suiteName) throws ConfigException
    {
        SuiteConfig config = suiteMap.get(suiteName);
        this.executor.setSuiteConfig(config);
        this.executor.executeAutomation();

    }


    /**
     * Save config.
     *
     * @param config the config
     */
    @Override
    public void saveConfig(SuiteConfig config)
    {
        suiteMap.put(config.getName(), config);
        logger.debug(suiteMap.toString());
    }

    /**
     * Gets the executor.
     *
     * @return the executor
     */
    public ScriptExecutor getExecutor()
    {
        return executor;
    }
    
    /**
     * Sets the executor.
     *
     * @param executor the new executor
     */
    public void setExecutor(ScriptExecutor executor)
    {
        this.executor = executor;
    }
}
