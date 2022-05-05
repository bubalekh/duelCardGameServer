package gameModels;

import websocket.controller.CardGuildType;

public class CreatureModel {

    private  final int id;
    private final CardGuildType type;
    private final int maxPower;
    private int currentPower;

    public CreatureModel(CardGuildType type, int maxPower) {
        this.id = (int) ((Math.random() * 65535));
        this.maxPower = maxPower;
        this.currentPower = maxPower;
        this.type = type;
    }

    public int getMaxPower() {
        return maxPower;
    }

    public int getCurrentPower() {
        return currentPower;
    }

    public int getId() {
        return id;
    }

    public CardGuildType getType() {
        return type;
    }

    public void setCurrentPower(int currentPower) {
        this.currentPower = currentPower;
    }

    public boolean applyDamage(int amount) {
        int currentPower = this.getCurrentPower();
        setCurrentPower(currentPower - amount);
        currentPower = this.getCurrentPower();
        return currentPower <= 0;
    }

    public void healDamage(int amount) {
        if (this.getCurrentPower() < this.getMaxPower())
            this.setCurrentPower(this.getCurrentPower() + amount);
    }

    public void healDamage() {
        this.healDamage(1);
    }
}
