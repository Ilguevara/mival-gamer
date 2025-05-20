package mivalgamer.app.Controllers;

import mivalgamer.app.MivalGamerInterfaz;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BibliotecaController implements Initializable {

    @FXML
    private TextField searchInput;

    @FXML
    private Label totalGamesLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Inicialización del controlador
        System.out.println("Inicializando vista de Biblioteca...");
    }

    @FXML
    private void handleGameCardClick(MouseEvent event) {
        if (event.getSource() instanceof VBox) {
            VBox gameCard = (VBox) event.getSource();

            // Obtener el título del juego
            String gameTitle = "";
            for (javafx.scene.Node node : gameCard.getChildren()) {
                if (node instanceof Label && ((Label) node).getStyleClass().contains("game-title")) {
                    gameTitle = ((Label) node).getText();
                    break;
                }
            }

            System.out.println("Juego seleccionado: " + gameTitle);

            // Aquí puedes implementar la navegación a la vista de detalles del juego
            // o cualquier otra acción que desees realizar al hacer clic en un juego
        }
    }

    @FXML
    private void handleSearchAction() {
        String searchTerm = searchInput.getText();
        System.out.println("Buscando: " + searchTerm);

        // Implementar lógica de búsqueda
    }

    @FXML
    private void handleManagePurchasesClick() {
        System.out.println("Administrar compras");

        // Implementar navegación a la vista de administración de compras
    }

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
}

