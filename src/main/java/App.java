import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Scanner;

public class App {
    private Scanner scanner;
    private Game game;

    App(){
        scanner = new Scanner(System.in);
        game = new Game();
    }

    public void menu() throws IOException, ParseException {
        System.out.println("How many decks of cards?:");
        game.startNewGame(scanner.nextInt());
        System.out.println("Current money: "+game.getPlayerMoney() +"$\nHow much are you betting?:");
        game.startRound(scanner.nextInt());
        game.printCards();
        round();
    }
    public void round(){
        System.out.println("Your money: " + game.getPlayerMoney());
        if(game.canContinue()){
            System.out.println("Stand or hit?");
            scanner.reset();
            switch(scanner.next().toUpperCase()){
                case "STAND":
                    game.playerStand();
                    break;
                case "HIT":
                    game.playerHit();
                    break;
                default:
                    System.out.println("Wrong option, try again");
                    round();
                    break;
            }
            if(game.canContinue()){
                round();
            }else {
                game.checkWin();
                if(game.getPlayerMoney() <= 0){
                    System.out.println("Game over!");
                }else{
                    scanner.reset();
                    System.out.println("Next round, your bet?: ");
                    int bet = scanner.nextInt();
                    game.startRound(bet);
                    game.printCards();
                    round();
                }
            }
        }else{
            game.checkWin();
            if(game.getPlayerMoney() <= 0){
                System.out.println("Game over!");
            }else{
                scanner.reset();
                System.out.println("Next round, your bet?: ");
                int bet = scanner.nextInt();
                game.startRound(bet);
                game.printCards();
                round();
            }
        }
    }
}
