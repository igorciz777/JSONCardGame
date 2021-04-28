import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private DeckService deckService;
    private CardDeck deck;
    private int counter;
    private Scanner scanner = new Scanner(System.in);
    private List<Card> playerCardList;
    private List<Card> dealerCardList;
    private int playerMoney;
    private int playerBet;

    public void start() throws IOException, ParseException {
        System.out.println("How many decks of cards?:");
        startNewGame(scanner.nextInt());
        System.out.println("Current money: "+getPlayerMoney());
        startRound(getPlayerBet());
    }
    private void startNewGame(int deckCount) throws IOException, ParseException {
        deckService = new DeckService(deckCount);
        deck = deckService.getCardDeck();
        counter = 0;
        playerMoney = 1000;
    }
    private Card drawCard(){
        return deck.getCardDeckList().get(counter++);
    }
    private void clearLists(){
        playerCardList = new ArrayList<>();
        dealerCardList = new ArrayList<>();
    }
    private void startRound(int bet){
        clearLists();
        playerBet = bet;
        setPlayerMoney(playerMoney - playerBet);
        playerCardList.add(drawCard());
        playerCardList.add(drawCard());
        dealerCardList.add(drawCard());
        dealerCardList.add(drawCard());
        if(getPlayerValue() >= 21 || getDealerValue() >= 21){
            checkWin();
        }else{continueRound();}
    }
    private void playerHit(){
        playerCardList.add(drawCard());
        printInfo();
        if(getPlayerValue() >= 21){
            checkWin();
        }else{
            continueRound();
        }
    }
    private void playerStand(){
        if(getPlayerValue() < getDealerValue()){
            System.err.println("Cant stand");
            playerHit();
        }else {
            dealerHit();
        }
    }
    private void dealerHit(){
        dealerCardList.add(drawCard());
        printInfo();
        if(getDealerValue() >= 21){
            checkWin();
        }else if(getDealerValue() > getPlayerValue()){
            checkWin();
        }
        else{
            dealerHit();
        }
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
    private void printInfo(){
        System.out.println("Dealer cards:");
        dealerCardList.forEach(card -> System.out.println(card.getValue() + " " + card.getSuit()));
        System.out.println("Total value: " + getDealerValue());
        System.out.println("######################################");
        System.out.println("Your cards:");
        playerCardList.forEach(card -> System.out.println(card.getValue() + " " + card.getSuit()));
        System.out.println("Total value: " +  + getPlayerValue());
        System.out.println("######################################");
        System.out.println("Your money: " + getPlayerMoney());
    }

    public int getPlayerMoney() {
        return playerMoney;
    }

    public void setPlayerMoney(int playerMoney) {
        this.playerMoney = playerMoney;
    }
    public int getPlayerBet(){
        System.out.println("How much do you want to bet?: ");
        return scanner.nextInt();
    }
    private void canContinue(){
        if(deck.getCardDeckList().size() > 4 && getPlayerMoney() > 0){
            startRound(getPlayerBet());
        }
        else{
            System.out.println("Game over!");
        }
    }
    private void checkWin(){
        if(getPlayerValue() > 21 || (getPlayerValue() < getDealerValue() && getDealerValue() <= 21)){
            System.err.println("You lose!");
            System.out.println("Your money: "+getPlayerMoney());
        }
        else if(getPlayerValue() == 21 || getDealerValue() > 21 || getPlayerValue() > getDealerValue()){
            System.out.println("You win!");
            playerMoney += playerBet * 2;
            System.out.println("Your money: "+getPlayerMoney());
        }
        canContinue();
    }
    private void continueRound() {
        printInfo();
        System.out.println("Stand or hit?");
        switch (scanner.next().toUpperCase()) {
            case "STAND":
                playerStand();
                break;
            case "HIT":
                playerHit();
                break;
            default:
                System.out.println("Wrong option, try again");
                continueRound();
                break;
        }
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

    @Override
    public String toString() {
        return "Game{" +
                "deckService=" + deckService +
                ", deck=" + deck +
                ", counter=" + counter +
                ", scanner=" + scanner +
                ", playerCardList=" + playerCardList +
                ", dealerCardList=" + dealerCardList +
                ", playerMoney=" + playerMoney +
                ", playerBet=" + playerBet +
                '}';
    }
}
