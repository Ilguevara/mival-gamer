package mivalgamer.app.config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionDB {
    private static final Logger LOGGER = Logger.getLogger(ConnectionDB.class.getName());
    private static final String DB_URL = "jdbc:mysql://bkgshsddffwp1gdiqqcl-mysql.services.clever-cloud.com/bkgshsddffwp1gdiqqcl";
    private static final String DB_USER = "uzjpyykc41cm3273";
    private static final String DB_PASS = "oq4aVRScMPmmRQS6SciH";


    private ConnectionDB() {}

    public static Connection conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (ClassNotFoundException | SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error de conexión", ex);
            return null;
        }
    }
}