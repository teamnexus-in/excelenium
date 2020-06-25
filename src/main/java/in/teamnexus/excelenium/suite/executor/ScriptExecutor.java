/*
 * Copyright (C) 2017 - Present. NEXUS. All rights reserved.
 * 
 * https://teamnexus.in
 * 
 * This software is released under the Apache 2.0 License.
 * 
 * 
 */
package in.teamnexus.excelenium.suite.executor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import in.teamnexus.excelenium.suite.BrowserConfig;
import in.teamnexus.excelenium.suite.SuiteConfig;
import in.teamnexus.excelenium.suite.SuiteSettings;
import in.teamnexus.excelenium.suite.UserAgentConfig;
import in.teamnexus.excelenium.suite.exception.ConfigException;
import in.teamnexus.excelenium.suite.exception.ScriptException;
import in.teamnexus.excelenium.suite.script.Script;
import in.teamnexus.excelenium.suite.util.WebDriverUtil;

/**
 * The class that executes all the scripts that are marked as <b>TRUE</b> in the
 * <b>settings</b> sheet of the xlsx.
 */
public class ScriptExecutor
{
    
    /** The suite config. */
    private SuiteConfig suiteConfig;

    /** The logger. */
    Logger logger = LoggerFactory.getLogger(ScriptExecutor.class);

    /**
     * Execute automation.
     * 
     * @throws ConfigException
     *             the config exception
     */
    public void executeAutomation() throws ConfigException
    {

        SuiteSettings suiteSettings = this.suiteConfig.getSettings();
        List<BrowserConfig> browsers = suiteSettings.getBrowsers();

        boolean isConcurrent = this.suiteConfig.getSettings().isRunConcurrent();

        logger.info("####### Is Concurrent: " + isConcurrent);

        for (BrowserConfig browserCfg : browsers)
        {
            if (browserCfg.isEnabled())
            {
                WebDriver driver = getWebDriver(browserCfg);
                BrowserRunner browserRunner = new BrowserRunner(driver, browserCfg);
                if (isConcurrent)
                {
                    new Thread(browserRunner).start();
                }
                else
                {
                    browserRunner.run();
                }
            }
        }

    }

    /**
     * Gets the web driver.
     *
     * @param browserCfg            the browser
     * @return the web driver
     */
    private WebDriver getWebDriver(BrowserConfig browserCfg)
    {
        WebDriver webDriver = null;
        String browserName = browserCfg.getName();
        UserAgentConfig uaCfg = this.suiteConfig.getSettings().getUserAgent();
        if ("firefox".equals(browserName))
        {
            System.setProperty("webdriver.gecko.driver", browserCfg.getDriverPath());
            if (uaCfg != null && uaCfg.isEnabled())
            {
                FirefoxProfile ffp = new FirefoxProfile();
                ffp.setPreference("general.useragent.override", uaCfg.getUserAgent());
                FirefoxOptions ffo = new FirefoxOptions();
                ffo.setProfile(ffp);
                webDriver = new FirefoxDriver(ffo);
            }
            else
            {
                webDriver = new FirefoxDriver();
            }
        }
        else if ("chrome".equals(browserName))
        {
            System.setProperty("webdriver.chrome.driver", browserCfg.getDriverPath());
            if (uaCfg != null && uaCfg.isEnabled())
            {
                String ua = "--user-agent=" + uaCfg.getUserAgent();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments(ua);
                webDriver = new ChromeDriver(chromeOptions);
            }
            else
            {
                webDriver = new ChromeDriver();
            }
        }
        else if ("edge".equals(browserName))
        {
            System.setProperty("webdriver.edge.driver", browserCfg.getDriverPath());
            webDriver = new EdgeDriver();
        }
        else if ("opera".equals(browserName))
        {
            System.setProperty("webdriver.opera.driver", browserCfg.getDriverPath());
            if (uaCfg != null && uaCfg.isEnabled())
            {
                String ua = "--user-agent=" + uaCfg.getUserAgent();
                OperaOptions operaOptions = new OperaOptions();
                operaOptions.addArguments(ua);
                webDriver = new OperaDriver(operaOptions);
            }
            else
            {
                webDriver = new OperaDriver();
            }
        }
        else if ("safari".equals(browserName))
        {
            String os = System.getProperty("os.name").toLowerCase();
            if (isWindows(os) || isUnix(os))
            {
                logger.info("Safari is not supported on Microsoft Windows or Linux.");
            }
            else if (isMac(os))
            {
                webDriver = new SafariDriver();
            }
        }

        return webDriver;
    }

    /**
     * Checks if is windows.
     * 
     * @param os
     *            the os
     * @return true, if is windows
     */
    boolean isWindows(String os)
    {

        return (os.indexOf("win") >= 0);

    }

    /**
     * Checks if is mac.
     * 
     * @param os
     *            the os
     * @return true, if is mac
     */
    boolean isMac(String os)
    {

        return (os.indexOf("mac") >= 0);

    }

    /**
     * Checks if is unix.
     * 
     * @param os
     *            the os
     * @return true, if is unix
     */
    boolean isUnix(String os)
    {

        return (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0 || os.indexOf("aix") >= 0);

    }

    /**
     * Gets the suite config.
     *
     * @return the suite config
     */
    public SuiteConfig getSuiteConfig()
    {
        return suiteConfig;
    }

    /**
     * Sets the suite config.
     *
     * @param suiteConfig the new suite config
     */
    public void setSuiteConfig(SuiteConfig suiteConfig)
    {
        this.suiteConfig = suiteConfig;
    }

    /**
     * The Class BrowserRunner.
     */
    private class BrowserRunner implements Runnable
    {

        /** The driver. */
        WebDriver driver;

        /** The browser. */
        BrowserConfig browserCfg;

        /**
         * Instantiates a new browser runner.
         *
         * @param driver            the webdriver object
         * @param browserCfg            the browser
         */
        BrowserRunner(WebDriver driver, BrowserConfig browserCfg)
        {
            this.driver = driver;
            this.browserCfg = browserCfg;
        }

        /**
         * Run.
         */
        /*
         * (non-Javadoc)
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run()
        {
            logger.info("running browser " + browserCfg + " driver: " + driver);
            if (driver != null)
            {
                try
                {
                    driver.manage().window().maximize();
                    WebDriverUtil.getInstance().initializeWindowHandles(driver);
                    driver.get(suiteConfig.getSettings().getServerUrl());
                    executeScripts(driver);
                    logger.info("**** Finished executing the script. WebDriver will close in a few moments");
                    Thread.sleep(10000);
                }
                catch (Exception e)
                {
                    logger.error("Error while running script", e);
                }
                finally
                {
                    driver.quit();
                    logger.info("Webdriver for browser: " + browserCfg + " completed");
                }
            }
        }

        /**
         * Execute scripts.
         *
         * @param webDriver            the web driver
         * @throws ScriptException the script exception
         */
        private void executeScripts(WebDriver webDriver) throws ScriptException
        {
            Logger reportsLogger = LoggerFactory.getLogger("reports");
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-HHmmss");
            String timestamp = format.format(new Date());
            String browserString = this.browserCfg.getName() + "-" + timestamp;
            MDC.put("browser", browserString);
            List<Script> scripts = suiteConfig.getScripts();

            for (Script script : scripts)
            {
                script.setReportsLogger(reportsLogger);
                if (script.isRun())
                {
                    logger.info("Running script:" + script.getName());
                    script.execute(webDriver);
                }
                else
                {
                    logger.info("Skipping script:" + script.getName() + " as Execute is false");
                }
            }

        }

    }

}
