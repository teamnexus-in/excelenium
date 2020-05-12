/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import in.teamnexus.excelenium.suite.script.Action;

// TODO: Auto-generated Javadoc
/**
 * The Class ClearAction.
 *
 * @author Prabhu
 */
public class ClearAction extends Action
{

    /**
     * Execute action.
     *
     * @param webDriver the web driver
     * @return true, if successful
     */
    @Override
    public boolean executeAction(WebDriver webDriver)
    {
        boolean success = true;
        WebElement webElement = this.getWebElement(webDriver, this.element);
        
        this.doPreProcess(webDriver, webElement);
        webElement.clear();
        
        return success;
    }

}
