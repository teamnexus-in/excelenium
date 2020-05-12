/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import in.teamnexus.excelenium.suite.script.Action;

// TODO: Auto-generated Javadoc
/**
 * The Class AddCookieAction.
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
    public boolean executeAction(WebDriver webDriver)
    {
        boolean success = true;
        
        Cookie cookie = new Cookie(this.element, this.elementValue);
        webDriver.manage().addCookie(cookie);
        
        return success;
    }

}
