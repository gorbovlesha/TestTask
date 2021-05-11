package com.game.service;

import com.game.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface PlayerService {
    Page<Player> findAllPlayers(Specification<Player> specification, Pageable pageable);

    Long countPlayers(Specification<Player> specification);

    Player createPlayer(Player player);

    Player updatePlayer(Player newPlayer, Player oldPlayer);

    void deletePlayer(Long id);

    Player getPlayerById(Long id);


}
