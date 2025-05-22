package mivalgamer.app.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class HistorialCompraController {

    @FXML
    private void handleBibliotecaClick(MouseEvent event) {
        try {
            // Cargar la vista de Biblioteca
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Biblioteca.fxml"));
            Parent bibliotecaView = loader.load();

            // Cambiar la vista actual por la vista de Biblioteca
            mivalgamer.app.MivalGamerInterfaz.changeView(bibliotecaView);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la vista de Biblioteca: " + e.getMessage());
        }
    }

    @FXML
    private void handlePlataformaClick(MouseEvent event) {
        try {
            // Cargar la vista de Biblioteca
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Main.fxml"));
            Parent plataformaView = loader.load();

            // Cambiar la vista actual por la vista de plataforma
            mivalgamer.app.MivalGamerInterfaz.changeView(plataformaView);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la vista de plataforma: " + e.getMessage());
        }
    }
    @FXML
    private void handleInicioClick(MouseEvent event) {
        try {
            // Cargar la vista de Biblioteca
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Inicio.fxml"));
            Parent inicioView = loader.load();

            // Cambiar la vista actual por la vista de inicio
            mivalgamer.app.MivalGamerInterfaz.changeView(inicioView);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la vista de inicio: " + e.getMessage());
        }
    }

    @FXML
    private void handleCarritoClick(MouseEvent event) {
        try {
            // Cargar la vista de Carrito
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Carrito.fxml"));
            Parent carritoView = loader.load();

            // Cambiar la vista actual por la vista de Carrito
            mivalgamer.app.MivalGamerInterfaz.changeView(carritoView);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la vista de Carrito: " + e.getMessage());
        }
    }

    @FXML
    private void handleHistorialClick(MouseEvent event) {
        try {
            // Cargar la vista de Carrito
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Carrito.fxml"));
            Parent carritoView = loader.load();

            // Cambiar la vista actual por la vista de Carrito
            mivalgamer.app.MivalGamerInterfaz.changeView(carritoView);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la vista de Carrito: " + e.getMessage());
        }
    }
}
