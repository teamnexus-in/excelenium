/*
 * Copyright (C) 2017 - Present. NEXUS. All rights reserved.
 * 
 * https://teamnexus.in
 * 
 * This software is released under the MIT license.
 * 
 * 
 */
package in.teamnexus.excelenium.suite.exception;

/**
 * The exception class representing exceptions in the configuration.
 */
public class ConfigException extends Exception
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 7016318997917588411L;

    /**
     * Instantiates a new config exception.
     */
    public ConfigException()
    {
    }

    /**
     * Instantiates a new config exception.
     * 
     * @param message
     *            the message
     */
    public ConfigException(String message)
    {
        super(message);
    }

    /**
     * Instantiates a new config exception.
     * 
     * @param cause
     *            the cause
     */
    public ConfigException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Instantiates a new config exception.
     * 
     * @param message
     *            the message
     * @param cause
     *            the cause
     */
    public ConfigException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
