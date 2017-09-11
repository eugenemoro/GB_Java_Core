package client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.spec.PKCS8EncodedKeySpec;

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
