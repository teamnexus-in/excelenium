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
import in.teamnexus.excelenium.suite.script.Action;

/**
 * Checks if the css attribute of the element has the specified value
 * 
 * Column | Description
 * ----------|---------------
 * **Element** | id or xpath of the element
 * **Element Value** | Css attribute name
 * **Attribute Name** | Css attribute value
 * **Attribute Value** | None
 * 
 * @author Prabhu
 */
public class CheckCssAttributeAction extends Action
{

    /**
     * Execute action.
     *
     * @param webDriver the web driver
     * @return true, if successful
     */
    @Override
    protected boolean executeAction(WebDriver webDriver)
    {
        boolean success = true;
        WebElement webElement = this.getWebElement(webDriver, this.element);
        this.doPreProcess(webDriver, webElement);

        String cssValue = webElement.getCssValue(this.elementValue);
        if (this.attributeName.equals(cssValue))
        {
            reportsLogger.info(this.getFullyQualifiedName() + " element has css attribute " + this.elementValue + " with value: " + cssValue);
        }
        else
        {
            success = false;
            String warningMsg = " element does not have css attribute " + this.elementValue + " with value: " + this.attributeName + " found: " + cssValue;
            reportsLogger.warn(this.getFullyQualifiedName() + " failed - " + warningMsg);
        }

        return success;

    }

    @Override
    protected void validate(ServiceResponse response)
    {
        if ((this.element == null || this.element.isEmpty())
                || (this.elementValue == null || this.elementValue.isEmpty())
                || (this.attributeName == null || this.attributeName.isEmpty()))
        {
            String str = String.format("%s - %s", this.actionName, "Element Name, Element Value, Attribute Name fields cannot be empty.");
            response.setStatus(ServiceResponse.STATUS_FAILURE);
            response.addMessage(ValidationMessage.TYPE_ERROR, str);
        }

        if ((this.attributeValue != null && !this.attributeValue.isEmpty()))
        {
            String str = String.format("%s - %s", this.actionName, "Attribute Value field will be ignored.");
            response.addMessage(ValidationMessage.TYPE_WARNING, str);
        }

    }

}
