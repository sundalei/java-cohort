package com.wileyedge.dao;

import com.wileyedge.model.GameDTO;
import com.wileyedge.model.Guess;
import com.wileyedge.model.Round;

public interface GameDao {

    GameDTO generateGame();

    Round guess(Guess guess);
}
