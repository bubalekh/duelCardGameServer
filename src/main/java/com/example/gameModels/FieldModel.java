package com.example.gameModels;

import com.example.websocket.controller.PlayerType;

import java.util.LinkedList;

public class FieldModel {

    private final int id;
    private final LinkedList<CreatureModel> player1Creatures;
    private final LinkedList<CreatureModel> player2Creatures;

    public FieldModel(int id) {
        this.id = id;
        player1Creatures = new LinkedList<>();
        player2Creatures = new LinkedList<>();
    }

    public void addCreatures(PlayerModel playerModel, CreatureModel creature) {
        if (playerModel.getType() == PlayerType.FIRST_PLAYER)
            player1Creatures.add(creature);
        else
            player2Creatures.add(creature);
    }

    public void addCreatures(PlayerModel playerModel, LinkedList<CreatureModel> creatures) {
        if (playerModel.getType() == PlayerType.FIRST_PLAYER)
            player1Creatures.addAll(creatures);
        else
            player2Creatures.addAll(creatures);
    }

    public PlayerType isControlledBy() {
        if (player1Creatures.isEmpty() && player2Creatures.isEmpty())
            return PlayerType.NO_PLAYER;
        if (!player1Creatures.isEmpty() && !player2Creatures.isEmpty())
            return PlayerType.NO_PLAYER;
        if (!player1Creatures.isEmpty())
            return PlayerType.FIRST_PLAYER;
        return PlayerType.SECOND_PLAYER;
    }

    public CreatureModel getCreature(PlayerModel playerModel, int position) {
        if (playerModel.getType() == PlayerType.FIRST_PLAYER) {
            try {
                return player1Creatures.get(position);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                return player2Creatures.get(position);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public CreatureModel getFirstCreature(PlayerModel playerModel) {
        if (playerModel.getType() == PlayerType.FIRST_PLAYER) {
            try {
                return player1Creatures.getFirst();
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                return player2Creatures.getFirst();
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public CreatureModel getLastCreature(PlayerModel playerModel) {
        if (playerModel.getType() == PlayerType.FIRST_PLAYER) {
            try {
                return player1Creatures.getLast();
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                return player2Creatures.getLast();
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void removeCreature(PlayerModel playerModel, int position) {
        if (playerModel.getType() == PlayerType.FIRST_PLAYER) {
            try {
                player1Creatures.remove(position);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                player2Creatures.remove(position);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }
    public void removeCreature(PlayerModel playerModel, CreatureModel creature) {
        if (playerModel.getType() == PlayerType.FIRST_PLAYER) {
            try {
                player1Creatures.remove(creature);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                player2Creatures.remove(creature);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }

    public void activateCreature(PlayerModel playerModel, int position) {
        /* switch (this.getCreature(order, position).getType()) {
            case WATER:
                waterGuild.onActivationAction(order, this, position);
                break;
            case FIRE:
                waterGuild.onActivationAction(order, this, position);
                break;
            case ICE:
                waterGuild.onActivationAction(order, this, position);
                break;
            case WIND:
                waterGuild.onActivationAction(order, this, position);
                break;
            case CRYSTAL:
                waterGuild.onActivationAction(order, this, position);
                break;
            case EARTH:
                waterGuild.onActivationAction(order, this, position);
                break;
            case SHADOW:
                waterGuild.onActivationAction(order, this, position);
                break;
            case PLANTS:
                waterGuild.onActivationAction(order, this, position);
                break;
            case LIGHT:
                waterGuild.onActivationAction(order, this, position);
                break;
            case THUNDERBOLT:
                waterGuild.onActivationAction(order, this, position);
                break;
        } */
    }

    public int getId() {
        return id;
    }

    public LinkedList<CreatureModel> getPlayerCreatures(PlayerModel playerModel) {
        if (playerModel.getType() == PlayerType.FIRST_PLAYER) {
            try {
                return player1Creatures;
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                return player2Creatures;
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
