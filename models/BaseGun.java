package viceCity.models;

import viceCity.common.ExceptionMessages;
import viceCity.models.guns.Gun;

public abstract class BaseGun implements Gun {
    private String name;
    private int bulletsPerBarrel;
    private int totalBullets;
    private int initialBulletsInBarrel;

    protected BaseGun(String name, int bulletsPerBarrel, int totalBullets) {
        this.setName(name);
        this.setBulletsPerBarrel(bulletsPerBarrel);
        this.initialBulletsInBarrel = bulletsPerBarrel;
        this.setTotalBullets(totalBullets);
    }

    private void setTotalBullets(int totalBullets) {
        if (totalBullets < 0) {
            throw new IllegalArgumentException(ExceptionMessages.TOTAL_BULLETS_LESS_THAN_ZERO);
        }
        this.totalBullets = totalBullets;
    }

    protected void setBulletsPerBarrel(int bulletsPerBarrel) {
        if (bulletsPerBarrel < 0) {
            throw new IllegalArgumentException(ExceptionMessages.BULLETS_LESS_THAN_ZERO);
        }
        this.bulletsPerBarrel = bulletsPerBarrel;
    }

    @Override
    public String getName() {
        return this.name;
    }


    private void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException(ExceptionMessages.NAME_NULL);
        }
        this.name = name;
    }

    @Override
    public int getBulletsPerBarrel() {
        return this.bulletsPerBarrel;
    }

    @Override
    public boolean canFire() {
        return bulletsPerBarrel > 0 || totalBullets > 0 ;

    }

    @Override
    public int getTotalBullets() {
        return this.totalBullets;
    }

    protected void reload() {
        this.bulletsPerBarrel += initialBulletsInBarrel;
        this.totalBullets -= initialBulletsInBarrel;
    }
}
