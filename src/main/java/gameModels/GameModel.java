package gameModels;

import websocket.controller.CardGuildType;
import websocket.message.GameEvent;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class GameModel {
    private final ArrayList<FieldModel> fieldsModels;
    private final ArrayList<PlayerModel> playerModels;


    public GameModel() {
        this.fieldsModels = new ArrayList<>();
        this.playerModels = new ArrayList<>();
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

    public void activateCreature(PlayerModel playerModel, ArrayList<FieldModel> fieldModel, boolean attack, int position, int target) {
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
                        }
                        fieldModel.get(1).addCreatures(playerModel, attackerCreature);
                        fieldModel.get(0).removeCreature(playerModel, attackerCreature);
                        defenderCreature = fieldModel.get(1).getFirstCreature(getOpponentPlayerModel(playerModel));
                        if (defenderCreature.applyDamage(1)) { //Нанести 1 урона первому элементалю в локации 1
                            fieldModel.get(1).removeCreature(getOpponentPlayerModel(playerModel), defenderCreature);
                            if (defenderCreature.getType() == CardGuildType.CRYSTAL)
                                playerModel.addPoints(2);
                            else playerModel.addPoints(1);
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
                            } //Если на последнем существе в локации
                        }
                        else {
                            if (defenderCreature.applyDamage(4)) {
                                fieldModel.get(0).removeCreature(getOpponentPlayerModel(playerModel), defenderCreature);
                                if (defenderCreature.getType() == CardGuildType.CRYSTAL)
                                    playerModel.addPoints(2);
                                else playerModel.addPoints(1);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case WIND:
                    try {

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
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case EARTH:
                    try {
                        if (defenderCreature.applyDamage(2)) {
                            fieldModel.get(0).removeCreature(getOpponentPlayerModel(playerModel), defenderCreature);
                            if (defenderCreature.getType() == CardGuildType.CRYSTAL)
                                playerModel.addPoints(2);
                            else playerModel.addPoints(1);
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
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case LIGHT:
                    try {
                        if (defenderCreature.applyDamage(2)) {
                            fieldModel.get(0).removeCreature(getOpponentPlayerModel(playerModel), defenderCreature);
                            if (defenderCreature.getType() == CardGuildType.CRYSTAL)
                                playerModel.addPoints(3);
                            else playerModel.addPoints(2);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    public void activateCreature(PlayerModel playerModel, ArrayList<FieldModel> fieldModel, boolean attack, int position, int[] target) {
        try {
            int counter = 0;
            CreatureModel attackerCreature = fieldModel.get(0).getCreature(playerModel, position);
            if (attackerCreature.getType() == CardGuildType.THUNDERBOLT) {
                CreatureModel defenderCreature = fieldModel.get(0).getCreature(getOpponentPlayerModel(playerModel), target[counter]);
                while (defenderCreature.applyDamage(2)) {
                    if (defenderCreature.getType() == CardGuildType.CRYSTAL)
                        playerModel.addPoints(3);
                    else playerModel.addPoints(2);
                    defenderCreature = fieldModel.get(0).getCreature(getOpponentPlayerModel(playerModel), target[counter]);
                    counter++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void summonCreature() {

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
}
