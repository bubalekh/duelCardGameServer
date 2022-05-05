package gameModels;

import websocket.controller.CardGuildType;

import java.util.LinkedList;

public class GuildModel {

    private final CardGuildType type;
    private final LinkedList<CreatureModel> creatures;

    public GuildModel(CardGuildType type) {
        this.type = type;

        creatures = new LinkedList<>();
        creatures.add(new CreatureModel(type, 5));
        creatures.add(new CreatureModel(type, 5));
        creatures.add(new CreatureModel(type, 5));
        creatures.add(new CreatureModel(type, 5));
        creatures.add(new CreatureModel(type, 6));
        creatures.add(new CreatureModel(type, 6));
        creatures.add(new CreatureModel(type, 6));
        creatures.add(new CreatureModel(type, 7));
        creatures.add(new CreatureModel(type, 7));
    }

    public CardGuildType getType() {
        return type;
    }

    public LinkedList<CreatureModel> getDeck() {
        return creatures;
    }
}
