/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.openqa.selenium.WebDriver;

import in.teamnexus.excelenium.suite.script.Action;

// TODO: Auto-generated Javadoc
/**
 * The Class AcceptPopupAction.
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
    public boolean executeAction(WebDriver webDriver)
    {
        boolean success = true;
        
        webDriver.switchTo().alert().accept();
        
        return success;
    }

}
