package com.playtech.game.protocol;

import com.playtech.common.Card;

import java.io.Serializable;

/**
 * Server notifies client that round has started
 * Server provides client will all the necessary information that client can use to display to player
 */
public final class StartRoundRequest implements Serializable {
    private final int actionRoundDuration;
    private final long actionRoundStartTimestamp;
    private final long roundId;
    private final Card baseCard;

    public StartRoundRequest(int actionRoundDuration, long actionRoundStartTimestamp, long roundId, Card baseCard) {
        this.actionRoundDuration = actionRoundDuration;
        this.actionRoundStartTimestamp = actionRoundStartTimestamp;
        this.roundId = roundId;
        this.baseCard = baseCard;
    }

    public int getActionRoundDuration() {
        return actionRoundDuration;
    }

    public long getActionRoundStartTimestamp() {
        return actionRoundStartTimestamp;
    }

    public long getRoundId() {
        return roundId;
    }

    public Card getBaseCard() {
        return baseCard;
    }

    @Override
    public String toString() {
        return "StartRoundRequest{" +
                "actionRoundDuration=" + actionRoundDuration +
                ", actionRoundStartTimestamp=" + actionRoundStartTimestamp +
                ", roundId=" + roundId +
                ", baseCard=" + baseCard +
                '}';
    }
}
