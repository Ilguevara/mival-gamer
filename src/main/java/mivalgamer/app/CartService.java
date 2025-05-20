package mivalgamer.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mivalgamer.app.Controllers.PlataformasController;

public class CartService {
    private static CartService instance;
    private ObservableList<PlataformasController.GameItem> cartItems = FXCollections.observableArrayList();
    private double total = 0.0;

    private CartService() {}

    public static CartService getInstance() {
        if (instance == null) {
            instance = new CartService();
        }
        return instance;
    }

    public ObservableList<PlataformasController.GameItem> getCartItems() {
        return cartItems;
    }

    public void addItem(PlataformasController.GameItem item) {
        cartItems.add(item);
        updateTotal();
    }

    public void removeItem(PlataformasController.GameItem item) {
        cartItems.remove(item);
        updateTotal();
    }

    public double getTotal() {
        return total;
    }

    private void updateTotal() {
        total = cartItems.stream()
                .mapToDouble(item -> item.getPrice())
                .sum();
    }
}