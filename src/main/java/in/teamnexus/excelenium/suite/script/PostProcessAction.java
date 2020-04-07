/*
 * Copyright (C) 2017 - Present. NEXUS. All rights reserved.
 * 
 * https://teamnexus.in
 * 
 * This software is released under the MIT license.
 * 
 * 
 */
package in.teamnexus.excelenium.suite.script;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Reserved for future not used now.
 * 
 * @author Prabhu
 */
public class PostProcessAction
{
    
    /** The action type. */
    String actionType;
    
    /** The attribute name. */
    String attributeName;
    
    /** The attribute value. */
    String attributeValue;
    
    /** The logger. */
    Logger logger = LoggerFactory.getLogger(PostProcessAction.class);
    
    /** The action. */
    private Action action;
    
    /** The reports logger. */
    @JsonIgnore
    private
    Logger reportsLogger;

    /**
     * Reserved for future not used now.
     */
    public enum ActionType
    {
        
        /** The add attribute. */
        ADD_ATTRIBUTE, 
 /** The remove attribute. */
 REMOVE_ATTRIBUTE, 
 /** The set attribute. */
 SET_ATTRIBUTE
    };

    /** The a type. */
    ActionType aType;

    /**
     * Gets the action type.
     * 
     * @return the action type
     */
    public String getActionType()
    {
        return this.actionType;
    }

    /**
     * Sets the action type.
     * 
     * @param actionType
     *            the new action type
     */
    public void setActionType(String actionType)
    {
        this.actionType = actionType;
        if ("add_attribute".equalsIgnoreCase(actionType))
        {
            this.aType = ActionType.ADD_ATTRIBUTE;
        }
        else if ("set_attribute".equalsIgnoreCase(actionType))
        {
            this.aType = ActionType.SET_ATTRIBUTE;

        }
        else if ("remove_attribute".equalsIgnoreCase(actionType))
        {
            this.aType = ActionType.REMOVE_ATTRIBUTE;
        }

    }

    /**
     * Gets the attribute name.
     * 
     * @return the attribute name
     */
    public String getAttributeName()
    {
        return this.attributeName;
    }

    /**
     * Sets the attribute name.
     * 
     * @param attributeName
     *            the new attribute name
     */
    public void setAttributeName(String attributeName)
    {
        this.attributeName = attributeName;
    }

    /**
     * Gets the attribute value.
     * 
     * @return the attribute value
     */
    public String getAttributeValue()
    {
        return this.attributeValue;
    }

    /**
     * Sets the attribute value.
     * 
     * @param attributeValue
     *            the new attribute value
     */
    public void setAttributeValue(String attributeValue)
    {
        this.attributeValue = attributeValue;
    }

    /**
     * Execute.
     * 
     * @param webDriver
     *            the web driver
     * @param webElement
     *            the web element
     */
    public void execute(WebDriver webDriver, WebElement webElement)
    {
        try
        {
            StopWatch stopwatch = new StopWatch();
            stopwatch.start();
            logger.info("Executing preprocess action" + this.getFullyQualifiedName());
            switch (aType)
            {
                case REMOVE_ATTRIBUTE:
                    this.attributeValue = "";
                    break;
                case ADD_ATTRIBUTE:
                case SET_ATTRIBUTE:
                    break;
            }
            JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;
            String script = "arguments[0]." + this.attributeName + "=arguments[1]";
            jsExecutor.executeScript(script, webElement, this.attributeValue);
            stopwatch.stop();
            logger.info("Executed postprocess action " + webElement.getAttribute(this.attributeName) + " " + this.attributeValue);
            reportsLogger.info("Postprocess Action:" + this.getFullyQualifiedName() + " completed in " + stopwatch.getTime(TimeUnit.MILLISECONDS) + " msecs");
        }
        catch (Exception e)
        {
            logger.error("error", e);
            reportsLogger.error("Postprocess Action:" + this.getFullyQualifiedName() + " failed - " + e.getMessage());
        }

    }

    /**
     * Sets the action.
     * 
     * @param action
     *            the new action
     */
    public void setAction(Action action)
    {
        this.action = action;

    }

    /**
     * Gets the fully qualified name.
     * 
     * @return the fully qualified name
     */
    String getFullyQualifiedName()
    {
        return action.getFullyQualifiedName() + "/" + aType;
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

}
