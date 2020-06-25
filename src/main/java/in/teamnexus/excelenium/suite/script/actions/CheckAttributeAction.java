/*
 * Copyright (C) 2017 - Present. NEXUS. All rights reserved.
 * 
 * https://teamnexus.in
 * 
 * This software is released under the Apache 2.0 License.
 * 
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import in.teamnexus.excelenium.service.ServiceResponse;
import in.teamnexus.excelenium.service.ValidationMessage;
import in.teamnexus.excelenium.suite.exception.ScriptException;
import in.teamnexus.excelenium.suite.script.Action;

/**
 * Checks if the attribute is with the value specified is set for the element..
 *
 * Column | Description
 * ----------|---------------
 * **Element** | id or xpath of the element
 * **Element Value** | None
 * **Attribute Name** | Name of the attribute
 * **Attribute Value** | Value of the attribute
 *
 * @author Prabhu
 */
public class CheckAttributeAction extends Action
{

    /**
     * Execute action.
     *
     * @param webDriver the web driver
     * @return true, if successful
     * @throws Exception the exception
     */
    @Override
    protected boolean executeAction(WebDriver webDriver) throws Exception
    {
        boolean success = true;
        WebElement webElement = this.getWebElement(webDriver, this.element);
        this.doPreProcess(webDriver, webElement);

        String attr = webElement.getAttribute(this.attributeName).trim();
        if (attr != null)
        {
            if (attr.equals(this.attributeValue))
            {
                reportsLogger.info("Attribute: " + this.attributeName + " with value: " + this.attributeValue + " found");
            }
            else
            {
                if (this.isStopOnError)
                {
                    throw new ScriptException("Stopping script as  Attribute: " + this.attributeName + " value: " + attr + " does not match " + this.attributeValue);

                }
                else
                {
                    success = false;
                    String warningMsg = "Attribute: " + this.attributeName + " value: " + attr + " does not match " + this.attributeValue;
                    reportsLogger.warn(this.getFullyQualifiedName() + " failed - " + warningMsg);
                }
            }

        }
        else
        {
            if (this.isStopOnError)
            {
                throw new ScriptException("Stopping script as " + this.attributeName + " not found");
            }
            else
            {
                success = false;
                String warningMsg = "Attribute: " + this.attributeName + " not found";
                reportsLogger.warn(this.getFullyQualifiedName() + " failed - " + warningMsg);
            }
        }
        return success;
    }

    @Override
    protected void validate(ServiceResponse response)
    {
        if ((this.element == null || this.element.isEmpty())
                || (this.elementValue == null || this.elementValue.isEmpty())
                || (this.attributeName == null || this.attributeName.isEmpty())
                || (this.attributeValue == null || this.attributeValue.isEmpty()))
        {
            String str = String.format("%s - %s", this.actionName, "Element Name, Element Value, Attribute Name, Attribute Value fields cannot be empty.");
            response.setStatus(ServiceResponse.STATUS_FAILURE);
            response.addMessage(ValidationMessage.TYPE_ERROR, str);
        }
        
        if(this.preProcess != null)
        {
            this.preProcess.validate(this.actionName, response);
        }
    }

}
