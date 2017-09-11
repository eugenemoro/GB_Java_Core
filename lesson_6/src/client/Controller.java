package client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
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

	String msg;
	@FXML
	private TextArea chatTextArea;

	@FXML
	private TextField messageBox;

	static Socket socket;
	static DataInputStream in;
	static DataOutputStream out;

	public void sendBtnAction(ActionEvent actionEvent) throws Exception {
		messageSend();
	}

	public void onEnter(ActionEvent actionEvent) {
		messageSend();
	}

	public void messageSend(){
		String msg = messageBox.getText();
		try {
			out.writeUTF(msg);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		messageBox.setText("");

	}

	public void updateChatTextArea(String msg) {
		chatTextArea.appendText(msg);
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try{
			socket = new Socket("localhost", 8189);
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
		}catch(IOException e){
			e.printStackTrace();
		}

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						String msg = in.readUTF();
						chatTextArea.appendText(msg + "\n");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		thread.setDaemon(true);
		thread.start();
	}

	@FXML
	public void exitApplication() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Platform.exit();
	}
}
