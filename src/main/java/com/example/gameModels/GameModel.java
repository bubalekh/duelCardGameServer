package com.example.gameModels;

import com.example.websocket.controller.CardGuildType;
import com.example.websocket.controller.PlayerType;
import com.example.websocket.message.GameEvent;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class GameModel {
    private final ArrayList<FieldModel> fieldsModels;
    private final ArrayList<PlayerModel> playerModels;


    public GameModel() {
        this.fieldsModels = new ArrayList<>();
        this.playerModels = new ArrayList<>();
        //Adding fields to the game
        fieldsModels.add(new FieldModel(0));
        fieldsModels.add(new FieldModel(1));
        fieldsModels.add(new FieldModel(2));
        fieldsModels.add(new FieldModel(3));
        fieldsModels.add(new FieldModel(4));
    }

    public void addPlayer(PlayerModel playerModel) {
        try {
            this.playerModels.add(playerModel);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public GameEvent generateServerEvent() {
        return new GameEvent();
    }

    public void activateCreature(PlayerModel playerModel, CreatureModel creatureModel, ArrayList<FieldModel> fieldModel, boolean attack, int position, int target) {
        playerModel.addToGraveyard(creatureModel);
        playerModel.getHandModel().discardCard(creatureModel);
        if (attack) {
            CreatureModel defenderCreature;
            if (target == -1) {
                defenderCreature = fieldModel.get(0).getLastCreature(getOpponentPlayerModel(playerModel));
            } else {
                defenderCreature = fieldModel.get(0).getCreature(getOpponentPlayerModel(playerModel), target);
            }
            CreatureModel attackerCreature = fieldModel.get(0).getCreature(playerModel, position);
            switch (attackerCreature.getType()) {

                case WATER:
                    try {
                        if (defenderCreature.applyDamage(2)) { //Нанести 2 урона первому элементалю в локации 0
                            fieldModel.get(0).removeCreature(getOpponentPlayerModel(playerModel), defenderCreature);
                            if (defenderCreature.getType() == CardGuildType.CRYSTAL)
                                playerModel.addPoints(2);
                            else playerModel.addPoints(1);
                            getOpponentPlayerModel(playerModel).addToGraveyard(defenderCreature);
                        }
                        fieldModel.get(1).addCreatures(playerModel, attackerCreature);
                        fieldModel.get(0).removeCreature(playerModel, attackerCreature);
                        defenderCreature = fieldModel.get(1).getFirstCreature(getOpponentPlayerModel(playerModel));
                        if (defenderCreature.applyDamage(1)) { //Нанести 1 урона первому элементалю в локации 1
                            fieldModel.get(1).removeCreature(getOpponentPlayerModel(playerModel), defenderCreature);
                            if (defenderCreature.getType() == CardGuildType.CRYSTAL)
                                playerModel.addPoints(2);
                            else playerModel.addPoints(1);
                            getOpponentPlayerModel(playerModel).addToGraveyard(defenderCreature);
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    break;
                case FIRE:
                    try {
                        if (defenderCreature.applyDamage(3)) {
                            fieldModel.get(0).removeCreature(getOpponentPlayerModel(playerModel), defenderCreature);
                            if (defenderCreature.getType() == CardGuildType.CRYSTAL)
                                playerModel.addPoints(2);
                            else playerModel.addPoints(1);
                            getOpponentPlayerModel(playerModel).addToGraveyard(defenderCreature);
                        }
                        fieldModel.get(0).getCreature(playerModel, attackerCreature.getId() + 1).applyDamage(1); //Нанести 1 единицу урона союзнику, стоящему за огнем
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    break;
                case ICE:
                    try {
                        defenderCreature = fieldModel.get(0).getLastCreature(getOpponentPlayerModel(playerModel));
                        if (defenderCreature.getCurrentPower() == defenderCreature.getMaxPower()) {
                            if (defenderCreature.applyDamage(1)) {
                                fieldModel.get(0).removeCreature(getOpponentPlayerModel(playerModel), defenderCreature);
                                if (defenderCreature.getType() == CardGuildType.CRYSTAL)
                                    playerModel.addPoints(2);
                                else playerModel.addPoints(1);
                                getOpponentPlayerModel(playerModel).addToGraveyard(defenderCreature);
                            } //Если на последнем существе в локации
                        }
                        else {
                            if (defenderCreature.applyDamage(4)) {
                                fieldModel.get(0).removeCreature(getOpponentPlayerModel(playerModel), defenderCreature);
                                if (defenderCreature.getType() == CardGuildType.CRYSTAL)
                                    playerModel.addPoints(2);
                                else playerModel.addPoints(1);
                                getOpponentPlayerModel(playerModel).addToGraveyard(defenderCreature);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case WIND:
                    try {
                        // Move elemental from field 0 to field 1
                        fieldModel.get(1).addCreatures(playerModel, attackerCreature);
                        fieldModel.get(0).removeCreature(playerModel, attackerCreature);
                        defenderCreature = fieldsModels.get(fieldModel.get(1).getId() - 1).getFirstCreature(getOpponentPlayerModel(playerModel));
                        if (defenderCreature.applyDamage(1)) {
                            fieldsModels.get(fieldModel.get(0).getId() - 1).removeCreature(getOpponentPlayerModel(playerModel), defenderCreature);
                            if (defenderCreature.getType() == CardGuildType.CRYSTAL)
                                playerModel.addPoints(2);
                            else playerModel.addPoints(1);
                            getOpponentPlayerModel(playerModel).addToGraveyard(defenderCreature);
                        }
                        defenderCreature = fieldsModels.get(fieldModel.get(1).getId()).getFirstCreature(getOpponentPlayerModel(playerModel));
                        if (defenderCreature.applyDamage(1)) {
                            fieldsModels.get(fieldModel.get(0).getId()).removeCreature(getOpponentPlayerModel(playerModel), defenderCreature);
                            if (defenderCreature.getType() == CardGuildType.CRYSTAL)
                                playerModel.addPoints(2);
                            else playerModel.addPoints(1);
                            getOpponentPlayerModel(playerModel).addToGraveyard(defenderCreature);
                        }
                        defenderCreature = fieldsModels.get(fieldModel.get(1).getId() + 1).getFirstCreature(getOpponentPlayerModel(playerModel));
                        if (defenderCreature.applyDamage(1)) {
                            fieldsModels.get(fieldModel.get(0).getId()).removeCreature(getOpponentPlayerModel(playerModel), defenderCreature);
                            if (defenderCreature.getType() == CardGuildType.CRYSTAL)
                                playerModel.addPoints(2);
                            else playerModel.addPoints(1);
                            getOpponentPlayerModel(playerModel).addToGraveyard(defenderCreature);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case CRYSTAL:
                    try {
                        if (defenderCreature.applyDamage(4)) {
                            fieldModel.get(0).removeCreature(getOpponentPlayerModel(playerModel), defenderCreature);
                            if (defenderCreature.getType() == CardGuildType.CRYSTAL)
                                playerModel.addPoints(2);
                            else playerModel.addPoints(1);
                            getOpponentPlayerModel(playerModel).addToGraveyard(defenderCreature);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case EARTH:
                case LIGHT:
                    try {
                        if (defenderCreature.applyDamage(2)) {
                            fieldModel.get(0).removeCreature(getOpponentPlayerModel(playerModel), defenderCreature);
                            if (defenderCreature.getType() == CardGuildType.CRYSTAL)
                                playerModel.addPoints(2);
                            else playerModel.addPoints(1);
                            getOpponentPlayerModel(playerModel).addToGraveyard(defenderCreature);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case SHADOW:
                    try {
                        if (defenderCreature.applyDamage(1)) {
                            fieldModel.get(0).removeCreature(getOpponentPlayerModel(playerModel), defenderCreature);
                            if (defenderCreature.getType() == CardGuildType.CRYSTAL)
                                playerModel.addPoints(3);
                            else playerModel.addPoints(2);
                            getOpponentPlayerModel(playerModel).addToGraveyard(defenderCreature);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case PLANTS:
                    try {
                        defenderCreature = fieldModel.get(1).getFirstCreature(playerModel);
                        if (defenderCreature.applyDamage(2)) {
                            fieldModel.get(0).removeCreature(getOpponentPlayerModel(playerModel), defenderCreature);
                            if (defenderCreature.getType() == CardGuildType.CRYSTAL)
                                playerModel.addPoints(2);
                            else playerModel.addPoints(1);
                            getOpponentPlayerModel(playerModel).addToGraveyard(defenderCreature);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
        else {
            CreatureModel attackerCreature =  fieldModel.get(0).getCreature(playerModel, position);
            if (attackerCreature.getType() == CardGuildType.LIGHT) {
                for (int i = 0; i < fieldModel.size() - 1; i++){
                    CreatureModel healedCreature = fieldModel.get(i).getCreature(playerModel, target);
                    healedCreature.healDamage();
                }
            }
        }
    }

    public void activateCreature(PlayerModel playerModel, CreatureModel creatureModel, ArrayList<FieldModel> fields, boolean attack, int position, ArrayList<Integer> target) {
        playerModel.addToGraveyard(creatureModel);
        playerModel.getHandModel().discardCard(creatureModel);
        try {
            int counter = 0;
            CreatureModel attackerCreature = fields.get(0).getCreature(playerModel, position);
            if (attackerCreature.getType() == CardGuildType.THUNDERBOLT) {
                CreatureModel defenderCreature = fields.get(0).getCreature(getOpponentPlayerModel(playerModel), target.get(counter));
                while (defenderCreature.applyDamage(2)) {
                    if (defenderCreature.getType() == CardGuildType.CRYSTAL)
                        playerModel.addPoints(2);
                    else playerModel.addPoints(1);
                    getOpponentPlayerModel(playerModel).addToGraveyard(defenderCreature);
                    defenderCreature = fields.get(0).getCreature(getOpponentPlayerModel(playerModel), target.get(counter));
                    counter++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void summonCreature(PlayerModel playerModel, ArrayList<FieldModel> fields, ArrayList<CreatureModel> creatures) {
        try {
            fields.forEach(field -> {
                if (creatures.get(field.getId()).getType() == CardGuildType.EARTH) {
                    fieldsModels.get(field.getId()).getPlayerCreatures(getOpponentPlayerModel(playerModel)).forEach(creatureModel -> {
                        if (creatureModel.applyDamage(1)) {
                            if (creatureModel.getType() == CardGuildType.CRYSTAL)
                                playerModel.addPoints(2);
                            else
                                playerModel.addPoints(1);
                            getOpponentPlayerModel(playerModel).addToGraveyard(creatureModel);
                        }
                    });
                }
                fieldsModels.get(field.getId()).addCreatures(playerModel, creatures.get(field.getId()));
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void check() {

    }

    private PlayerModel getOpponentPlayerModel(PlayerModel playerModel) {
        AtomicReference<PlayerModel> tmp = new AtomicReference<>();
        this.playerModels.forEach(playerModel1 -> {
            if(!playerModel1.equals(playerModel)) {
                tmp.set(playerModel1);
            }
        });
        return tmp.get();
    }

    public PlayerModel getPlayerModel(PlayerType playerType) {
        try {
            return this.playerModels.get(getPlayerIdFromType(playerType));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public FieldModel getField(int fieldId) {
        try {
            return this.fieldsModels.get(fieldId);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getPlayerIdFromType(PlayerType playerType) {
        return playerType == PlayerType.FIRST_PLAYER ? 0 : 1;
    }
}
