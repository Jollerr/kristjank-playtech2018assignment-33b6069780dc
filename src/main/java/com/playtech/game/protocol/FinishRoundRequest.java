package com.playtech.game.protocol;

import java.io.Serializable;

/**
 * Server notifies client that round is finished
 */
public final class FinishRoundRequest implements Serializable {
    private final long roundId;
    private final boolean win;

    public FinishRoundRequest(long roundId, boolean win) {
        this.roundId = roundId;
        this.win = win;
    }

    public long getRoundId() {
        return roundId;
    }

    public boolean isWin() {
        return win;
    }

    @Override
    public String toString() {
        return "FinishRoundRequest{" +
                "roundId=" + roundId +
                ", win=" + win +
                '}';
    }
}
