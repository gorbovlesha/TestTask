package com.game.service;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.exception.NotFoundException;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.util.Date;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;


    @Override
    public Page<Player> findAllPlayers(Specification<Player> specification, Pageable pageable) {
        return playerRepository.findAll(specification, pageable);
    }

    @Override
    public Long countPlayers(Specification<Player> specification) {
        return playerRepository.count(specification);
    }

    @Override
    public Player createPlayer(Player player) {
        Checker.checkIsNamePresent(player.getName());
        Checker.checkIsTitlePresent(player.getTitle());
        Checker.checkIsRacePresent(player.getRace());
        Checker.checkIsProfessionPresent(player.getProfession());
        Checker.checkIsDatePresent(player.getBirthday());
        Checker.checkIsExperiencePresent(player.getExperience());
        player.setLevel(getPlayersLevel(player.getExperience()));
        player.setUntilNextLevel(getUntilNextLevel(player.getLevel(), player.getExperience()));
        if (player.getBanned() == null) player.setBanned(false);
        return playerRepository.save(player);
    }

    @Override
    public Player updatePlayer(Player updatedPlayer, Player oldPlayer) {
        if (oldPlayer.getName() != null) {
            Checker.checkIsNamePresent(oldPlayer.getName());
            updatedPlayer.setName(oldPlayer.getName());
        }
        if (oldPlayer.getTitle() != null) {
            Checker.checkIsTitlePresent(oldPlayer.getTitle());
            updatedPlayer.setTitle(oldPlayer.getTitle());
        }
        if (oldPlayer.getRace() != null) {
            Checker.checkIsRacePresent((oldPlayer.getRace()));
            updatedPlayer.setRace(oldPlayer.getRace());
        }
        if (oldPlayer.getProfession() != null) {
            Checker.checkIsProfessionPresent(oldPlayer.getProfession());
            updatedPlayer.setProfession(oldPlayer.getProfession());
        }
        if (oldPlayer.getBirthday() != null) {
            Checker.checkIsDatePresent(oldPlayer.getBirthday());
            updatedPlayer.setBirthday(oldPlayer.getBirthday());
        }
        if (oldPlayer.getBanned() != null) {
            updatedPlayer.setBanned(oldPlayer.getBanned());
        }
        if (oldPlayer.getExperience() != null) {
            Checker.checkIsExperiencePresent(oldPlayer.getExperience());
            updatedPlayer.setExperience(oldPlayer.getExperience());
        }
        updatedPlayer.setLevel(getPlayersLevel(updatedPlayer.getExperience()));
        updatedPlayer.setUntilNextLevel(getUntilNextLevel(updatedPlayer.getLevel(), updatedPlayer.getExperience()));
        return playerRepository.save(updatedPlayer);
    }

    @Override
    public void deletePlayer(Long id) {
        Checker.checkIsIDPresent(id);
        playerRepository.delete(this.getPlayerById(id));
    }

    @Override
    public Player getPlayerById(Long id) {
        Checker.checkIsIDPresent(id);
        boolean present = playerRepository.findById(id).isPresent();
        if (!present) throw new NotFoundException();
        return playerRepository.findById(id).get();
    }


    public Integer getPlayersLevel(Integer experience) {
        double d = ((Math.sqrt(2500 + (200*experience))) - 50) / 100;
        return (int) d;
    }
    public Integer getUntilNextLevel(Integer level, Integer experience) {
        return 50 * (level + 1) * (level + 2) - experience;
    }

}

