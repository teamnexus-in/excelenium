/*
 * Copyright (C) 2017 - Present. NEXUS. All rights reserved.
 * 
 * https://teamnexus.in
 * 
 * This software is released under the Apache 2.0 License.
 * 
 * 
 */
package in.teamnexus.excelenium.suite.exception;

/**
 * The exception class that represents the exceptions during script execution.
 */
public class ScriptException extends Exception
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -6193233958155519293L;

    /**
     * Instantiates a new script exception.
     */
    public ScriptException()
    {
    }

    /**
     * Instantiates a new script exception.
     * 
     * @param message
     *            the message
     */
    public ScriptException(String message)
    {
        super(message);
    }

    /**
     * Instantiates a new script exception.
     * 
     * @param cause
     *            the cause
     */
    public ScriptException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Instantiates a new script exception.
     * 
     * @param message
     *            the message
     * @param cause
     *            the cause
     */
    public ScriptException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
