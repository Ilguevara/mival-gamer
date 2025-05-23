package mivalgamer.app.Controllers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import mivalgamer.app.CartService;
import mivalgamer.app.MivalGamerInterfaz;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mivalgamer.app.Videojuego;
import mivalgamer.app.service.GameService;

public class PlataformasController {

    @FXML
    private GridPane gamesGrid = new GridPane();

    @FXML
    private VBox cartItems;
    @FXML
    private Label totalAmount;

    private CartService cartService = CartService.getInstance();

    private GameService gameService;

    private int platformID = 0;

    public static class GameItem {
        private final String title;
        private final double price;
        private HBox cartItemView;

        public GameItem(String title, double price) {
            this.title = title;
            this.price = price;
        }

        public String getTitle() {
            return title;
        }

        public double getPrice() {
            return price;
        }

        public HBox getCartItemView() {
            return cartItemView;
        }

        public void setCartItemView(HBox view) {
            this.cartItemView = view;
        }
    }

    @FXML
    public void initialize() {

        if (platformID == 0) {
            gameService = new GameService();
            try {
                List<Videojuego> videojuegos = gameService.getAllVideoGames();
                int column = 0;
                int row = 0;

                for (Videojuego juego : videojuegos) {
                    VBox gameCard = createGameCard(juego);
                    gamesGrid.add(gameCard, column, row);

                    column++;
                    if (column == 6) {
                        column = 0;
                        row++;
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        updateCartView();
    }

    private VBox createGameCard(Videojuego juego) {
        VBox gameCard = new VBox();
        gameCard.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10; -fx-alignment: center;");

        String imageUrl = juego.getIcono().get(0);
        Image gameImage = new Image(imageUrl, true);


        ImageView gameImageView = new ImageView(gameImage);
        gameImageView.setFitWidth(180);
        gameImageView.setFitHeight(180);
        gameImageView.setPreserveRatio(true);

        gameImage.progressProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() == 1.0) {
                gameImageView.setImage(gameImage);
            }
        });


        Label gameTitle = new Label(juego.getTitulo());
        gameTitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #000;");
        gameTitle.getStyleClass().add("game-title");

        Label gamePrice = new Label(juego.getPrecio() + " COP");
        gamePrice.setStyle("-fx-font-size: 12px; -fx-text-fill: #636363;");
        gamePrice.getStyleClass().add("game-price");


        Button addButton = new Button("+");
        addButton.setOnAction(this::handleAddToCartButton);


        HBox priceAndButton = new HBox(5, addButton, gamePrice);
        priceAndButton.setStyle("-fx-alignment: center-left;");
        priceAndButton.setAlignment(Pos.CENTER_LEFT);


        gameCard.getChildren().addAll(gameImageView, gameTitle, priceAndButton);

        return gameCard;
    }


    @FXML
    public void handleAddToCartButton(ActionEvent event) {
        Button button = (Button) event.getSource();
        VBox gameCard = (VBox) button.getParent().getParent();
        Label titleLabel = findLabelInHierarchy(gameCard, "game-title");
        Label priceLabel = findLabelInHierarchy(gameCard, "game-price");

        if (titleLabel != null && priceLabel != null) {
            String title = titleLabel.getText();
            try {
                double price = Double.parseDouble(priceLabel.getText().replace(" COP", ""));
                System.out.println("Agregando al carrito: " + title + " - " + price);
                addToCart(title, price);
            } catch (NumberFormatException e) {
                System.err.println("Error al parsear el precio: " + priceLabel.getText());
            }
        } else {
            System.err.println("No se encontró título o precio en el gameCard");
        }
    }

    private Button findAddButton(Node node) {
        if (node instanceof Button) {
            Button button = (Button) node;
            if (button.getText().equals("+")) {
                return button;
            }
        }

        if (node instanceof Parent) {
            for (Node child : ((Parent) node).getChildrenUnmodifiable()) {
                Button found = findAddButton(child);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    private Label findLabelInHierarchy(Node node, String styleClass) {
        if (node instanceof Label) {
            Label label = (Label) node;
            if (label.getStyleClass().contains(styleClass)) {
                return label;
            }
        }

        if (node instanceof Parent) {
            for (Node child : ((Parent) node).getChildrenUnmodifiable()) {
                Label found = findLabelInHierarchy(child, styleClass);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    private void updateCartView() {
        cartItems.getChildren().clear();
        for (GameItem item : cartService.getCartItems()) {
            HBox cartItemView = createCartItemView(item);
            item.setCartItemView(cartItemView);
            cartItems.getChildren().add(cartItemView);
        }
        updateTotal();
    }

    private void addToCart(String title, double price) {
        GameItem gameItem = new GameItem(title, price);
        HBox cartItemView = createCartItemView(gameItem);
        gameItem.setCartItemView(cartItemView);

        cartService.addItem(gameItem);

        if (cartItems.getChildren().size() == 1 &&
                cartItems.getChildren().get(0) instanceof Label &&
                ((Label)cartItems.getChildren().get(0)).getText().contains("no has agregado")) {
            cartItems.getChildren().clear();
        }

        cartItems.getChildren().add(cartItemView);
        updateTotal();
    }


    private HBox createCartItemView(GameItem item) {
        HBox cartItemView = new HBox();
        cartItemView.getStyleClass().add("cart-item");
        cartItemView.setSpacing(10);
        cartItemView.setAlignment(Pos.CENTER_LEFT);

        Label titleLabel = new Label(item.title);
        titleLabel.getStyleClass().add("cart-item-title");

        Label priceLabel = new Label(String.format("%.2f COP", item.price));
        priceLabel.getStyleClass().add("cart-item-price");

        Button removeButton = new Button("X");
        removeButton.getStyleClass().add("remove-button");
        removeButton.setOnAction(e -> removeFromCart(item));

        cartItemView.getChildren().addAll(titleLabel, priceLabel, removeButton);
        return cartItemView;
    }

    private void removeFromCart(GameItem item) {
        cartService.removeItem(item);
        cartItems.getChildren().remove(item.getCartItemView());

        if (cartService.getCartItems().isEmpty()) {
            Label emptyLabel = new Label("Aun no has agregado juegos a tu carrito");
            emptyLabel.getStyleClass().add("cart-empty");
            cartItems.getChildren().add(emptyLabel);
        }

        updateTotal();
    }


    private void updateTotal() {
        totalAmount.setText(String.format("%.2f COP", cartService.getTotal()));
    }

    public void handleGameCardClick(MouseEvent mouseEvent) {

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

    @FXML
    private void handleUsuarioClick(MouseEvent event) {
        try {
            // Cargar la vista de Carrito
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Usuario.fxml"));
            Parent UsuarioView = loader.load();

            // Cambiar la vista actual por la vista de Carrito
            mivalgamer.app.MivalGamerInterfaz.changeView(UsuarioView);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la vista de Carrito: " + e.getMessage());
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

    public void setPlatformID(int plataformaId) {
        this.platformID = plataformaId;
    }
    @FXML
    private void handleHistorialComprasClick(MouseEvent event) {
        try {
            // Cargar la vista de Biblioteca
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/HistorialCompra.fxml"));
            Parent plataformaView = loader.load();

            // Cambiar la vista actual por la vista de plataforma
            mivalgamer.app.MivalGamerInterfaz.changeView(plataformaView);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la vista de plataforma: " + e.getMessage());
        }
    }
}