package com.afrunt.jach;

import java.util.ResourceBundle;

/**
 * @author Andrii Frunt
 */
public class ACHMessages {
    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("ach_messages");

    public static final String ERROR_LINE_LENGTH = "error.linelength";
    public static final String ERROR_UNKNOWN_RECORD_TYPE = "error.unknownrecordtypecode";

    public String getMessage(String key) {
        return MESSAGES.getString(key);
    }
}
