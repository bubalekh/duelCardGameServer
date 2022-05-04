package websocket.controller;

public class CardController {
    private int power;
    private CardGuildType guild;

    public CardController(int power, CardGuildType guild) {
        this.power = power;
        this.guild = guild;
    }

    public CardController() {
        super();
    }

    public int getPower() {
        return power;
    }

    public CardGuildType getGuild() {
        return guild;
    }
}
