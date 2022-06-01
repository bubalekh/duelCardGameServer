package com.example.gameModels;

import java.util.LinkedList;

public class HandModel {
    LinkedList<CreatureModel> hand;

    public HandModel() {
        this.hand = new LinkedList<>();
    }

    public void addToHand(CreatureModel creature) {
        if (this.hand.size() < 8)
            this.hand.add(creature);
    }

    public CreatureModel getCard(int id) {
        try {
            CreatureModel tmp = new CreatureModel(hand.get(id).getType(), hand.get(id).getMaxPower());
            hand.remove(id);
            return tmp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void discardCard(int cardId) {
        try {
            hand.remove(cardId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void discardCard(CreatureModel card) {
        try {
            hand.remove(card);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
