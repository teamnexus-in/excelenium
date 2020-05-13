/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import in.teamnexus.excelenium.suite.script.Action;
import in.teamnexus.excelenium.suite.util.WebDriverUtil;

/**
 * Verifies if an element is present in the DOM even if it visible or not. 
 * Use IS_VISIBLE to check if the element is displayed.
 * 
 * Column | Description
 * ----------|---------------
 * **Element** | id or xpath of the element
 * **Element Value** | None
 * **Attribute Name** |None
 * **Attribute Value** | None

 * @author Prabhu
 */
public class VerifyPresentAction extends Action
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
        WebDriverUtil util = WebDriverUtil.getInstance();
      
        this.doPreProcess(webDriver, webElement);
        if (webElement != null)
        {
            util.highlightElement(webDriver, webElement);
        }

        return success;

    }

}
