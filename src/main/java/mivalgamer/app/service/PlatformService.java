package mivalgamer.app.service;

import mivalgamer.app.Plataforma;
import mivalgamer.app.repository.PlatformRepository;

import java.sql.SQLException;
import java.util.List;

public class PlatformService {

    private final PlatformRepository repository;

    public PlatformService() {
        repository = new PlatformRepository();
    }

    public List<Plataforma> getAll() throws SQLException {
        return repository.getAll();
    }

    public List<Plataforma> getByGameID(Long gameID) throws SQLException {
        return repository.getByGameID(gameID);
    }

    public Plataforma getAllInDiscount(Long platformID) throws SQLException {
        return repository.getByID(platformID);
    }
}
