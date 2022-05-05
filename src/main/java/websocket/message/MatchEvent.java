package websocket.message;

import websocket.controller.CardController;
import websocket.controller.CreatureController;

import java.util.LinkedList;

public class MatchEvent {
    private EventType type;
    private LinkedList<CardController> cards;
    private LinkedList<CreatureController> creatures;

    public MatchEvent() {
        super();
    }

    public MatchEvent(EventType type, LinkedList<CardController> cards, LinkedList<CreatureController> creatures) {
        this.type = type;
        this.cards = cards;
        this.creatures = creatures;
    }

    public MatchEvent(EventType error) {
        this.type = error;
    }

    public EventType getType() {
        return type;
    }

    public LinkedList<CardController> getCards() {
        return cards;
    }

    public LinkedList<CreatureController> getCreatures() {
        return creatures;
    }
}
