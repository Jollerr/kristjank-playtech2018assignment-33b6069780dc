package com.playtech.server.impl;

import com.playtech.common.Card;

import java.util.*;

/**
 * A Deck object of 52 cards with appropriate methods
 */
public class Deck {
    private List<Card> cards;
    private int cardsLeft;

    public Deck() {
        generateNewDeck();
    }

    public void generateNewDeck() {
        cards = new ArrayList<Card>(); // to randomize order of cards before storing in a LinkedHashSet
        for (Card card : Card.values()) {
            cards.add(card);
        }
        Collections.shuffle(cards);
        cardsLeft = cards.size();
    }

    public List<Card> getCards() {
        return cards;
    }

    public void nextCard() {
        cardsLeft--;
    }

    public boolean isNextBasePossible() {
        return cardsLeft > 1;
    }

    public Card getNextExpectedCard() {
        return cards.get(cardsLeft - 2);
    }

    public Card getCurrentCard() {
        return cards.get(cardsLeft - 1);
    }

    public void setCurrentCard(Card card) {
        cards.set(cardsLeft - 1, card);
    }

    @Override
    public String toString() {
        return cards.toString();
    }
}
