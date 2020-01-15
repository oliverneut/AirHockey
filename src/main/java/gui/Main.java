package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    static String username;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        Parent root = FXMLLoader.load(Thread.currentThread()
                .getContextClassLoader().getResource("main.fxml"));
        Scene scene = new Scene(root, 400, 600);

        primaryStage.setTitle("AIR HOCKEY");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
