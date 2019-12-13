package viceCity.models;

public class Pistol extends BaseGun {
    private static final int BULLETS_PER_BARREL = 10;
    private static final int TOTAL_BULLETS = 100;
    private static final int SHOT_BULLETS = 1;


    public Pistol(String name) {
        super(name, BULLETS_PER_BARREL, TOTAL_BULLETS);
    }

    @Override
    public int fire() {
        if (canFire()) {
            if (getBulletsPerBarrel() == 0) {
                reload();
            }
            this.setBulletsPerBarrel(this.getBulletsPerBarrel() - SHOT_BULLETS);
            return SHOT_BULLETS;
        }
        return 0;
    }

}
