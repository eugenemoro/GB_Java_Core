package client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;

public class Main extends Application {

    public static Scene chat;
    public static Scene auth;
    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Controller controller = new Controller();

        FXMLLoader chatLoader = new FXMLLoader(getClass().getResource("Chat4GB.fxml"));
        chatLoader.setController(controller);
        Parent chatPanel = chatLoader.load();

        FXMLLoader authLoader = new FXMLLoader(getClass().getResource("authChat4GB.fxml"));
        authLoader.setController(controller);
        Parent authPanel = authLoader.load();

        stage = primaryStage;
        /*stage.setOnHidden(e -> {
            controller.shutdown();
        });*/

        chat = new Scene(chatPanel, 400, 385);
        auth = new Scene(authPanel, 400, 385);

        TextField loginTextBox = (TextField) authPanel.lookup("#loginTextBox");
        loginTextBox.requestFocus();

        stage.setScene(auth);

        stage.show();
        stage.setResizable(false);
        stage.setTitle("Chat");

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                super.windowClosing(e);
                Controller.shutdown();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
