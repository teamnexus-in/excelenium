/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.teamnexus.excelenium.suite.script.Action;

/**
 * Fills the value in for the element selected, usually, text field or text
 * area. Optionally can also do a submit in case of search boxes etc. *
 * 
 * Values expected
 * 
 * <b>Element</b> - id or xpath of the element <br/>
 * <b>Element Value</b> - Value to be filled in that element<br/>
 * <b>Attribute Name</b> - true/false to indicate if the field needs to be submitted i.e., Enter key
 * pressed. For example, "Search". <b>Default: </b> false<br/>
 * <b>Attribute Value </b> - None
 * 
 * @author Prabhu
 */
public class FillAction extends Action
{

    /** The logger. */
    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Execute action.
     *
     * @param webDriver
     *            the web driver
     * @return true, if successful
     */
    @Override
    public boolean executeAction(WebDriver webDriver)
    {
        logger.info("In FillAction:" + this.getFullyQualifiedName() + " " + this.element + " " + this.elementValue);
        boolean success = true;
        WebElement webElement = this.getWebElement(webDriver, this.element);

        this.doPreProcess(webDriver, webElement);

        webElement.sendKeys(this.elementValue);
        if ("true".equalsIgnoreCase(this.attributeName))
        {
            webElement.submit();
        }

        return success;

    }

}
