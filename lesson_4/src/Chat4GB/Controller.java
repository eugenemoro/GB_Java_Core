package Chat4GB;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {

	@FXML
	private TextArea chatTextArea;

	@FXML
	private TextField messageBox;

	public void sendBtnAction(ActionEvent actionEvent) throws Exception {
		messageSend();
	}

	public void onEnter(ActionEvent actionEvent) {
		messageSend();
	}

	public void messageSend() {
		chatTextArea.appendText(messageBox.getText() + "\n");
	}


}
