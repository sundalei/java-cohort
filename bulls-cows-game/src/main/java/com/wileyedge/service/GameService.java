package com.wileyedge.service;

import com.wileyedge.dao.GameDao;
import com.wileyedge.model.GameDTO;
import com.wileyedge.model.Guess;
import com.wileyedge.model.Round;

import org.springframework.stereotype.Service;

@Service
public class GameService {

    private final GameDao gameDao;

    public GameService(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    public GameDTO generateGame() {
        return gameDao.generateGame();
    }

    public Round guess(Guess guess) {
        return gameDao.guess(guess);
    }
}
