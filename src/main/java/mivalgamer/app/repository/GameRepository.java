package mivalgamer.app.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import mivalgamer.app.EstadoVideojuego;
import mivalgamer.app.Videojuego;
import mivalgamer.app.config.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GameRepository {

    private final Connection connection;

    public GameRepository(){
        connection = ConnectionDB.conectar();
    }

    public List<Videojuego> getAll() throws SQLException {
        List<Videojuego> videojuegos = new ArrayList<>();
        String sql = "SELECT * FROM videojuego";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                videojuegos.add(fromResultSet(rs));
            }
        }
        return videojuegos;
    }

    public List<Videojuego> getByPlatform(long idPlatform) throws SQLException {
        List<Videojuego> videojuegos = new ArrayList<>();
        String sql = """
            SELECT v.*
            FROM videojuego v
            JOIN videojuego_plataforma vp ON v.id_videojuego = vp.id_videojuego
            WHERE vp.id_plataforma = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, idPlatform);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    videojuegos.add(fromResultSet(rs));
                }
            }
        }
        return videojuegos;
    }

    public List<Videojuego> getAllInDiscount() throws SQLException {
        List<Videojuego> videojuegos = new ArrayList<>();
        String sql = "SELECT v.* FROM videojuego v " +
            "JOIN videojuego_descuento vd ON v.id_videojuego = vd.videojuego_id " +
            "JOIN descuento d ON vd.descuento_id = d.id_descuento " +
            "WHERE d.fecha_inicio <= CURRENT_DATE AND d.fecha_expiracion >= CURRENT_DATE";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                videojuegos.add(fromResultSet(rs));
            }
        }
        return videojuegos;
    }

    private List<Videojuego> getByUser(String idUser) throws SQLException {
        List<Videojuego> videoGames = new ArrayList<>();
        String sql = """
            SELECT v.*, b.fecha_compra, b.key_activacion FROM biblioteca b
            JOIN videojuego v ON b.id_videojuego = v.id_videojuego
            WHERE b.id_usuario = ?
        """;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                videoGames.add(fromResultSet(rs));
            }
        }
        return videoGames;
    }

    public Videojuego getByID(long id) throws SQLException {
        String sql = "SELECT * FROM videojuego WHERE id_videojuego = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return fromResultSet(rs);
                }
            }
        }
        throw new SQLException("Videojuego no encontrado");
    }


    public Videojuego fromResultSet(ResultSet rs) throws SQLException {
         return new Videojuego(
            rs.getLong("id_videojuego"),
            rs.getString("titulo"),
            rs.getString("estudio"),
            rs.getLong("id_genero"),
            rs.getString("descripcion"),
            rs.getDouble("precio"),
            rs.getDouble("precio_original"),
            rs.getBoolean("descuento_aplicado"),
            EstadoVideojuego.fromString(rs.getString("estado")),
            getListFromJsonArray(rs.getString("icono")),
            getListFromJsonArray(rs.getString("portada")),
            getListFromJsonArray(rs.getString("contenido_visual")),
            rs.getInt("stock")
        );
    }

    private List<String> getListFromJsonArray(String jsonArray) throws SQLException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> array = new ArrayList<>();
        try {
            if (jsonArray != null && !jsonArray.isEmpty()) {
                array = objectMapper.readValue(jsonArray, new TypeReference<>() {});
            }
        } catch (JsonProcessingException e) {
            throw new SQLException("Error parsing JSON field 'contenido_visual'", e);
        }
        return array;
    }
}
