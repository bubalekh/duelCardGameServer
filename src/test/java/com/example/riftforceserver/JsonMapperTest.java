package com.example.riftforceserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import websocket.controller.*;
import websocket.message.MatchEvent;
import websocket.message.MatchEventType;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

public class JsonMapperTest {
    String testJson = "{\"type\":\"activation\"}";

    public JsonMapperTest() throws NullPointerException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            LinkedList<CreatureController> creatures = new LinkedList<>();
            ArrayList<LocationController> locationControllers = new ArrayList<>();
            locationControllers.add(new LocationController(0, 1));
            locationControllers.add(new LocationController(0, 2));
            creatures.add(new CreatureController(true, locationControllers));

            LinkedList<CardController> cards = new LinkedList<>();
            cards.add(new CardController(5, CardGuildType.EARTH));

            MatchEvent matchEvent = new MatchEvent(
                    MatchEventType.ACTIVATION,
                    cards,
                    creatures);
            // MatchEvent matchEvent = new MatchEvent(MatchEventType.READY);
            objectMapper.writeValue(new File("target/test.json"), matchEvent);

            /*GameEvent gameEvent = objectMapper.readValue(testJson, GameEvent.class);
            System.out.println(gameEvent.getType());*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws JsonProcessingException {
        new JsonMapperTest();
    }
}
