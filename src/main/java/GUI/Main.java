package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;


public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        URL url = new File("/Users/oliverneut/Desktop/template/src/main/resources/main.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root, 400, 600);

        URL url2 = new File("/Users/oliverneut/Desktop/template/src/main/resources/dark-theme.css").toURI().toURL();
        scene.getStylesheets().add(url2.toExternalForm());

        primaryStage.setTitle("FXML Welcome");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
