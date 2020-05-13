/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import in.teamnexus.excelenium.suite.script.Action;

/**
 * Adds the specified cookie with the given value
 * 
 * Column | Description
 * ----------|---------------
 * **Element** | cookie name
 * **Element Value** | cookie value
 * **Attribute Name** |None
 * **Attribute Value** | None
 * 
 * @author Prabhu
 */
public class AddCookieAction extends Action
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
        
        Cookie cookie = new Cookie(this.element, this.elementValue);
        webDriver.manage().addCookie(cookie);
        
        return success;
    }

}
