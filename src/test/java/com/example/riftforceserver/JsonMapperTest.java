package com.example.riftforceserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.websocket.controller.*;
import com.example.websocket.message.MatchEvent;
import com.example.websocket.message.EventType;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

public class JsonMapperTest {
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
                    EventType.ACTIVATION,
                    cards,
                    creatures);
            objectMapper.writeValue(new File("target/test.json"), matchEvent);

            MatchEvent gameEvent = objectMapper.readValue(new File("target/test.json"), MatchEvent.class);
            System.out.println(matchEvent.getCreatures().get(0).isAttack() == gameEvent.getCreatures().get(0).isAttack());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws JsonProcessingException {
        new JsonMapperTest();
    }
}
