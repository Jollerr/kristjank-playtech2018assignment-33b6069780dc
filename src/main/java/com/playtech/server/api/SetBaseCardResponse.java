package com.playtech.server.api;

import java.io.Serializable;

/**
 * Notifies client is PlayerActionRequest was successful or not
 * ErrorText - meaningful error text in case there was an error. Empty if no error.
 */
public class SetBaseCardResponse implements Serializable {
    private final String errorText;

    public SetBaseCardResponse(String errorText) {
        this.errorText = errorText;
    }

    public String getErrorText() {
        return errorText;
    }

    @Override
    public String toString() {
        return "SetBaseCardResponse{" +
                "errorText='" + errorText + '\'' +
                '}';
    }
}
