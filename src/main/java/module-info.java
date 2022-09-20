module edu.lawrence.wordle {
    requires javafx.controls;
    requires javafx.fxml;

    opens edu.lawrence.wordle to javafx.fxml;
    exports edu.lawrence.wordle;
}
