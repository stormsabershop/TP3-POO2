package tp3.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


// IMPORTANT ne rien chagner dans cette classe.

public class MagasinApplication extends Application {

    private MagasinController magasinController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("magasin.fxml"));
        Parent root = loader.load();
        magasinController = loader.getController();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

//        FXMLLoader loader2 = new FXMLLoader(this.getClass().getResource("sections.fxml"));
//        Parent rootSection = loader2.load();
//        SectionController sectionController = loader2.getController();
//        magasinController.setSectionController(sectionController);
//        Stage secondaryStage = new Stage();
//        secondaryStage.setScene(new Scene(rootSection));
//        secondaryStage.initOwner(primaryStage);
//        secondaryStage.show();


    }

    @Override
    public void stop() {
        System.out.println("stop");
        magasinController.stop();
    }

    public static void main(String[] args) {
        Application.launch();
    }
}
