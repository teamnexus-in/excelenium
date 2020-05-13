/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import in.teamnexus.excelenium.suite.script.Action;
import in.teamnexus.excelenium.suite.util.WebDriverUtil;

/**
 * Captures the currently rendered screen in the browser.
 * 
 * Column | Description
 * ----------|---------------
 * **Element** | Optional file name
 * **Element Value** | None
 * **Attribute Name** |None
 * **Attribute Value** | None
*
 * @author Prabhu
 */
public class CaptureScreenAction extends Action
{
    
    /** The logger. */
    Logger logger = LoggerFactory.getLogger(getClass());

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
        String browser = MDC.get("browser");
        String fileName = ((this.element == null) ? (this.actionName + "-" + browser + System.currentTimeMillis() + ".png") : (browser + this.element));
        WebDriverUtil.getInstance().captureScreenshot(webDriver, fileName);
        logger.info("Screenshot captured to " + fileName);

        return success;

    }

}
