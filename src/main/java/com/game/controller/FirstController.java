package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerServiceImpl;
import com.game.service.Specificator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/players")
public class FirstController {

    private PlayerServiceImpl playerServiceImpl;

    @Autowired
    public void setPlayerServiceImpl(PlayerServiceImpl playerServiceImpl) {
        this.playerServiceImpl = playerServiceImpl;
    }

    @GetMapping()
    public ResponseEntity<List<Player>> showAllPlayers(@RequestParam(value = "name", required = false) String name,
                                                       @RequestParam(value = "title", required = false) String title,
                                                       @RequestParam(value = "race", required = false) Race race,
                                                       @RequestParam(value = "profession", required = false) Profession profession,
                                                       @RequestParam(value = "after", required = false) Long after,
                                                       @RequestParam(value = "before", required = false) Long before,
                                                       @RequestParam(value = "banned", required = false) Boolean banned,
                                                       @RequestParam(value = "minExperience", required = false) Integer minExperience,
                                                       @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
                                                       @RequestParam(value = "minLevel", required = false) Integer minLevel,
                                                       @RequestParam(value = "maxLevel", required = false) Integer maxLevel,
                                                       @RequestParam(value = "order", required = false) PlayerOrder order,
                                                       @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                       @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        Pageable pageable = PageRequest.of((pageNumber == null) ? 0 : pageNumber,
                                                        (pageSize == null) ? 3 : pageSize,
                                                        Sort.by(order == null ? "id" : order.getFieldName()));

        return  new ResponseEntity<>(playerServiceImpl.findAllPlayers(
                Specification.where(Specificator.hasName(name))
                        .and(Specificator.hasTitle(title))
                        .and(Specificator.hasRace(race))
                        .and(Specificator.hasProfession(profession))
                        .and(Specificator.hasBefore(before))
                        .and(Specificator.hasAfter(after))
                        .and(Specificator.isBanned(banned))
                        .and(Specificator.hasMinExperience(minExperience))
                        .and(Specificator.hasMaxExperience(maxExperience))
                        .and(Specificator.hasMinLevel(minLevel))
                        .and(Specificator.hasMaxLevel(maxLevel)),
                pageable).getContent(), HttpStatus.OK);

    }

    @GetMapping("/count")
    public Long countAllPlayers(@RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "title", required = false) String title,
                                   @RequestParam(value = "race", required = false) Race race,
                                   @RequestParam(value = "profession", required = false) Profession profession,
                                   @RequestParam(value = "after", required = false) Long after,
                                   @RequestParam(value = "before", required = false) Long before,
                                   @RequestParam(value = "banned", required = false) Boolean banned,
                                   @RequestParam(value = "minExperience", required = false) Integer minExperience,
                                   @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
                                   @RequestParam(value = "minLevel", required = false) Integer minLevel,
                                   @RequestParam(value = "maxLevel", required = false) Integer maxLevel) {

        return playerServiceImpl.countPlayers(Specification.where(Specificator.hasName(name))
                .and(Specificator.hasTitle(title))
                .and(Specificator.hasRace(race))
                .and(Specificator.hasProfession(profession))
                .and(Specificator.hasBefore(before))
                .and(Specificator.hasAfter(after))
                .and(Specificator.isBanned(banned))
                .and(Specificator.hasMinExperience(minExperience))
                .and(Specificator.hasMaxExperience(maxExperience))
                .and(Specificator.hasMinLevel(minLevel))
                .and(Specificator.hasMaxLevel(maxLevel)));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable Long id) {
        if (id == null) {
            return new ResponseEntity<Player>(HttpStatus.BAD_REQUEST);
        }

        Player player = this.playerServiceImpl.getPlayerById(id);

        if(player == null) {
            return new ResponseEntity<Player>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Player>(player, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Player> savePlayer(@RequestBody Player player) {

        if (player == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        this.playerServiceImpl.createPlayer(player);
        return new ResponseEntity<>(player, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Player> deletePlayer(@PathVariable Long id) {
        Player player = this.playerServiceImpl.getPlayerById(id);

        if (player == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        this.playerServiceImpl.deletePlayer(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Player> updatePlayer(@PathVariable Long id, @RequestBody Player oldPlayer) {

        if (oldPlayer == null || id == null) return new ResponseEntity<>(playerServiceImpl.getPlayerById(id), HttpStatus.OK);


        Player player = this.playerServiceImpl.updatePlayer(playerServiceImpl.getPlayerById(id), oldPlayer);

        return new ResponseEntity<>(player, HttpStatus.OK);
    }
}
