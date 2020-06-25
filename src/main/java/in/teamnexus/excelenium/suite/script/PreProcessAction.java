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

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Stopwatch;

import in.teamnexus.excelenium.service.ServiceResponse;
import in.teamnexus.excelenium.service.ValidationMessage;

/**
 * Executed before the action on the element is performed.
 */
public class PreProcessAction
{

    /** The action type. */
    String actionType;

    /** The attribute name. */
    String attributeName;

    /** The attribute value. */
    String attributeValue;

    /** The logger. */
    @JsonIgnore
    Logger logger = LoggerFactory.getLogger(PreProcessAction.class);

    /** The reports logger. */
    @JsonIgnore
    private Logger reportsLogger;

    /**
     * The preprocess action type to add, remove and set attribute for the element.
     */
    public enum ActionType
    {

        /** add an attribute to the element. */
        ADD_ATTRIBUTE,
        /** remove attribute from the element. */
        REMOVE_ATTRIBUTE,
        /** set attribute of the element. */
        SET_ATTRIBUTE
    };

    /** The action type on of the permissible ActionType enum values. */
    ActionType aType;

    /** The action object that has the preprocess action. */
    private Action action;

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
     *            - one of add_attribute, set_attribute, remove_attribute
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
     *            the attribute name
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
     *            the attribute value
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
            Stopwatch stopwatch = Stopwatch.createStarted();
            logger.info("Executing preprocess action " + this.getFullyQualifiedName() + " attributeName: " + this.attributeName + "  attributeValue: " + webElement.getAttribute(attributeName));
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
            logger.info("Executed preprocess action " + aType + " " + this.attributeName + " " + webElement.getAttribute(this.attributeName) + " " + this.attributeValue);
            reportsLogger.info("Preprocess Action:" + this.getFullyQualifiedName() + " completed in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " msecs");
        }
        catch (Exception e)
        {
            logger.error("error", e);
            reportsLogger.error("Preprocess Action:" + this.getFullyQualifiedName() + " failed - " + e.getMessage());
        }

    }

    /**
     * Sets the action.
     * 
     * @param action
     *            the action that has the 
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

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("PreProcessAction [actionType=");
        builder.append(actionType);
        builder.append(", attributeName=");
        builder.append(attributeName);
        builder.append(", attributeValue=");
        builder.append(attributeValue);
        builder.append(", aType=");
        builder.append(aType);
        builder.append(", action=");
        builder.append(action);
        builder.append("]");
        return builder.toString();
    }

    public void validate(String actionName, ServiceResponse response)
    {
        if (this.aType == ActionType.REMOVE_ATTRIBUTE)
        {
            if ((this.attributeName == null || this.attributeName.isEmpty()))
            {
                String str = String.format("%s - %s", actionName, "Preprocess Attribute Name cannot be empty.");
                response.setStatus(ServiceResponse.STATUS_FAILURE);
                response.addMessage(ValidationMessage.TYPE_ERROR, str);
            }

            if ((this.attributeValue != null && !this.attributeValue.isEmpty()))
            {
                String str = String.format("%s - %s", actionName, "Preprocess Attribute Value will be ignored.");
                response.addMessage(ValidationMessage.TYPE_WARNING, str);
            }

        }
        else
        {
            if ((this.attributeName == null || this.attributeName.isEmpty())
                    || (this.attributeValue == null || this.attributeValue.isEmpty()))
            {
                String str = String.format("%s - %s", actionName, "Preprocess Attribute Name, Attribute Value fields cannot be empty.");
                response.setStatus(ServiceResponse.STATUS_FAILURE);
                response.addMessage(ValidationMessage.TYPE_ERROR, str);
            }

        }

    }

}
