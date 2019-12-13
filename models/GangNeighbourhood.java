package viceCity.models;

import viceCity.models.guns.Gun;
import viceCity.models.neighbourhood.Neighbourhood;
import viceCity.models.players.Player;

import java.util.Collection;

public class GangNeighbourhood implements Neighbourhood {
    @Override
    public void action(Player mainPlayer, Collection<Player> civilPlayers) {
        for (Gun gun : mainPlayer.getGunRepository().getModels()) {
            for (Player civilPlayer : civilPlayers) {
                if (!gun.canFire()){
                    break;
                }

                while (civilPlayer.isAlive() && gun.canFire()){
                    civilPlayer.takeLifePoints(gun.fire());
                }
            }
        }

        for (Player civilPlayer : civilPlayers) {
            if (!mainPlayer.isAlive()){
                break;
            }
            if (!civilPlayer.isAlive()){
                continue;
            }
            for (Gun gun : civilPlayer.getGunRepository().getModels()) {
                if (!gun.canFire()){
                    break;
                }
                while (mainPlayer.isAlive() && gun.canFire()){
                    mainPlayer.takeLifePoints(gun.fire());
                }

            }
        }

    }
}
