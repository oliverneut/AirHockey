package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    public static HTTPController httpController;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        httpController = new gui.HTTPController();

<<<<<<< HEAD
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"));
=======
        Parent root = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("main.fxml"));
>>>>>>> parent of 021c095... Fixed the checkstyle errors in GUI package
        Scene scene = new Scene(root, 400, 600);

        primaryStage.setTitle("FXML Welcome");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
