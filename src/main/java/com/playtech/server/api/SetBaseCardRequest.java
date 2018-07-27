package com.playtech.server.api;

import com.playtech.common.Card;

import java.io.Serializable;

/**
 * Client sets manually server base card
 */
public class SetBaseCardRequest implements Serializable {
    private final Card baseCard;

    public SetBaseCardRequest(Card baseCard) {
        this.baseCard = baseCard;
    }

    public Card getBaseCard() {
        return baseCard;
    }

    @Override
    public String toString() {
        return "SetBaseCardRequest{" +
                "baseCard=" + baseCard +
                '}';
    }
}
