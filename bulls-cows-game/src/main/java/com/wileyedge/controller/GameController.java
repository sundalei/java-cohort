package com.wileyedge.controller;

import com.wileyedge.model.GameDTO;
import com.wileyedge.model.Guess;
import com.wileyedge.model.Round;
import com.wileyedge.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("begin")
    @ResponseStatus(HttpStatus.CREATED)
    public GameDTO begin() {
        return gameService.generateGame();
    }

    @PostMapping("guess")
    public Round guess(@RequestBody Guess guess) {
        return gameService.guess(guess);
    }
}
