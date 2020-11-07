package battleship;

public enum State {

    LOCATION("Wrong ship location!"),
    LENGTH("Wrong length of the ship!"),
    CLOSE("You placed it too close to another one."),
    CORRECT(""),
    MISSED("You missed!"),
    HIT("You hit a ship!"),
    SANK("You sank a ship!");

    private final String text;

    State(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
