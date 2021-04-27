import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class DeckService {
    private String url = "https://deckofcardsapi.com/api/deck/new/shuffle/?deck_count=6";

    private String getDeckID() throws ParseException, IOException {
        HTTPConnector connector = new HTTPConnector(new URL(url));
        ParseToJSON parser = new ParseToJSON(connector.contentToString());

        JSONObject data_obj = parser.getJsonObject();

        return data_obj.get("deck_id").toString();
    }
    public CardDeck getCardDeck() throws IOException, ParseException {
        CardDeck deck = new CardDeck();
        URL requestUrl = new URL("https://deckofcardsapi.com/api/deck/" + getDeckID() + "/draw/?count=312");

        HTTPConnector connector = new HTTPConnector(requestUrl);
        ParseToJSON parser = new ParseToJSON(connector.contentToString());

        JSONObject data_obj = parser.getJsonObject();
        JSONArray jsonArray = (JSONArray) data_obj.get("cards");

        for(Object obj : jsonArray){
            JSONObject jsonObject = (JSONObject) obj;
            Card card = new Card(jsonObject.get("code").toString(), jsonObject.get("value").toString(),jsonObject.get("suit").toString());
            deck.saveToDeck(card);
        }
        return deck;
    }
}
