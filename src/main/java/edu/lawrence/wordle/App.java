package edu.lawrence.wordle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("primary.fxml"));
        Scene scene = new Scene(loader.load(), 440, 720);
        stage.setScene(scene);
        scene.getRoot().setStyle("-fx-font-family: 'sans-serif'");
        stage.setTitle("Wordle");
        stage.show();
    }

    public static void main(String[] args) {
        GamePane.Init();
        launch();
    }

}