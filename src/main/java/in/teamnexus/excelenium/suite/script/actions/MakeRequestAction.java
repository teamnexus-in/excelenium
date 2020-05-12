/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.openqa.selenium.WebDriver;

import in.teamnexus.excelenium.suite.script.Action;
import in.teamnexus.excelenium.suite.util.WebDriverUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class MakeRequestAction.
 *
 * @author Prabhu
 */
public class MakeRequestAction extends Action
{

    /**
     * Execute action.
     *
     * @param webDriver the web driver
     * @return true, if successful
     * @throws Exception the exception
     */
    @Override
    public boolean executeAction(WebDriver webDriver) throws Exception
    {
        boolean success = true;
        
        WebDriverUtil.makeRequest(webDriver, this.element, this.elementValue);
        
        return success;
    }

}
