/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import in.teamnexus.excelenium.suite.script.Action;

/**
 * Drag and drop the source element to target element
 * 
 * Column | Description
 * ----------|---------------
 * **Element** | id or xpath of the source element
 * **Element Value** | id or xpath of target element
 * **Attribute Name** |None
 * **Attribute Value** | None
 * 
 * @author Prabhu
 */
public class DragAndDropAction extends Action
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
        WebElement sourceElement = this.getWebElement(webDriver, this.element);
        this.doPreProcess(webDriver, sourceElement);

        WebElement targetElement = getWebElement(webDriver, this.elementValue);

        Actions action = new Actions(webDriver);
        action.dragAndDrop(sourceElement, targetElement);
        action.perform();


        return success;
    }

}
