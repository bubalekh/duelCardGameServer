package com.example.riftforceserver;

import gameModels.CreatureModel;
import gameModels.FieldModel;
import gameModels.GameModel;
import gameModels.PlayerModel;
import websocket.controller.CardGuildType;
import websocket.controller.PlayerType;

import java.util.ArrayList;

public class CreaturesTest {

    public static void main(String[] args) {
        GameModel gameModel = new GameModel();
        gameModel.addPlayer(new PlayerModel(PlayerType.FIRST_PLAYER));
        gameModel.addPlayer(new PlayerModel(PlayerType.SECOND_PLAYER));

        //gameModel.getPlayerModel(0).addCreaturesToHand(CardGuildType.WATER);
        gameModel.getPlayerModel(PlayerType.FIRST_PLAYER).addCreaturesToHand(CardGuildType.EARTH);
        //gameModel.getPlayerModel(0).addCreaturesToHand(CardGuildType.LIGHT);
        //gameModel.getPlayerModel(0).addCreaturesToHand(CardGuildType.ICE);

        gameModel.getPlayerModel(PlayerType.SECOND_PLAYER).addCreaturesToHand(CardGuildType.LIGHT);
        //gameModel.getPlayerModel(1).addCreaturesToHand(CardGuildType.SHADOW);
        //gameModel.getPlayerModel(1).addCreaturesToHand(CardGuildType.FIRE);
        //gameModel.getPlayerModel(1).addCreaturesToHand(CardGuildType.THUNDERBOLT);

        for (int i = 0; i < 7; i++) {
            gameModel.getPlayerModel(PlayerType.FIRST_PLAYER).getHandModel().addToHand(gameModel.getPlayerModel(PlayerType.FIRST_PLAYER).getDeckModel().getCard());
        }

        for (int i = 0; i < 7; i++) {
            gameModel.getPlayerModel(PlayerType.SECOND_PLAYER).getHandModel().addToHand(gameModel.getPlayerModel(PlayerType.SECOND_PLAYER).getDeckModel().getCard());
        }

        ArrayList<FieldModel> player2Fields = new ArrayList<>();
        player2Fields.add(gameModel.getField(0));
        player2Fields.add(gameModel.getField(1));
        player2Fields.add(gameModel.getField(2));

        ArrayList<CreatureModel> player2Creatures = new ArrayList<>();
        player2Creatures.add(gameModel.getPlayerModel(PlayerType.SECOND_PLAYER).getHandModel().getCard(0));
        player2Creatures.add(gameModel.getPlayerModel(PlayerType.SECOND_PLAYER).getHandModel().getCard(1));
        player2Creatures.add(gameModel.getPlayerModel(PlayerType.SECOND_PLAYER).getHandModel().getCard(2));


        gameModel.summonCreature(gameModel.getPlayerModel(PlayerType.SECOND_PLAYER), player2Fields, player2Creatures);

        ArrayList<FieldModel> player1Fields = new ArrayList<>();
        player1Fields.add(gameModel.getField(0));
        player1Fields.add(gameModel.getField(1));
        player1Fields.add(gameModel.getField(2));

        ArrayList<CreatureModel> player1Creatures = new ArrayList<>();
        player1Creatures.add(gameModel.getPlayerModel(PlayerType.FIRST_PLAYER).getHandModel().getCard(0));
        player1Creatures.add(gameModel.getPlayerModel(PlayerType.FIRST_PLAYER).getHandModel().getCard(1));
        player1Creatures.add(gameModel.getPlayerModel(PlayerType.FIRST_PLAYER).getHandModel().getCard(2));


        gameModel.summonCreature(gameModel.getPlayerModel(PlayerType.FIRST_PLAYER), player1Fields, player1Creatures);

        player1Fields = new ArrayList<>();
        player1Fields.add(gameModel.getField(0));
        player1Fields.add(gameModel.getField(1));

        player2Fields = new ArrayList<>();
        player2Fields.add(gameModel.getField(0));
        player2Fields.add(gameModel.getField(0));
        player2Fields.add(gameModel.getField(0));

        gameModel.activateCreature(gameModel.getPlayerModel(PlayerType.FIRST_PLAYER), gameModel.getPlayerModel(PlayerType.FIRST_PLAYER).getHandModel().getCard(0), player1Fields, true, 0, 0);
        gameModel.activateCreature(gameModel.getPlayerModel(PlayerType.SECOND_PLAYER), gameModel.getPlayerModel(PlayerType.SECOND_PLAYER).getHandModel().getCard(0), player1Fields, false, 0, 0);

        System.out.println("test");
    }
}
