/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.openqa.selenium.WebDriver;

import in.teamnexus.excelenium.service.ServiceResponse;
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

    @Override
    protected void validate(ServiceResponse response)
    {
        if (this.element == null || this.element.isEmpty())
        {
            String str = String.format("%s - %s", this.actionName, "ERROR: Element Name cannot be empty.");
            response.setStatus(ServiceResponse.STATUS_FAILURE);
            response.addMessage(str);
        }

        if (this.element != null && !this.element.isEmpty() && !this.element.matches("back|forward|refresh")
                && !this.element.startsWith("http://") && !this.element.startsWith("https://"))
        {
            String str = String.format("%s - %s", this.actionName, "ERROR: Element Name has to have one of the values - back, forward, refresh or a url.");
            response.setStatus(ServiceResponse.STATUS_FAILURE);
            response.addMessage(str);
        }

        if ((this.elementValue != null && !this.elementValue.isEmpty())
                || (this.attributeName != null && !this.attributeName.isEmpty())
                || (this.attributeValue != null && !this.attributeValue.isEmpty()))
        {
            String str = String.format("%s - %s", this.actionName, "WARNING: Element value, Attribute Name, Attribute Value fields will be ignored.");
            response.addMessage(str);
        }

        if (this.preProcess != null)
        {
            String str = String.format("%s - %s", this.actionName, "WARNING: Preprocess values will be ignored");
            response.addMessage(str);
        }
    }

}
