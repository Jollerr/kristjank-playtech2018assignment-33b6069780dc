package com.playtech.server.api;

import com.playtech.game.protocol.PlayerActionRequest;
import com.playtech.game.protocol.PlayerActionResponse;

/**
 * This is a game server that will receive messages from client
 */
public interface GameService {
    /**
     * Client notifies server of its action
     */
    PlayerActionResponse playerAction(PlayerActionRequest playerActionRequest);

    /**
     * Client manually sets a base card. To be used only for testing purpose!
     * Should only be called outside active game round and return an error if game round is already active.
     */
    SetBaseCardResponse setBaseCard(SetBaseCardRequest setBaseCardRequest);
}
