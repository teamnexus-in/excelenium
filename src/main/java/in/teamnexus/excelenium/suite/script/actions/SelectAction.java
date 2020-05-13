/**
 * 
 */
package in.teamnexus.excelenium.suite.script.actions;

import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.teamnexus.excelenium.suite.script.Action;

/**
 * Selects the option in the drop down as specified in the Element Value column. A special variable `<#random>` can be used to randomly select one of the values in the dropdown.
 *
 * Column | Description
 * ----------|---------------
 * **Element** | id or xpath of the element
 * **Element Value** | Text value of the item to be selected or `<#random>` to randomly select one of the items in the dropdown
 * **Attribute Name** |None
 * **Attribute Value** | None

 * @author Prabhu
 */
public class SelectAction extends Action
{
    
    /** The logger. */
    private Logger logger = LoggerFactory.getLogger(getClass());

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
        WebElement webElement = this.getWebElement(webDriver, this.element);
        
        this.doPreProcess(webDriver, webElement);

        String selText = this.elementValue;
        Select select = new Select(webElement);
        if ("<#random>".equalsIgnoreCase(elementValue))
        {
            int size = select.getOptions().size();
            Random rand = new Random();
            int selIdx = rand.nextInt(size);
            logger.debug("Selected Index:" + selIdx);
            select.selectByIndex(selIdx);
            logger.debug("Item Selected:" + select.getFirstSelectedOption().getText());
        }
        else
        {
            logger.debug("Select text:" + selText);
            select.selectByVisibleText(selText);
            logger.debug("Item Selected:" + select.getFirstSelectedOption().getText());
        }
        
        return success;

    }

}
