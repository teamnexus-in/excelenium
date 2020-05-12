/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.openqa.selenium.WebDriver;

import in.teamnexus.excelenium.suite.exception.ScriptException;
import in.teamnexus.excelenium.suite.script.Action;

// TODO: Auto-generated Javadoc
/**
 * The Class CompareUrlAction.
 *
 * @author Prabhu
 */
public class CompareUrlAction extends Action
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
        
        String currentUrl = webDriver.getCurrentUrl();
        if (this.elementValue.equalsIgnoreCase("starts_with"))
        {
            if (!currentUrl.startsWith(this.element))
            {
                throw new ScriptException("url does not start with" + this.element);
            }
        }
        else if (this.elementValue.equalsIgnoreCase("full_url"))
        {
            if (!currentUrl.equals(this.element))
            {
                throw new ScriptException("url does not match " + this.element);
            }
        }
        else if (this.elementValue.equalsIgnoreCase("ends_with"))
        {
            if (!currentUrl.endsWith(this.element))
            {
                throw new ScriptException("url does not end with " + this.element);
            }
        }
        else if (this.elementValue.equalsIgnoreCase("contains"))
        {
            if (!currentUrl.contains(this.element))
            {
                throw new ScriptException("url does not contain " + this.element);
            }
        }
        else
        {
            throw new ScriptException("Unsupported option " + this.elementValue + " for operation " + actionType);
        }
        
        return success;
    }

}
