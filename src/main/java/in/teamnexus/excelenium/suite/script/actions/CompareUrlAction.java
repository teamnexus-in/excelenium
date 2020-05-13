/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.openqa.selenium.WebDriver;

import in.teamnexus.excelenium.suite.exception.ScriptException;
import in.teamnexus.excelenium.suite.script.Action;

/**
 * Compares the current browser url with the provided url considering the options - `starts_with`, `ends_with`, `contains` and `full_url`.
 * 
 * Column | Description
 * ----------|---------------
 * **Element** | Url to compare against the current browser url
 * **Element Value** | One of following options -<br/> **starts_with** : compares if the current browser url starts with the provided url. The url might contain additional query parameters that might need to be ignored.<br/> **ends_with** : compares if the current browser url ends with the provided url.<br/> **contains** : checks if the browser url contains the provided text.<br/> **full_url** : compares the entire url.
 * **Attribute Name** |None
 * **Attribute Value** | None
 * 
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
    protected boolean executeAction(WebDriver webDriver) throws Exception
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
