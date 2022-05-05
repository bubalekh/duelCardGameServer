package gameModels;

import java.util.LinkedList;

public class HandModel {
    LinkedList<CreatureModel> hand;

    public HandModel() {
        hand = new LinkedList<>();
    }

    public void addToHand(CreatureModel creature) {
        if (hand.size() < 8)
            hand.add(creature);
    }

    public CreatureModel getCreature(int id) {
        try {
            CreatureModel tmp = new CreatureModel(hand.get(id).getType(), hand.get(id).getMaxPower());
            hand.remove(id);
            return tmp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
