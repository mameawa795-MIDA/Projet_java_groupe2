package com.hotel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/hotel/view/hotel.fxml"));
        Scene scene = new Scene(loader.load(), 1050, 700);

        scene.getStylesheets().add(
                Main.class.getResource("/com/hotel/view/style.css").toExternalForm()
        );

        stage.setTitle("Application JavaFX - Gestion de Réservations d'Hôtel");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
