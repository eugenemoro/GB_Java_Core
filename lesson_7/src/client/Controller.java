package client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

	@FXML
	public TextField loginTextBox;
	public PasswordField pwdTextBox;
	public Button loginBtn;

	@FXML
	private TextArea chatTextArea;

	@FXML
	private TextField messageBox;

	String msg;
	private static Socket socket;
	private static DataInputStream in;
	private static DataOutputStream out;
	private boolean isAuthorized;

	public void sendBtnAction(ActionEvent actionEvent) throws Exception {
		messageSend();
	}

	public void onEnter(ActionEvent actionEvent) {
		messageSend();
	}

	public static void send (String s){
		try {
			if (s.equals("/end")) socket.close();
			out.writeUTF(s);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void messageSend(){
		String msg = messageBox.getText();
		send(msg);
		messageBox.setText("");

	}

	public void updateChatTextArea(String msg) {
		chatTextArea.appendText(msg);
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			socket = new Socket("localhost", 8189);
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			//setAuthorized(false);
			Thread t = new Thread(() -> {
				try {
					while (true) {
						String str = in.readUTF();
						if (str.startsWith("/authok")) {
							setAuthorized(true);
							break;
						}
						chatTextArea.appendText(str + "\n");
					}
					while (true) {
						try {
							String str = in.readUTF();
							if (str.equals("/end")) {
								break ;
							}
							chatTextArea.appendText(str + "\n");
						} catch (NullPointerException e) {
							System.out.println("null caught");
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					setAuthorized(false);
				}
			});
			t.setDaemon(true);
			t.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void shutdown() {
		System.out.println("shutdown!");
		send("/end");
		Platform.exit();
	}

	public void setAuthorized(boolean authorized){
		isAuthorized = authorized;
		Scene scene;
		if (!authorized) {
			scene = Main.auth;
		} else {
			scene = Main.chat;
		}
		Platform.runLater(() -> {
			Main.stage.setScene(scene);
			Main.stage.setResizable(false);
			Main.stage.show();
		});
	}

	public void onLoginEnter(ActionEvent actionEvent) {
		send("/auth " + loginTextBox.getText() + " " + pwdTextBox.getText());
		loginTextBox.setText("");
		pwdTextBox.setText("");
	}

	public void onLoginEnterMouse(MouseEvent mouseEvent) {
		onLoginEnter(new ActionEvent());
	}
}
