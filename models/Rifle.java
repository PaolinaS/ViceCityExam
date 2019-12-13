package viceCity.models;

public class Rifle extends BaseGun {
    private static final int BULLETS_PER_BARREL = 50;
    private static final int TOTAL_BULLETS = 500;
    private static final int SHOT_BULLETS = 5;


    public Rifle(String name) {
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
