package com.playtech.game.protocol;

import com.playtech.common.PlayerAction;

import java.io.Serializable;

/**
 * Player notifies server about its action
 */
public final class PlayerActionRequest implements Serializable {
    private final PlayerAction playerAction;

    public PlayerActionRequest(PlayerAction playerAction) {
        this.playerAction = playerAction;
    }

    public PlayerAction getPlayerAction() {
        return playerAction;
    }

    @Override
    public String toString() {
        return "PlayerActionRequest{" +
                "playerAction=" + playerAction +
                '}';
    }
}
