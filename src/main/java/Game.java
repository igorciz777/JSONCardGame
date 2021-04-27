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
    private int playerMoney;
    private int playerBet;

    public void startNewGame(int deckCount) throws IOException, ParseException {
        deckService = new DeckService(deckCount);
        deck = deckService.getCardDeck();
        counter = 0;
        playerMoney = 1000;
    }
    public Card drawCard(){
        return deck.getCardDeckList().get(counter++);
    }
    public void clearLists(){
        playerCardList = new ArrayList<>();
        dealerCardList = new ArrayList<>();
    }
    public void startRound(int bet){
        clearLists();
        playerBet = bet;
        setPlayerMoney(playerMoney - playerBet);
        playerCardList.add(drawCard());
        playerCardList.add(drawCard());
        dealerCardList.add(drawCard());
        dealerCardList.add(drawCard());
    }
    public void playerHit(){
        playerCardList.add(drawCard());
        printCards();
    }
    public void playerStand(){
        if(getPlayerValue() < getDealerValue()){
            System.out.println("Cant stand");
            playerHit();
        }else {
            dealerHit();
        }
    }
    public void dealerHit(){
        while(getDealerValue() <= getPlayerValue()){
            dealerCardList.add(drawCard());
        }
        printCards();
        checkWin();
    }
    private int getPlayerValue(){
        return playerCardList.stream()
                .mapToInt(Card::getConvertedValue)
                .sum();
    }
    private int getDealerValue(){
        return dealerCardList.stream()
                .mapToInt(Card::getConvertedValue)
                .sum();
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
        dealerCardList.forEach(card -> System.out.println(card.getValue() + " " + card.getSuit()));
        System.out.println("Total value: " + getDealerValue());
        System.out.println("######################################");
        System.out.println("Your cards:");
        playerCardList.forEach(card -> System.out.println(card.getValue() + " " + card.getSuit()));
        System.out.println("Total value: " +  + getPlayerValue());
        System.out.println("######################################");
    }

    public int getPlayerMoney() {
        return playerMoney;
    }

    public void setPlayerMoney(int playerMoney) {
        this.playerMoney = playerMoney;
    }
    public boolean canContinue(){
        if (getPlayerValue() >= 21){
            return false;
        }
        if(getDealerValue() >= 21){
            return false;
        }
        return true;
    }
    public void checkWin(){
        if(getPlayerValue() > 21 || (getPlayerValue() < getDealerValue() && getDealerValue() <= 21)){
            System.err.println("You lose!");
        }
        else if(getPlayerValue() == 21 || getDealerValue() > 21 || getPlayerValue() > getDealerValue()){
            System.out.println("You win!");
            playerMoney += playerBet * 2;
        }
    }
}
