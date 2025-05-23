package mivalgamer.app.service;

import mivalgamer.app.Videojuego;
import mivalgamer.app.repository.GameRepository;

import java.sql.SQLException;
import java.util.List;

public class GameService {

    private final GameRepository repository;

    public GameService() {
        repository = new GameRepository();
    }

    public List<Videojuego> getAllVideoGames() throws SQLException {
        return repository.getAll();
    }

    public List<Videojuego> getVideoGamesByPlatform(long platformID) throws SQLException {
        return repository.getByPlatform(platformID);
    }

    public List<Videojuego> getAllInDiscount() throws SQLException {
        return repository.getAllInDiscount();
    }

    public Videojuego getVideoGameByID(long id) throws SQLException {
        return repository.getByID(id);
    }
}
