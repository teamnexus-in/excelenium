/*
 * Copyright (C) 2017 - Present. NEXUS. All rights reserved.
 * 
 * https://teamnexus.in
 * 
 * This software is released under the Apache 2.0 License.
 * 
 * 
 */
package in.teamnexus.excelenium.service;

/**
 * @author Prabhu
 *
 */
public class ValidationMessage
{
    public static final int TYPE_ERROR = 101;
    public static final int TYPE_WARNING = 102;
    int type;
    String message;

    public ValidationMessage(int type, String message)
    {
        this.type = type;
        this.message = message;
    }

    /**
     * @return the type
     */
    public int getType()
    {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type)
    {
        this.type = type;
    }

    /**
     * @return the message
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message)
    {
        this.message = message;
    }

}
