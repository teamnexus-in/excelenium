/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import in.teamnexus.excelenium.service.ServiceResponse;
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

    @Override
    protected void validate(ServiceResponse response)
    {
        if((this.element == null || this.element.isEmpty()) || (this.elementValue == null || this.elementValue.isEmpty()))
        {
            String str = String.format("%s - %s", this.actionName, "ERROR: Element Name, Element Value fields cannot be empty.");
            response.setStatus(ServiceResponse.STATUS_FAILURE);
            response.addMessage(str);
            
        }
        
        if ((this.attributeName != null && !this.attributeName.isEmpty())
                || (this.attributeValue != null && !this.attributeValue.isEmpty()))
        {
            String str = String.format("%s - %s", this.actionName, "WARNING: Attribute Name, Attribute Value fields will be ignored.");
            response.addMessage(str);
        }
        
        if(this.preProcess != null)
        {
            String str = String.format("%s - %s", this.actionName, "WARNING: Preprocess values will be ignored");
            response.addMessage(str);
        }
        
    }

}
