/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.teamnexus.excelenium.service.ServiceResponse;
import in.teamnexus.excelenium.suite.script.Action;

/**
 * Fills the value in for the element selected, usually, text field or text
 * area. Optionally can also do a submit in case of search boxes etc. *
 * 
 * Values expected

 * Column | Description
 * ----------|---------------
 * **Element** | id or xpath of the element
 * **Element Value** | Value to be filled in that element
 * **Attribute Name** |true/false to indicate if the field needs to be submitted i.e., Enter key pressed. For example, "Search".
 * **Attribute Value** | None

 * @author Prabhu
 */
public class FillAction extends Action
{

    /** The logger. */
    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Execute action.
     *
     * @param webDriver
     *            the web driver
     * @return true, if successful
     */
    @Override
    protected boolean executeAction(WebDriver webDriver)
    {
        logger.info("In FillAction:" + this.getFullyQualifiedName() + " " + this.element + " " + this.elementValue);
        boolean success = true;
        WebElement webElement = this.getWebElement(webDriver, this.element);

        this.doPreProcess(webDriver, webElement);

        webElement.sendKeys(this.elementValue);
        if ("true".equalsIgnoreCase(this.attributeName))
        {
            webElement.submit();
        }

        return success;

    }

    @Override
    protected void validate(ServiceResponse response)
    {
        if ((this.element == null || this.element.isEmpty())
                || (this.elementValue == null || this.elementValue.isEmpty()))
        {
            String str = String.format("%s - %s", this.actionName, "ERROR: Element Name, Element Value cannot be empty.");
            response.setStatus(ServiceResponse.STATUS_FAILURE);
            response.addMessage(str);
        }
        
        if(this.attributeName != null && !this.attributeName.isEmpty() && !this.attributeName.matches("true|false"))
        {
            String str = String.format("%s - %s", this.actionName, "ERROR: Attribute Name has to be either true or false.");
            response.setStatus(ServiceResponse.STATUS_FAILURE);
            response.addMessage(str);
        }

        if ((this.attributeValue != null && !this.attributeValue.isEmpty()))
        {
            String str = String.format("%s - %s", this.actionName, "WARNING:  Attribute Value fields will be ignored.");
            response.addMessage(str);
        }

        if (this.preProcess != null)
        {
            this.preProcess.validate(this.actionName, response);
        }
    }

}
