package client;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.AccessibleRole;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

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
	public Label authInfoMessage;

	@FXML
	private TextArea chatTextArea;

	@FXML
	private TextField messageBox;

	@FXML
	private ListView<User> userList;

	String msg;
	private static Socket socket;
	private static DataInputStream in;
	private static DataOutputStream out;
	private boolean isAuthorized;
	UserList userListInit = new UserList();
	ObservableList<User> userListForListView;


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

			userListForListView = FXCollections.observableList(userListInit.getList());
			userList.setItems(userListForListView);

			userList.setCellFactory(new Callback<ListView<User>, ListCell<User>>(){
				@Override
				public ListCell<User> call(ListView<User> p) {
					ListCell<User> cell = new ListCell<User>(){
						@Override
						protected void updateItem(User t, boolean bln) {
							super.updateItem(t, bln);
							if (t != null) {
								setText(t.getName());
							} else {
								setText("");   //clear the now empty cell.
							}
						}
					};
					/*cell.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent mouseEvent) {
							messageBox.setText("/w " + userList.getSelectionModel().getSelectedItem().getName() + " ");
						}
					});*/
					return cell;
				}
			});

			Thread t = new Thread(() -> {
				try {
					while (true) {
						String str = in.readUTF();
						if (str.startsWith("/authok")) {
							setAuthorized(true);
							messageBox.requestFocus();
							break;
						} else {
							Platform.runLater(() -> authInfoMessage.setText(str));
						}
					}
					while (true) {
						try {
							String str = in.readUTF();
							if(str.startsWith("/")){
								if(str.startsWith("/clients")){
									str = str.substring(9);
									String[] users = str.split(" ");
									userListInit.renew(users);
									userList.refresh();
								}
								if (str.equals("/end")) {
									break;
								}
							}else{
								chatTextArea.appendText(str + "\n");
							}
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
		if (!loginTextBox.getText().isEmpty() && !pwdTextBox.getText().isEmpty()) {
			send("/auth " + loginTextBox.getText() + " " + pwdTextBox.getText());
			authInfoMessage.setText("");
		} else {
			authInfoMessage.setText("Login/Password cannot be empty");
		}
		loginTextBox.setText("");
		pwdTextBox.setText("");
	}

	public void onLoginEnterMouse(MouseEvent mouseEvent) {
		onLoginEnter(new ActionEvent());
	}
}
