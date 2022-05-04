package websocket.controller;

public class LocationController {
    private int field;
    private int position;

    public LocationController() {
        super();
    }

    public LocationController(int field, int position) {
        this.field = field;
        this.position = position;
    }

    public int getField() {
        return field;
    }

    public int getPosition() {
        return position;
    }
}
