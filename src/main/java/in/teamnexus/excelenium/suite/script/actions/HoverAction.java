/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import in.teamnexus.excelenium.service.ServiceResponse;
import in.teamnexus.excelenium.suite.script.Action;

/**
 * Hovers the mouse on the specified element
 * 
 * Column | Description
 * ----------|---------------
 * **Element** | id or xpath of the element
 * **Element Value** | None
 * **Attribute Name** |None
 * **Attribute Value** | None
 * 
 * @author Prabhu
 */
public class HoverAction extends Action
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

        Actions action = new Actions(webDriver);
        action.moveToElement(webElement);
        action.perform();

        return success;
    }

    @Override
    protected void validate(ServiceResponse response)
    {
        if (this.element == null || this.element.isEmpty())
        {
            String str = String.format("%s - %s", this.actionName, "ERROR: Element Name cannot be empty.");
            response.setStatus(ServiceResponse.STATUS_FAILURE);
            response.addMessage(str);
        }
        
        if ((this.elementValue != null && !this.elementValue.isEmpty())
                || (this.attributeName != null && !this.attributeName.isEmpty())
                || (this.attributeValue != null && !this.attributeValue.isEmpty()))
        {
            String str = String.format("%s - %s", this.actionName, "WARNING: Element value, Attribute Name, Attribute Value fields will be ignored.");
            response.addMessage(str);
        }

        if (this.preProcess != null)
        {
            this.preProcess.validate(this.actionName, response);
        }        
    }

}
