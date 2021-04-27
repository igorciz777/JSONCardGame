import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        DeckService service = new DeckService();
        CardDeck deck = service.getCardDeck();
        deck.getCardDeckList().forEach(System.out::println);
    }
}
