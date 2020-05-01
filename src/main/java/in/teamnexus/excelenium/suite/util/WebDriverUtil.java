/*
 * Copyright (C) 2017 - Present. NEXUS. All rights reserved.
 * 
 * https://teamnexus.in
 * 
 * This software is released under the MIT license.
 * 
 * 
 */
package in.teamnexus.excelenium.suite.util;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.script.ScriptException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.text.StringSubstitutor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;
import org.codehaus.groovy.control.customizers.SecureASTCustomizer;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bsh.EvalError;
import bsh.Interpreter;

/**
 * The Utility class that provides helper methods using the webdriver.
 */
/**
 * @author prabhu
 * 
 */
/**
 * @author prabhu
 */
public class WebDriverUtil
{

    /** The logger. */
    private static Logger logger = LoggerFactory.getLogger(WebDriverUtil.class);

    private static HashMap<String, String> map = new HashMap<>();
    private static HashMap<String, String> handles = new HashMap<>();

    private WebDriverUtil()
    {

    }

    /**
     * Highlight element.
     * 
     * @param driver
     *            the driver
     * @param element
     *            the element
     */
    public static void highlightElement(WebDriver driver, WebElement element)
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
    public static void clickElement(WebDriver webDriver, WebElement webElement) throws Exception
    {
        logger.debug("WebUtil click: " + webElement.getTagName());
        webElement.click();
        // JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;
        // String script = "arguments[0].click()";
        // jsExecutor.executeScript(script, webElement);
        addNewWindowHandles(webDriver);
    }

    private static void addNewWindowHandles(WebDriver webDriver)
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
    public static void captureScreenshot(WebDriver driver, String fileName) throws Exception
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
    public static String substitute(String elementValue)
    {
        map.put("timestamp", String.valueOf(System.currentTimeMillis()));
        String retVal = StringSubstitutor.replace(elementValue, map);
        return retVal;
    }

    /**
     * Executes the javascript file passed in the elementValue
     * 
     * @param webDriver
     * @param elementValue
     *            - Javascript file to be executed
     * @throws ScriptException
     */
    public static void executeJavascript(WebDriver webDriver, String elementValue) throws ScriptException
    {
        try
        {
            logger.info("Executing Javascript file");
            String filePath = System.getProperty("EXEC_DIR") + "/" + elementValue;
            String script = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
            JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;
            jsExecutor.executeScript(script);

        }
        catch (IOException e)
        {
            logger.error("Unable load script file", e);
            throw new ScriptException(e);
        }
    }

    /**
     * @param variableName
     *            - Sets the variable in scope of the script.
     * @param variableValue
     *            - Sets the variable value in the scope of the script.
     */
    public static void setVariable(String variableName, String variableValue)
    {
        map.put(variableName, variableValue);
    }

    /**
     * @param element
     *            - Unsets the variable in the scope of the script.
     */
    public static void unsetVariable(String element)
    {
        map.remove(element);
    }

    /**
     * @param webDriver
     *            - Initializes the window handles of the driver
     */
    public static void initializeWindowHandles(WebDriver webDriver)
    {
        handles.put("0", webDriver.getWindowHandle());
    }

    /**
     * @param webDriver
     *            - webDriver element
     * @param elementValue
     *            - index of the window - 0 to n - '0' refers to Main window always
     */
    public static void switchToWindow(WebDriver webDriver, String elementValue)
    {
        String handle = handles.get(elementValue);
        logger.info("Switching to handle: " + handle);
        logger.info("Available handles: " + handles.toString());
        if (handle != null)
        {
            webDriver.switchTo().window(handle);
        }
    }

    public static void setWindowSize(WebDriver webDriver, int width, int height)
    {
        webDriver.manage().window().setSize(new Dimension(width, height));
    }

    /**
     * @param webDriver
     * @param element
     * @param elementValue
     * @throws Exception
     */
    public static void runScript(WebDriver webDriver, String element, String elementValue) throws Exception
    {
        HashMap<String, String> result = null;
        if (element.endsWith(".bsh"))
        {
            result = runBeanshellScript(webDriver, element, elementValue);
        }
        else if (element.endsWith(".groovy"))
        {
            result = runGroovyScript(webDriver, element, elementValue);
        }

        if (result != null)
        {
            String opStatus = result.get("status");
            boolean status = ((opStatus == null) || ("true".equalsIgnoreCase(opStatus)));
            if (status)
            {
                logger.info("User script executed successfully");
            }
            else
            {
                logger.error("User script execution failed");
            }
        }

    }

