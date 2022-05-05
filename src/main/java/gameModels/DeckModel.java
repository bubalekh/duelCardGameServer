package gameModels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class DeckModel {

    private final LinkedList<CreatureModel> deck;

    public DeckModel() {
        deck = new LinkedList<>();
    }

    public void shuffleDeck() {
        if (!deck.isEmpty())
            Collections.shuffle(deck);
    }

    public void addToDeck(LinkedList<CreatureModel> creatures) {
        try {
            deck.addAll(creatures);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CreatureModel getCard() {
        if (!deck.isEmpty())
            try {
                CreatureModel tmp = new CreatureModel(deck.getFirst().getType(), deck.getFirst().getMaxPower());
                deck.removeFirst();
                return tmp;
            } catch (Exception e) {
                e.printStackTrace();
            }
        return  null;
    }

    public int getDeckSize() {
        return deck.size();
    }
}
