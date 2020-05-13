/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import in.teamnexus.excelenium.suite.script.Action;

/**
 * Scrolls the window by specified x and y pixels
 * 
 * Column | Description
 * ----------|---------------
 * **Element** | number of `x` pixels to scroll
 * **Element Value** | number of `y` pixels to scroll
 * **Attribute Name** |None
 * **Attribute Value** | None
 * 
 * @author Prabhu
 */
public class ScrollWindowByAction extends Action
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
        
        int x = Integer.parseInt(this.element);
        int y = Integer.parseInt(this.elementValue);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;
        String script = "window.scrollBy(" + x + ", " + y + ")";
        jsExecutor.executeScript(script);
        
        return success;
    }

}