    /**
     * @param webDriver
     * @param element
     *            - Root Directory of the script
     * @param elementValue
     *            - Script Name
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private static HashMap<String, String> runGroovyScript(WebDriver webDriver, String element, String elementValue) throws Exception
    {
        logger.info("Executing groovy script");
        final ImportCustomizer imports = new ImportCustomizer();
        imports.addStaticStars("java.lang.Math");
        imports.addStarImports("groovyx.net.http");
        imports.addStaticStars("groovyx.net.http.ContentType", "groovyx.net.http.Method");

        final SecureASTCustomizer secure = new SecureASTCustomizer();
        secure.setClosuresAllowed(true);

        final CompilerConfiguration config = new CompilerConfiguration();
        config.addCompilationCustomizers(imports, secure);

        GroovyScriptEngine gse = new GroovyScriptEngine(element);
        gse.setConfig(config);
        Binding binding = new Binding();
        binding.setVariable("logger", logger);
        binding.setVariable("inputMap", map);
        binding.setVariable("driver", webDriver);
        gse.run(elementValue, binding);
        HashMap<String, String> result = (HashMap<String, String>) binding.getVariable("result");
        return result;
    }

    /**
     * @param webDriver
     * @param element
     * @param elementValue
     * @return
     * @throws EvalError
     * @throws FileNotFoundException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    private static HashMap<String, String> runBeanshellScript(WebDriver webDriver, String element, String elementValue) throws Exception
    {
        logger.info("Executing bsh script");
        Interpreter ip = new Interpreter();
        ip.set("logger", logger);
        ip.set("inputMap", map);
        ip.set("driver", webDriver);
        String filePath = element + "/" + elementValue;
        ip.source(filePath);
        HashMap<String, String> result = (HashMap<String, String>) ip.get("result");
        return result;
    }

    public static void scrollTo(WebDriver webDriver, int x, int y)
    {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;
        String script = "window.scrollTo(" + x + ", " + y + ")";
        jsExecutor.executeScript(script);
    }

    public static void scrollBy(WebDriver webDriver, int x, int y)
    {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;
        String script = "window.scrollBy(" + x + ", " + y + ")";
        jsExecutor.executeScript(script);
    }

    public static void makeRequest(WebDriver webDriver, String url, String responseVar) throws Exception
    {
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try
        {
            logger.info("Making request to " + url);
            client = HttpClients.createDefault();
            HttpGet request = new HttpGet(url);
            Set<Cookie> cookies = webDriver.manage().getCookies();
            for (Iterator<Cookie> iterator = cookies.iterator(); iterator.hasNext();)
            {
                Cookie cookie = iterator.next();
                request.setHeader(cookie.getName(), cookie.getValue());
            }

            response = client.execute(request);
            String content = EntityUtils.toString(response.getEntity(), "UTF-8");
            setVariable(responseVar, content);
        }
        catch (Exception e)
        {
            throw new ScriptException("Error making request: " + e.getMessage());
        }
        finally
        {
            if (client != null)
            {
                try
                {
                    client.close();
                }
                catch (IOException e)
                {
                    logger.warn("MakeRequest: Error on client close");
                }
            }

            if (response != null)
            {
                try
                {
                    response.close();
                }
                catch (IOException e)
                {
                    logger.warn("MakeRequest: Error on response close");
                }
            }

        }

    }

    public static void hover(WebDriver webDriver, WebElement webElement)
    {
        Actions action = new Actions(webDriver);
        action.moveToElement(webElement);
        action.perform();
    }

    public static void dragAndDrop(WebDriver webDriver, WebElement sourceElement, WebElement targetElement)
    {
        Actions action = new Actions(webDriver);
        action.dragAndDrop(sourceElement, targetElement);
        action.perform();
    }

    public static void getCurrentUrl(WebDriver webDriver, String element)
    {
        String currentUrl = webDriver.getCurrentUrl();
        map.put(element, currentUrl);

    }

    public static List<String> getSanitizedList(String[] inputCls)
    {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < inputCls.length; i++)
        {
            if (inputCls[i] != null || !inputCls[i].isEmpty())
            {
                arrayList.add(inputCls[i]);
            }
        }
        return arrayList;
    }

}
