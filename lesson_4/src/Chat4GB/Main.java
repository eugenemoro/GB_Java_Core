package Chat4GB;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        final Parent panel = FXMLLoader.load(getClass().getResource("Chat4GB.fxml"));

        Scene scene = new Scene(panel, 400, 385);

        TextField messageBox = (TextField) panel.lookup("#messageBox");
        messageBox.requestFocus();

        primaryStage.setTitle("Chat");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
