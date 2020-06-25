/*
 * Copyright (C) 2017 - Present. NEXUS. All rights reserved.
 * 
 * https://teamnexus.in
 * 
 * This software is released under the Apache 2.0 License.
 * 
 * 
 */
package in.teamnexus.excelenium.service;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.teamnexus.excelenium.suite.SuiteConfig;
import in.teamnexus.excelenium.suite.exception.ConfigException;
import in.teamnexus.excelenium.suite.executor.ScriptExecutor;

/**
 * The SuiteService implementation that the ExceleniumController uses to execute the scripts
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
    public boolean saveSuite(SuiteConfig config)
    {
        suiteMap.put(config.getName(), config);
        logger.debug("In Suite Service: " + suiteMap.toString());
        return true;
    }

    @Override
    public SuiteConfig getSuite(String suiteName)
    {
        SuiteConfig suiteConfig = suiteMap.get(suiteName);
        logger.debug("Returning config for: " + suiteName + " " + suiteConfig);
        return suiteConfig;
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


    @Override
    public ServiceResponse validateSuite(SuiteConfig suite)
    {
        ServiceResponse response = suite.validate();
        return response;
    }


}
