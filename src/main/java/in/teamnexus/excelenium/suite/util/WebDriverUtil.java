/*
 * Copyright (C) 2017 - Present. NEXUS. All rights reserved.
 * 
 * https://teamnexus.in
 * 
 * This software is released under the Apache 2.0 License.
 * 
 * 
 */
package in.teamnexus.excelenium.suite.util;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.text.StringSubstitutor;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Utility class that provides helper methods using the webdriver.
 */
/**
 * @author Prabhu
 * 
 */
public class WebDriverUtil
{

    /** The logger. */
    private  Logger logger = LoggerFactory.getLogger(WebDriverUtil.class);

    private HashMap<String, String> map = new HashMap<>();
    private HashMap<String, String> handles = new HashMap<>();
    
    private static WebDriverUtil thisInstance = null;

    private WebDriverUtil()
    {

    }
    
    public static WebDriverUtil getInstance()
    {
        if(thisInstance == null)
        {
            thisInstance = new WebDriverUtil();
        }
        
        return thisInstance;
    }

    /**
     * Highlight element.
     * 
     * @param driver
     *            the driver
     * @param element
     *            the element
     */
    public void highlightElement(WebDriver driver, WebElement element)
    {
        for (int i = 0; i < 2; i++)
        {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "color: yellow; border: 2px solid yellow;");
            js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
        }
    }

    /**
     * Click element.
     * 
     * @param webDriver
     *            the web driver
     * @param webElement
     *            the web element
     * @throws Exception
     *             the exception
     */
    public  void clickElement(WebDriver webDriver, WebElement webElement) throws Exception
    {
        logger.debug("WebUtil click: " + webElement.getTagName());
        webElement.click();
        // JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;
        // String script = "arguments[0].click()";
        // jsExecutor.executeScript(script, webElement);
        addNewWindowHandles(webDriver);
    }

    private void addNewWindowHandles(WebDriver webDriver)
    {
        Set<String> newHandles = webDriver.getWindowHandles();
        Collection<String> currentHandles = handles.values();
        newHandles.removeAll(currentHandles);
        if (!newHandles.isEmpty())
        {
            for (Iterator<String> iterator = newHandles.iterator(); iterator.hasNext();)
            {
                String string = iterator.next();
                handles.put(String.valueOf(handles.size()), string);
            }
        }

    }

    /**
     * Capture screenshot.
     * 
     * @param driver
     *            the driver
     * @param fileName
     *            the file name
     * @throws Exception
     *             the exception
     */
    public void captureScreenshot(WebDriver driver, String fileName) throws Exception
    {
        logger.info("Capturing screenshot");
        File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String outputDir = "screens";
        File file = new File(fileName);
        if (!file.isAbsolute())
        {
            file = new File(outputDir + "/" + fileName);
        }
        FileUtils.copyFile(screen, file);
    }

    /**
     * @param elementValue
     *            - Substitues the value in the string.
     * @return
     */
    public  String substitute(String elementValue)
    {
        map.put("timestamp", String.valueOf(System.currentTimeMillis()));
        String retVal = StringSubstitutor.replace(elementValue, map);
        return retVal;
    }

   
    /**
     * @param variableName
     *            - Sets the variable in scope of the script.
     * @param variableValue
     *            - Sets the variable value in the scope of the script.
     */
    public void setVariable(String variableName, String variableValue)
    {
        map.put(variableName, variableValue);
    }

    /**
     * @param element
     *            - Unsets the variable in the scope of the script.
     */
    public void unsetVariable(String element)
    {
        map.remove(element);
    }

    /**
     * @param webDriver
     *            - Initializes the window handles of the driver
     */
    public void initializeWindowHandles(WebDriver webDriver)
    {
        handles.put("0", webDriver.getWindowHandle());
    }

    /**
     * @param webDriver
     *            - webDriver element
     * @param element
     *            - index of the window - 0 to n - '0' refers to Main window always
     */
    public void switchToWindow(WebDriver webDriver, String element)
    {
        String handle = handles.get(element);
        logger.info("Switching to handle: " + handle);
        logger.info("Available handles: " + handles.toString());
        if (handle != null)
        {
            webDriver.switchTo().window(handle);
        }
    }

    public void getCurrentUrl(WebDriver webDriver, String element)
    {
        String currentUrl = webDriver.getCurrentUrl();
        map.put(element, currentUrl);
    }
    
    public Map<String, String> getMap()
    {
        return this.map;
    }

}
