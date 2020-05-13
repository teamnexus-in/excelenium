/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

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

}
