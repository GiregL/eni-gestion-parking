package fr.eni.gestionParking.ihm;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {
    public Application() {
        super();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Application.class.getClassLoader().getResource("scene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Application.class.getClassLoader().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("ZDQQZD");
        primaryStage.show();
    }
}
