package lk.ijse;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.io.IOException;

public class Launcher extends Application {
    public static void main(String[] args) { launch(args);}

    public void start(Stage primaryStage) throws IOException {
        primaryStage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/LoginPageForm.fxml"))));
        primaryStage.show();
    }
}
