package com.example.websocket.message;

import com.example.gameModels.FieldModel;
import com.example.websocket.controller.CardController;

import java.util.ArrayList;

public class GameEvent {
    private EventType type;
    private ArrayList<CardController> hand;
    private ArrayList<ArrayList<FieldModel>> fields;
}
