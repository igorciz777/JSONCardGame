import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private DeckService deckService;
    private CardDeck deck;
    private int counter;
    private Scanner scanner;
    private List<Card> playerCardList;
    private List<Card> dealerCardList;

    public void startNewGame(int deckCount) throws IOException, ParseException {
        deckService = new DeckService(deckCount);
        deck = deckService.getCardDeck();
        counter = 0;
    }
    public Card drawCard(){
        return deck.getCardDeckList().get(counter++);
    }
    public void clearLists(){
        playerCardList = new ArrayList<>();
        dealerCardList = new ArrayList<>();
    }
    public void startRound(){
        clearLists();
        playerCardList.add(drawCard());
        playerCardList.add(drawCard());
        dealerCardList.add(drawCard());
        dealerCardList.add(drawCard());
        dealerCardList.add(drawCard());
    }
    public void playerHit(){
        playerCardList.add(drawCard());
    }
    public void playerStand(){

    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public CardDeck getDeck() {
        return deck;
    }

    public void setDeck(CardDeck deck) {
        this.deck = deck;
    }
    public void printCards(){
        System.out.println("Dealer cards:");
        dealerCardList.forEach(card -> System.out.println(card.getCode() + " " + card.getSuit() + " " + card.getValue()));
        System.out.println("Your cards:");
        playerCardList.forEach(card -> System.out.println(card.getCode() + " " + card.getSuit() + " " + card.getValue()));
    }
}
