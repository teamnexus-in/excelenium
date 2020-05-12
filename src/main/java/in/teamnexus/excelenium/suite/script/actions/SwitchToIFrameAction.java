/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import in.teamnexus.excelenium.suite.script.Action;

// TODO: Auto-generated Javadoc
/**
 * The Class SwitchToIFrameAction.
 *
 * @author Prabhu
 */
public class SwitchToIFrameAction extends Action
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
        WebElement webElement = getWebElement(webDriver, this.element);

        webDriver.switchTo().frame(webElement);

        return success;
    }

}
