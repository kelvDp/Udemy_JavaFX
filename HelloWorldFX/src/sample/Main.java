package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    // this code sets the stage and scenes for the app
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml")); // load resources from fxml
        primaryStage.setTitle("Hello Kelvin");
        primaryStage.setScene(new Scene(root, 300, 275)); // a scene is a window
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
