/*
 * Copyright (C) 2017 - Present. NEXUS. All rights reserved.
 * 
 * https://teamnexus.in
 * 
 * This software is released under the MIT license.
 * 
 * 
 */
package in.teamnexus.excelenium.suite.script;

import org.openqa.selenium.WebDriver;

import in.teamnexus.excelenium.suite.exception.ScriptException;

/**
 * A generic interface used by implementing classes for running.
 * 
 * @author Prabhu
 */
public interface Executable
{

    /**
     * Execute.
     * 
     * @param webDriver
     *            - The browser webdriver object that is currently running
     * 
     * @throws ScriptException
     *             the script exception
     */
    void execute(WebDriver webDriver) throws ScriptException;
}
