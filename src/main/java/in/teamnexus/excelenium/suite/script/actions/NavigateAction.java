/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.openqa.selenium.WebDriver;

import in.teamnexus.excelenium.suite.exception.ScriptException;
import in.teamnexus.excelenium.suite.script.Action;

/**
 * Navigates back/forward/to a particular URL.
 *
 * Column | Description
 * ----------|---------------
 * **Element** | One of the values - `back`, `forward`, `refresh` or a url string
 * **Element Value** | None
 * **Attribute Name** |None
 * **Attribute Value** | None
 *
 * @author Prabhu
 */
public class NavigateAction extends Action
{

    /**
     * Execute action.
     *
     * @param webDriver the web driver
     * @return true, if successful
     * @throws Exception the exception
     */
    @Override
    protected boolean executeAction(WebDriver webDriver) throws Exception
    {
        boolean success = true;
        if ("back".equalsIgnoreCase(this.element))
        {
            webDriver.navigate().back();
        }
        else if ("forward".equalsIgnoreCase(this.element))
        {
            webDriver.navigate().forward();
        }
        else if ("refresh".equalsIgnoreCase(this.element))
        {
            webDriver.navigate().refresh();
        }
        else if (element.startsWith("http://") || element.startsWith("https://"))
        {
            webDriver.navigate().to(this.element);
        }
        else
        {
            throw new ScriptException("Unsupported navigate option: " + this.element);
        }

        return success;
    }

}
