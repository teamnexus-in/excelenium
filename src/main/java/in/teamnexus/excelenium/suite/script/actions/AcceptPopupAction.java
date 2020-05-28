/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.openqa.selenium.WebDriver;

import in.teamnexus.excelenium.service.ServiceResponse;
import in.teamnexus.excelenium.suite.script.Action;

/**
 * Selects OK/Yes when the browser popup is displayed. Popup here refers to the native popup and not the modals generated using JavaScript
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
public class AcceptPopupAction extends Action
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

        webDriver.switchTo().alert().accept();

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
            String str = String.format("%s - %s", this.actionName, "WARNING: Element, Element value, Attribute Name, Attribute Value fields will be ignored.");
            response.addMessage(str);
        }
        
        if(this.preProcess != null)
        {
            String str = String.format("%s - %s", this.actionName, "WARNING: Preprocess values will be ignored");
            response.addMessage(str);
        }
        
    }

}
