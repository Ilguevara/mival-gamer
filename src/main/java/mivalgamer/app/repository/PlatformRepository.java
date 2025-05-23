package mivalgamer.app.repository;


import mivalgamer.app.Plataforma;
import mivalgamer.app.config.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PlatformRepository {

    private final Connection connection;

    public PlatformRepository(){
        connection = ConnectionDB.conectar();
    }

    public List<Plataforma> getAll() throws SQLException {
        List<Plataforma> plataformas = new ArrayList<>();
        String sql = "SELECT * FROM plataforma";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                plataformas.add(new Plataforma(
                    rs.getLong("id_plataforma"),
                    rs.getString("nombre_comercial"),
                    rs.getString("fabricante")
                ));
            }
        }
        return plataformas;
    }

    public Plataforma getByID(long id) throws SQLException {
        String sql = "SELECT * FROM plataforma WHERE id_plataforma = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Plataforma(
                        rs.getLong("id_plataforma"),
                        rs.getString("nombre_comercial"),
                        rs.getString("fabricante")
                    );
                }
            }
        }
        throw new SQLException("Plataforma no encontrada");
    }

    public List<Plataforma> getByGameID(long gameID) throws SQLException {
        List<Plataforma> plataformas = new ArrayList<>();
        String sql = """
            SELECT p.*
            FROM plataforma p
            JOIN videojuego_plataforma vp ON p.id_plataforma = vp.id_plataforma
            WHERE vp.id_videojuego = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, gameID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    plataformas.add(new Plataforma(
                        rs.getLong("id_plataforma"),
                        rs.getString("nombre_comercial"),
                        rs.getString("fabricante")
                    ));
                }
            }
        }
        return plataformas;
    }
}
