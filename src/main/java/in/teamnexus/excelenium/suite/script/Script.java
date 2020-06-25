/*
 * Copyright (C) 2017 - Present. NEXUS. All rights reserved.
 * 
 * https://teamnexus.in
 * 
 * This software is released under the Apache 2.0 License.
 * 
 * 
 */
package in.teamnexus.excelenium.suite.script;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Stopwatch;

import in.teamnexus.excelenium.service.ServiceResponse;
import in.teamnexus.excelenium.service.ValidationMessage;
import in.teamnexus.excelenium.suite.exception.ScriptException;

/**
 * Represents the script sheet in the spreadsheet which is a collection of actions.
 */
public class Script implements Executable
{

    /** The name. */
    String name;

    /** The actions. */
    List<Action> actions;
    
    /** The is run. */
    private boolean isRun = true;

    /** The stop on error. */
    private boolean stopOnError;

    /** The logger. */
    Logger logger = LoggerFactory.getLogger(getClass());

    /** The reports logger. */
    @JsonIgnore
    private Logger reportsLogger;

    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Sets the name.
     * 
     * @param name
     *            the name of the script
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Gets the actions.
     * 
     * @return the actions
     */
    public List<Action> getActions()
    {
        return this.actions;
    }

    /**
     * Sets the actions.
     * 
     * @param actions
     *            list of actions to be performed
     */
    public void setActions(List<Action> actions)
    {
        this.actions = actions;
    }

    /**
     * Checks if is run.
     *
     * @return the isRun
     */
    public boolean isRun()
    {
        return isRun;
    }

    /**
     * Sets the run.
     *
     * @param isRun the isRun to set
     */
    public void setRun(boolean isRun)
    {
        this.isRun = isRun;
    }

    /**
     * Checks if is stop on error.
     * 
     * @return  true if the script is to be stopped on error, false otherwise
     */
    public boolean isStopOnError()
    {
        return stopOnError;
    }

    /**
     * Sets the stop on error.
     * 
     * @param stopOnError
     *             true if the script is to be stopped on error, false otherwise
     */
    public void setStopOnError(boolean stopOnError)
    {
        this.stopOnError = stopOnError;
    }

    /**
     * (non-Javadoc).
     *
     * @param webDriver the web driver
     * @throws ScriptException the script exception
     * @see com.teamnexus.excelenium.automation.script.Executable#execute(org.openqa.selenium
     * .WebDriver)
     */
    @Override
    public void execute(WebDriver webDriver) throws ScriptException
    {
        
        try
        {
            Stopwatch stopwatch = Stopwatch.createStarted();
            logger.info("Running script: " + this.name);
            for (Action action : actions)
            {
                if (action.isExecute())
                {
                    logger.info("running action:" + action.getActionName());
                    logger.debug(action.toString());
                    action.setReportsLogger(reportsLogger);
                    action.setScript(this);
                    action.execute(webDriver);
                }
                else
                {
                    logger.info("running action:" + action.getActionName() + " execute is set to false");
                }
            }
            stopwatch.stop();
            logger.info("Script: " + this.name + " completed in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " msecs");
            reportsLogger.info(this.name + " completed in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " msecs");
        }
        catch (ScriptException e)
        {
            logger.error("Script " + this.name + " stopped due to script error");
            reportsLogger.error(this.name + " stopped due to script error");
            throw new ScriptException("Script stopped: " + e.getMessage());
        }

    }

    /**
     * Gets the reports logger.
     * 
     * @return the reportsLogger
     */
    public Logger getReportsLogger()
    {
        return reportsLogger;
    }

    /**
     * Sets the reports logger.
     * 
     * @param reportsLogger
     *            the reportsLogger to set
     */
    public void setReportsLogger(Logger reportsLogger)
    {
        this.reportsLogger = reportsLogger;
    }

    public void validate(ServiceResponse response)
    {
        if(name == null || name.isEmpty())
        {
            response.setStatus(ServiceResponse.STATUS_FAILURE);
            response.addMessage(ValidationMessage.TYPE_ERROR, "Script name cannot be empty.");
        }
        
        for (Action action : actions)
        {
            action.validate(response);
        }
        
    }
}
