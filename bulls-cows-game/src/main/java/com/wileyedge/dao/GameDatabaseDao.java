package com.wileyedge.dao;

import com.wileyedge.model.Game;
import com.wileyedge.model.GameDTO;
import com.wileyedge.model.Guess;
import com.wileyedge.model.Round;
import com.wileyedge.util.GameAnswerUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class GameDatabaseDao implements GameDao {

    private static Logger LOGGER = LoggerFactory.getLogger(GameDatabaseDao.class);

    private final JdbcTemplate jdbcTemplate;

    public GameDatabaseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public GameDTO generateGame() {

        final String INSERT_STRING = "INSERT INTO game (answer) VALUES (?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement =
                    connection.prepareStatement(INSERT_STRING, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, GameAnswerUtil.generateAnswer());
            return statement;
        }, keyHolder);
        int id = keyHolder.getKey().intValue();

        GameDTO gameDTO = new GameDTO();
        gameDTO.setGameId(id);
        return gameDTO;
    }

    @Override
    @Transactional
    public Round guess(Guess guess) {
        
        final String QUERY_GAME_STRING = "SELECT * FROM game WHERE id = ?";
        final String UPDATE_GAME_STRING = "UPDATE game SET time = ? WHERE id = ?";

        Game game = jdbcTemplate.queryForObject(QUERY_GAME_STRING, new GameMapper(), guess.getGameId());

        String result = GameAnswerUtil.evaluate(guess.getGuess(), game.getAnswer());

        Round round = new Round();
        round.setRound(game.getTime() + 1);
        round.setResult(result);

        jdbcTemplate.update(UPDATE_GAME_STRING, round.getRound(), guess.getGameId());

        return round;
    }

    private static final class GameMapper implements RowMapper<Game> {

        @Override
        @Nullable
        public Game mapRow(ResultSet rs, int rowNum) throws SQLException {

            Game game = new Game();

            game.setId(rs.getInt("id"));
            game.setAnswer(rs.getString("answer"));
            game.setTime(rs.getInt("time"));
            game.setFinished(rs.getBoolean("finished"));

            return game;
        }

    }
}
