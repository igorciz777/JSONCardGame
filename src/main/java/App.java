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
        game.startRound();
        game.printCards();
    }
}
