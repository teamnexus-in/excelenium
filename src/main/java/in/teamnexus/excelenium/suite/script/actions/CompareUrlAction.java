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

import in.teamnexus.excelenium.service.ServiceResponse;
import in.teamnexus.excelenium.service.ValidationMessage;
import in.teamnexus.excelenium.suite.exception.ScriptException;
import in.teamnexus.excelenium.suite.script.Action;

/**
 * Compares the current browser url with the provided url considering the options - `starts_with`, `ends_with`, `contains` and `full_url`.
 * 
 * Column | Description
 * ----------|---------------
 * **Element** | Url to compare against the current browser url
 * **Element Value** | One of following options -<br/> **starts_with** : compares if the current browser url starts with the provided url. The url might contain additional query parameters that might need to be ignored.<br/> **ends_with** : compares if the current browser url ends with the provided url.<br/> **contains** : checks if the browser url contains the provided text.<br/> **full_url** : compares the entire url.
 * **Attribute Name** |None
 * **Attribute Value** | None
 * 
 * 
 * @author Prabhu
 */
public class CompareUrlAction extends Action
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
        
        String currentUrl = webDriver.getCurrentUrl();
        if (this.elementValue.equalsIgnoreCase("starts_with"))
        {
            if (!currentUrl.startsWith(this.element))
            {
                throw new ScriptException("url does not start with" + this.element);
            }
        }
        else if (this.elementValue.equalsIgnoreCase("full_url"))
        {
            if (!currentUrl.equals(this.element))
            {
                throw new ScriptException("url does not match " + this.element);
            }
        }
        else if (this.elementValue.equalsIgnoreCase("ends_with"))
        {
            if (!currentUrl.endsWith(this.element))
            {
                throw new ScriptException("url does not end with " + this.element);
            }
        }
        else if (this.elementValue.equalsIgnoreCase("contains"))
        {
            if (!currentUrl.contains(this.element))
            {
                throw new ScriptException("url does not contain " + this.element);
            }
        }
        else
        {
            throw new ScriptException("Unsupported option " + this.elementValue + " for operation " + actionType);
        }
        
        return success;
    }

    @Override
    protected void validate(ServiceResponse response)
    {
        if ((this.element == null || this.element.isEmpty())
                || (this.elementValue == null || this.elementValue.isEmpty()))
        {
            String str = String.format("%s - %s", this.actionName, "Element Name, Element Value cannot be empty.");
            response.setStatus(ServiceResponse.STATUS_FAILURE);
            response.addMessage(ValidationMessage.TYPE_ERROR, str);
        }
        
        if(this.elementValue != null && !this.elementValue.matches("starts_with|ends_with|contains|full_url"))
        {
            String str = String.format("%s - %s", this.actionName, "Element Value has to have one of the values - starts_with, ends_with, contains, full_url.");
            response.setStatus(ServiceResponse.STATUS_FAILURE);
            response.addMessage(ValidationMessage.TYPE_ERROR, str);
        }
        
        if ((this.attributeName != null && !this.attributeName.isEmpty())
                || (this.attributeValue != null && !this.attributeValue.isEmpty()))
        {
            String str = String.format("%s - %s", this.actionName, "Element value, Attribute Name, Attribute Value fields will be ignored.");
            response.addMessage(ValidationMessage.TYPE_WARNING, str);
        }

        if (this.preProcess != null)
        {
            String str = String.format("%s - %s", this.actionName, "Preprocess values will be ignored");
            response.addMessage(ValidationMessage.TYPE_WARNING, str);
        }
    }

}
