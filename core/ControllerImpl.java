package viceCity.core;

import viceCity.common.ConstantMessages;
import viceCity.core.interfaces.Controller;
import viceCity.models.*;
import viceCity.models.guns.Gun;
import viceCity.models.neighbourhood.Neighbourhood;
import viceCity.models.players.Player;
import viceCity.repositories.GunRepository;
import viceCity.repositories.interfaces.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller {
    private List<Player> civilPlayers;
    private GunRepository gunRepository;
    private MainPlayer mainPlayer;
    private Neighbourhood neighbourhood;

    public ControllerImpl() {
        this.civilPlayers = new ArrayList<>();
        this.gunRepository = new GunRepository();
        this.mainPlayer = new MainPlayer();
        this.neighbourhood = new GangNeighbourhood();
    }

    @Override
    public String addPlayer(String name) {
        Player player = new CivilPlayer(name);
        this.civilPlayers.add(player);
        return String.format(ConstantMessages.PLAYER_ADDED, name);
    }

    @Override
    public String addGun(String type, String name) {
        BaseGun gun = null;
        switch (type) {
            case "Pistol":
                gun = new Pistol(name);
                break;
            case "Rifle":
                gun = new Rifle(name);
                break;
        }
        if (gun != null) {
            this.gunRepository.add(gun);
            return String.format(ConstantMessages.GUN_ADDED, name, type);
        }
        return String.format(ConstantMessages.GUN_TYPE_INVALID);
    }

    @Override
    public String addGunToPlayer(String name) {
        if (gunRepository.getModels().size() == 0) {
            return String.format(ConstantMessages.GUN_QUEUE_IS_EMPTY);
        }
        if (name.equals("Vercetti")) {
            List<Gun> collect = gunRepository.getModels().stream().collect(Collectors.toList());
            Gun gun = collect.get(0);
            mainPlayer.getGunRepository().add(collect.get(0));
            gunRepository.remove(gun);
            return String.format(ConstantMessages.GUN_ADDED_TO_MAIN_PLAYER, gun.getName(), "Tommy Vercetti");
        } else {
            Player civil = civilPlayers.stream().filter(c -> c.getName().equals(name)).findFirst().orElse(null);
            if (civil == null) {
                return String.format(ConstantMessages.CIVIL_PLAYER_DOES_NOT_EXIST);
            }
            List<Gun> collect = gunRepository.getModels().stream().collect(Collectors.toList());
            Gun gun = collect.get(0);
            civil.getGunRepository().add(gun);
            gunRepository.remove(gun);
            return String.format(ConstantMessages.GUN_ADDED_TO_CIVIL_PLAYER, gun.getName(), civil.getName());
        }


    }

    @Override
    public String fight() {
        int size = (int) civilPlayers.stream().filter(Player::isAlive).count();
        neighbourhood.action(mainPlayer, civilPlayers);
        int sizeAfter = (int) civilPlayers.stream().filter(Player::isAlive).count();
        if (mainPlayer.getLifePoints() == 100 && size == sizeAfter) {
            return String.format(ConstantMessages.FIGHT_HOT_HAPPENED);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(ConstantMessages.FIGHT_HAPPENED)
                .append(System.lineSeparator());
        sb.append(String.format(ConstantMessages.MAIN_PLAYER_LIVE_POINTS_MESSAGE, mainPlayer.getLifePoints()));
        sb.append(System.lineSeparator());
        sb.append(String.format(ConstantMessages.MAIN_PLAYER_KILLED_CIVIL_PLAYERS_MESSAGE, size - sizeAfter));
        sb.append(System.lineSeparator());
        sb.append(String.format(ConstantMessages.CIVIL_PLAYERS_LEFT_MESSAGE, sizeAfter));
        return sb.toString();
    }
}
