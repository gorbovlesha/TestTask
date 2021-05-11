package com.game.service;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class Specificator {

    public static Specification<Player> hasName(String name) {
        if (name == null) return null;
        return (r, cq, cb) -> cb.like(r.get("name"), "%" + name + "%");
    }

    public static Specification<Player> hasTitle(String title) {
        if (title == null) return null;
        return (r, cq, cb) -> cb.like(r.get("title"), "%" + title + "%");
    }

    public static Specification<Player> hasRace(Race race) {
        if (race == null) return null;
        return (r, cq, cb) -> cb.equal(r.get("race"), race);
    }

    public static Specification<Player> hasProfession(Profession profession) {
        if (profession == null) return null;
        return (r, cq, cb) -> cb.equal(r.get("profession"), profession);
    }

    public static Specification<Player> hasAfter(Long after) {
        if (after == null) return null;
        return (player, cq, cb) -> cb.greaterThanOrEqualTo(player.get("birthday"), new Date(after));
    }

    public static Specification<Player> hasBefore(Long before) {
        if (before == null) return null;
        return (player, cq, cb) -> cb.lessThanOrEqualTo(player.get("birthday"), new Date(before));
    }

    public static Specification<Player> isBanned(Boolean isBanned) {
        if (isBanned == null) {
            return null;
        }
        return (r, cq, cb) -> {
            if (isBanned) return cb.isTrue(r.get("banned"));
            return cb.isFalse(r.get("banned"));
        };
    }
    public static Specification<Player> hasMinExperience(Integer experience) {
        if (experience == null) return null;
        return (r, cq, cb) -> cb.greaterThanOrEqualTo(r.get("experience"), experience);
    }

    public static Specification<Player> hasMaxExperience(Integer experience) {
        if (experience == null) return null;
        return (r, cq, cb) -> cb.lessThanOrEqualTo(r.get("experience"), experience);
    }

    public static Specification<Player> hasMinLevel(Integer level) {
        if (level == null) return null;
        return (r, cq, cb) -> cb.greaterThanOrEqualTo(r.get("level"), level);
    }

    public static Specification<Player> hasMaxLevel(Integer level) {
        if (level == null) return null;
        return (r, cq, cb) -> cb.lessThanOrEqualTo(r.get("level"), level);
    }
}
