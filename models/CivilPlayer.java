package viceCity.models;

public class CivilPlayer extends BasePlayer {
    private static final int INITIAL_LIFE = 50;
    public CivilPlayer(String name) {
        super(name, INITIAL_LIFE);
    }
}
