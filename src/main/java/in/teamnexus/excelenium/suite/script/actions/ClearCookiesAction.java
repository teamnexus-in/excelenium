/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.openqa.selenium.WebDriver;

import in.teamnexus.excelenium.service.ServiceResponse;
import in.teamnexus.excelenium.suite.script.Action;

/**
 * Clears all the cookies that are currently present
 * 
 * Column | Description
 * ----------|---------------
 * **Element** | None
 * **Element Value** | None
 * **Attribute Name** |None
 * **Attribute Value** | None
 * 
 * @author Prabhu
 */
public class ClearCookiesAction extends Action
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

        webDriver.manage().deleteAllCookies();

        return success;
    }

    @Override
    protected void validate(ServiceResponse response)
    {

        if ((this.element != null && !this.element.isEmpty())
                || (this.elementValue != null && !this.elementValue.isEmpty())
                || (this.attributeName != null && !this.attributeName.isEmpty())
                || (this.attributeValue != null && !this.attributeValue.isEmpty()))
        {
            String str = String.format("%s - %s", this.actionName, "WARNING: Element Name, Element value, Attribute Name, Attribute Value fields will be ignored.");
            response.addMessage(str);
        }

        if (this.preProcess != null)
        {
            String str = String.format("%s - %s", this.actionName, "WARNING: Preprocess values will be ignored");
            response.addMessage(str);
        }

    }

}
