package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.dataModel.ToDoData;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
        primaryStage.setTitle("ToDoList");
        primaryStage.setScene(new Scene(root, 900, 500));
        primaryStage.show();
    }

    // method runs when application is stopped
    // will use this to save data
    @Override
    public void init() throws Exception {
        ToDoData.getInstance().loadToDoItems();
    }

    @Override
    public void stop() throws Exception {
        ToDoData.getInstance().storeToDoItems();
    }


    public static void main(String[] args) {
        launch(args);
    }
}