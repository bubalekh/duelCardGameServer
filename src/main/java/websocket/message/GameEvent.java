package websocket.message;

import gameModels.FieldModel;
import websocket.controller.CardController;

import java.util.ArrayList;

public class GameEvent {
    private EventType type;
    private ArrayList<CardController> hand;
    private ArrayList<ArrayList<FieldModel>> fields;
}
