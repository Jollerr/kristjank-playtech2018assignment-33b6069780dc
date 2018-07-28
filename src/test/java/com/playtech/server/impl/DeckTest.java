package com.playtech.server.impl;

import com.playtech.common.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    Deck deck;

    @BeforeEach
    void setUp() {
        deck = new Deck();
    }

    /**
     * Amount of cards in the deck must be 52
     */
    @Test
    void generateNewDeck_52cards() {
        deck.generateNewDeck();
        assertEquals(52, deck.getCards().size());
    }

    /**
     * 2 different decks must be unique
     */
    @Test
    void generateNewDeck_unique_decks() {
        deck.generateNewDeck();
        List<Card> deck1 = deck.getCards();
        deck.generateNewDeck();
        List<Card> deck2 = deck.getCards();
        assertNotEquals(deck1, deck2);
    }

    /**
     * ArrayIndexOutOfBoundsException NOT expected because there are 51 cards in a deck after first card
     */
    @Test
    void nextCard_51() {
        deck.getCurrentCard();
        for (int i = 1; i <= 51; i++) {
            deck.nextCard();
        }
        deck.getCurrentCard();
    }

    /**
     * ArrayIndexOutOfBoundsException expected because there are 51 cards in a deck after first card
     */
    @Test
    void getNextCard_52() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            for (int i = 1; i <= 52; i++) {
                deck.nextCard();
            }
            deck.getCurrentCard();
        });
    }

    /**
     * After 50 next cards there has to be left 2 cards (current and last card)
     */
    @Test
    void isNextBasePossible_50() {
        for (int i = 1; i <= 50; i++) {
            deck.nextCard();
            deck.getCards().remove(0);
        }
        assertTrue(deck.isNextBasePossible());
    }

    /**
     * After 51 next cards there has to be left 1 cards (last card)
     */
    @Test
    void isNextBasePossible_51() {
        for (int i = 1; i <= 51; i++) {
            deck.nextCard();
        }
        assertFalse(deck.isNextBasePossible());
    }
}