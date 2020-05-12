/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import in.teamnexus.excelenium.suite.exception.ScriptException;
import in.teamnexus.excelenium.suite.script.Action;

// TODO: Auto-generated Javadoc
/**
 * The Class IsHiddenAction.
 *
 * @author Prabhu
 */
public class IsHiddenAction extends Action
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
        
        if (!webElement.isDisplayed())
        {
            reportsLogger.info(this.getFullyQualifiedName() + " found " + this.element + " hidden");
        }
        else
        {
            if (this.isStopOnError)
            {
                throw new ScriptException("Stopping script as " + this.element + " is visible");
            }
            else
            {
                success = false;
                String warningMsg = " visible element " + this.element;
                reportsLogger.warn(this.getFullyQualifiedName() + " failed - " + warningMsg);
            }
        }
        return success;
    }

}
