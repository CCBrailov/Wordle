package edu.lawrence.wordle;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PrimaryController implements Initializable {
    
    @FXML private VBox vBox;
    
    private GamePane game;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        game = new GamePane();
        game.setPrefSize(430, 700);
        vBox.getChildren().add(game);
    }
    
    @FXML
    public void newGame() {
        game.restart();
    }
    
    @FXML
    public void exit() {
        ((Stage)game.getScene().getWindow()).close();
    }
}
