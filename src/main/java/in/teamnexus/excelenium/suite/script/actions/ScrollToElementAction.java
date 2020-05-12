/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import in.teamnexus.excelenium.suite.script.Action;
import in.teamnexus.excelenium.suite.util.WebDriverUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class ScrollToElementAction.
 *
 * @author Prabhu
 */
public class ScrollToElementAction extends Action
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
        WebElement webElement = this.getWebElement(webDriver, this.element);
        this.doPreProcess(webDriver, webElement);
        
        Point point = webElement.getLocation();// coord.onPage();
        WebDriverUtil.scrollTo(webDriver, point.getX(), point.getY());

        return success;
    }

}
