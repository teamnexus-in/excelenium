/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import in.teamnexus.excelenium.suite.script.Action;

/**
 * Sets the window size of the browser based on the width and height specified.
 * 
 * Column | Description
 * ----------|---------------
 * **Element** | Positive integer value specifying the width
 * **Element Value** | Positive integer value specifying the height
 * **Attribute Name** |None
 * **Attribute Value** | None
 * 
 * @author Prabhu
 */
public class SetWindowSizeAction extends Action
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
        
        int width = Integer.parseInt(this.element);
        int height = Integer.parseInt(this.elementValue);
        webDriver.manage().window().setSize(new Dimension(width, height));
        
        return success;
    }

}
