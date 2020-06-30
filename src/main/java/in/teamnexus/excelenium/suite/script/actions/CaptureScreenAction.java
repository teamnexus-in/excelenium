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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import in.teamnexus.excelenium.service.ServiceResponse;
import in.teamnexus.excelenium.service.ValidationMessage;
import in.teamnexus.excelenium.suite.script.Action;
import in.teamnexus.excelenium.suite.util.WebDriverUtil;

/**
 * Captures the currently rendered screen in the browser.
 * 
 * Column | Description
 * ----------|---------------
 * **Element** | Optional file name
 * **Element Value** | None
 * **Attribute Name** |None
 * **Attribute Value** | None
*
 * @author Prabhu
 */
public class CaptureScreenAction extends Action
{

    /** The logger. */
    Logger logger = LoggerFactory.getLogger(getClass());

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
        String browser = MDC.get("browser");
        String fileName = ((this.element == null || this.element.isBlank()) ? (this.actionName + "-" + browser  + "-" + System.currentTimeMillis() + ".png") : (browser + "-" + this.element +".png"));
        WebDriverUtil.getInstance().captureScreenshot(webDriver, fileName);
        logger.info("Screenshot captured to " + fileName);

        return success;

    }

    @Override
    protected void validate(ServiceResponse response)
    {
        if ((this.elementValue != null && !this.elementValue.isEmpty())
                || (this.attributeName != null && !this.attributeName.isEmpty())
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
