/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import in.teamnexus.excelenium.service.ServiceResponse;
import in.teamnexus.excelenium.suite.script.Action;

/**
 * Generates a mouse right click for the element selected
 * 
 * Column | Description
 * ----------|---------------
 * **Element** | id or xpath of the element
 * **Element Value** | 0 based index indicating the option to be chosen in case of a native browser context menu, -ve value in case of a Javascript generated context menu
 * **Attribute Name** |None
 * **Attribute Value** | None
 *
 * @author Prabhu
 */
public class RightClickAction extends Action
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

        Actions actions = new Actions(webDriver);
        actions = actions.contextClick(webElement);
        int itemToSelect = -1;
        if (this.elementValue != null && !this.elementValue.isEmpty())
        {
            itemToSelect = Integer.parseInt(this.elementValue);
        }
        if (itemToSelect >= 0)
        {
            for (int i = 0; i < itemToSelect; i++)
            {
                actions.sendKeys(Keys.ARROW_DOWN);
            }
            actions.sendKeys(Keys.ENTER);
        }
        actions.perform();
        
        return success;

    }

    @Override
    protected void validate(ServiceResponse response)
    {
        if ((this.element == null || this.element.isEmpty())
                || (this.elementValue == null || this.elementValue.isEmpty()))
        {
            String str = String.format("%s - %s", this.actionName, "ERROR: Element Name, Element Value, Attribute Name, Attribute Value fields cannot be empty.");
            response.setStatus(ServiceResponse.STATUS_FAILURE);
            response.addMessage(str);
        }
        
        if(this.elementValue != null && !this.elementValue.isEmpty() && !StringUtils.isNumeric(this.elementValue))
        {
            String str = String.format("%s - %s", this.actionName, "ERROR: Element Value has to be a 0-based numeric index.");
            response.setStatus(ServiceResponse.STATUS_FAILURE);
            response.addMessage(str);
        }
        
        if ((this.attributeName != null && !this.attributeName.isEmpty())
                || (this.attributeValue != null && !this.attributeValue.isEmpty()))
        {
            String str = String.format("%s - %s", this.actionName, "WARNING: Attribute Name, Attribute Value fields will be ignored.");
            response.addMessage(str);
        }

        if (this.preProcess != null)
        {
            this.preProcess.validate(this.actionName, response);
        }
    }

}
