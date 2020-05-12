/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import in.teamnexus.excelenium.suite.exception.ScriptException;
import in.teamnexus.excelenium.suite.script.Action;
import in.teamnexus.excelenium.suite.util.WebDriverUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class IsEnabledAction.
 *
 * @author Prabhu
 */
public class IsEnabledAction extends Action
{

    /**
     * Execute action.
     *
     * @param webDriver the web driver
     * @return true, if successful
     * @throws Exception the exception
     */
    @Override
    public boolean executeAction(WebDriver webDriver) throws Exception
    {
        boolean success = true;
        WebElement webElement = this.getWebElement(webDriver, this.element);
        this.doPreProcess(webDriver, webElement);

        if (webElement.isEnabled())
        {
            WebDriverUtil.highlightElement(webDriver, webElement);
            reportsLogger.info(this.getFullyQualifiedName() + " found " + this.element + " enabled");
        }
        else
        {
            if (this.isStopOnError)
            {
                throw new ScriptException("Stopping script as " + this.element + " disabled");
            }
            else
            {
                success = false;
                String warningMsg = "Found " + this.element + " disabled";
                reportsLogger.warn(this.getFullyQualifiedName() + " failed - " + warningMsg);
            }
        }
        return success;
    }

}
