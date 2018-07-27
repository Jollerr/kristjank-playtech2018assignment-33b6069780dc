package com.playtech.game.protocol;

import java.io.Serializable;

/**
 * Notifies client if PlayerActionRequest was successful or not
 * ErrorText - meaningful error text in case there was an error. Empty if no error.
 */
public class PlayerActionResponse implements Serializable {
    private final String errorText;

    public PlayerActionResponse(String errorText) {
        this.errorText = errorText;
    }

    public String getErrorText() {
        return errorText;
    }

    @Override
    public String toString() {
        return "PlayerActionResponse{" +
                "errorText='" + errorText + '\'' +
                '}';
    }
}
