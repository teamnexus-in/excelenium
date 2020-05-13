/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.openqa.selenium.WebDriver;

import in.teamnexus.excelenium.suite.script.Action;

/**
 * Wait for the specified milliseconds before performing the next action. This is in addition to the implicit waits provided in Selenium.
 *
 * Column | Description
 * ----------|---------------
 * **Element** | Time to wait in milliseconds before executing next action
 * **Element Value** | None
 * **Attribute Name** |None
 * **Attribute Value** | None
 *
 * @author Prabhu
 */
public class WaitMsecsAction extends Action
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
        
        Thread.sleep(Long.parseLong(this.element));

        return success;
    }

}
