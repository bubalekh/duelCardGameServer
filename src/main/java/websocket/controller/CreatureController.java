package websocket.controller;

import java.util.ArrayList;

public class CreatureController {
    private boolean attack;
    private ArrayList<LocationController> locations;

    public CreatureController() {
        super();
    }

    public CreatureController(boolean attack, ArrayList<LocationController> locations) {
        this.attack = attack;
        this.locations = locations;
    }

    public boolean isAttack() {
        return attack;
    }

    public ArrayList<LocationController> getLocations() {
        return locations;
    }
}
