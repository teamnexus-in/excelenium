/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.openqa.selenium.WebDriver;

import in.teamnexus.excelenium.suite.script.Action;
import in.teamnexus.excelenium.suite.util.WebDriverUtil;

/**
 * Switches to the specified window/tab - based on index from 0 to n.
 * 
 * Column | Description
 * ----------|---------------
 * **Element** |  0 based index, where 0 always indicates the main window and subsequent windows opened are numbers sequentially
 * **Element Value** |None
 * **Attribute Name** |None
 * **Attribute Value** | None
 * 
 * @author Prabhu
 */
public class SwitchToWindowAction extends Action
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
        
        WebDriverUtil.getInstance().switchToWindow(webDriver, this.element);
        
        return success;
    }

}
