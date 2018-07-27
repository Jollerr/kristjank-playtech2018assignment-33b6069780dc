package com.playtech.common;

import static com.playtech.common.CardSuit.CLUBS;
import static com.playtech.common.CardSuit.DIAMONDS;
import static com.playtech.common.CardSuit.HEARTS;
import static com.playtech.common.CardSuit.SPADES;

/**
 * Use this enum as a start to generate card deck, calculations etc
 */
public enum Card {
    C2(CLUBS, CardRank.TWO),
    C3(CLUBS, CardRank.THREE),
    C4(CLUBS, CardRank.FOUR),
    C5(CLUBS, CardRank.FIVE),
    C6(CLUBS, CardRank.SIX),
    C7(CLUBS, CardRank.SEVEN),
    C8(CLUBS, CardRank.EIGHT),
    C9(CLUBS, CardRank.NINE),
    C10(CLUBS, CardRank.TEN),
    CJ(CLUBS, CardRank.TEN),
    CQ(CLUBS, CardRank.TEN),
    CK(CLUBS, CardRank.TEN),
    CA(CLUBS, CardRank.TEN),
    D2(DIAMONDS, CardRank.TWO),
    D3(DIAMONDS, CardRank.THREE),
    D4(DIAMONDS, CardRank.FOUR),
    D5(DIAMONDS, CardRank.FIVE),
    D6(DIAMONDS, CardRank.SIX),
    D7(DIAMONDS, CardRank.SEVEN),
    D8(DIAMONDS, CardRank.EIGHT),
    D9(DIAMONDS, CardRank.NINE),
    D10(DIAMONDS, CardRank.TEN),
    DJ(DIAMONDS, CardRank.TEN),
    DQ(DIAMONDS, CardRank.TEN),
    DK(DIAMONDS, CardRank.TEN),
    DA(DIAMONDS, CardRank.TEN),
    S2(SPADES, CardRank.TWO),
    S3(SPADES, CardRank.THREE),
    S4(SPADES, CardRank.FOUR),
    S5(SPADES, CardRank.FIVE),
    S6(SPADES, CardRank.SIX),
    S7(SPADES, CardRank.SEVEN),
    S8(SPADES, CardRank.EIGHT),
    S9(SPADES, CardRank.NINE),
    S10(SPADES, CardRank.TEN),
    SJ(SPADES, CardRank.TEN),
    SQ(SPADES, CardRank.TEN),
    SK(SPADES, CardRank.TEN),
    SA(SPADES, CardRank.TEN),
    H2(HEARTS, CardRank.TWO),
    H3(HEARTS, CardRank.THREE),
    H4(HEARTS, CardRank.FOUR),
    H5(HEARTS, CardRank.FIVE),
    H6(HEARTS, CardRank.SIX),
    H7(HEARTS, CardRank.SEVEN),
    H8(HEARTS, CardRank.EIGHT),
    H9(HEARTS, CardRank.NINE),
    H10(HEARTS, CardRank.TEN),
    HJ(HEARTS, CardRank.TEN),
    HQ(HEARTS, CardRank.TEN),
    HK(HEARTS, CardRank.TEN),
    HA(HEARTS, CardRank.TEN);

    private final CardSuit suit;
    private final CardRank value;

    Card(CardSuit suit, CardRank value) {
        this.suit = suit;
        this.value = value;
    }

    public CardSuit getSuit() {
        return suit;
    }

    public CardRank getValue() {
        return value;
    }
}
