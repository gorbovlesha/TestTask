package com.game.service;

import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.exception.BadRequestException;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Checker {

    private static final Integer NAME_LENGTH = 12;
    private static final Integer TITLE_LENGTH = 30;
    private static final Integer MAX_EXPERIENCE = 10000000;
    private static final long MIN_SIZE_BIRTHDAY = new GregorianCalendar(2000, Calendar.JANUARY, 1).getTimeInMillis();
    private static final long MAX_SIZE_BIRTHDAY = new GregorianCalendar(3001, Calendar.JANUARY, 1).getTimeInMillis();


    public static void checkIsIDPresent(Long id) {
        if (id == null || id <= 0) throw new BadRequestException("Id is invalid");
    }
    public static void checkIsNamePresent(String name) {
        if (name == null || name.isEmpty() || name.length() > NAME_LENGTH) {
            throw new BadRequestException("Name is invalid");
        }
    }
    public static void checkIsTitlePresent(String title) {
        if (title == null || title.isEmpty() || title.length() > TITLE_LENGTH) {
            throw new BadRequestException("Title is invalid");
        }
    }
    public static void checkIsProfessionPresent(Profession profession) {
        if (profession == null) {
            throw new BadRequestException("Profession is invalid");
        }
    }
    public static void checkIsRacePresent(Race race) {
        if (race == null) {
            throw new BadRequestException("Race is invalid");
        }
    }
    public static void checkIsExperiencePresent(Integer experience) {
        if (experience == null || experience < 0 || experience > MAX_EXPERIENCE) {
            throw new BadRequestException("Experience is invalid");
        }
    }
    public static void checkIsDatePresent(Date birthday) {
        if(birthday == null)
            throw new BadRequestException("Birthday is invalid");

        if (birthday.getTime() < MIN_SIZE_BIRTHDAY || birthday.getTime() > MAX_SIZE_BIRTHDAY)
            throw new BadRequestException("Birthday is invalid");
    }
}
