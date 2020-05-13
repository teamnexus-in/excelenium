/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.openqa.selenium.WebDriver;

import in.teamnexus.excelenium.suite.script.Action;

/**
 * Selects Cancel/No when the browser popup is displayed. Popup here refers to the native popup and not the modals generated using JavaScript
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
public class DismissPopupAction extends Action
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

        webDriver.switchTo().alert().dismiss();
        
        return success;
    }

}
