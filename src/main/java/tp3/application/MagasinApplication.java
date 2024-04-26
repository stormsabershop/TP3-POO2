package tp3.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


// IMPORTANT ne rien chagner dans cette classe.

public class MagasinApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("magasin.fxml"));
        Parent root = loader.load();
        MagasinController magasinController = loader.getController();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    public static void main(String[] args) {
        Application.launch();
    }
}
