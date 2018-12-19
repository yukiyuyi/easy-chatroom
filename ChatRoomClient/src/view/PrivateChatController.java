package view;



import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;


public class PrivateChatController {
	@FXML
	private TextArea privateMessage;
	@FXML
	private TextField portField;
	private Stage stage;
	private boolean isOkClick = false;
	private String message;
	
	@FXML
	public void initialize() {
//		message = "";
	}
	
	public void setStage(Stage stage) {
		this.stage=stage;
	}
	@FXML
	public void handleOkButton(ActionEvent event) throws ClassNotFoundException, IOException {
		message = privateMessage.getText();
		if(!"".equals(message)||!"".equals(portField.getText())) {
			isOkClick = true;
			stage.close();
		}
	}
	
	@FXML
	public void handleCancelButton(ActionEvent event) {
		stage.close();
	}
	
	public boolean isOkClicked() {
		return isOkClick;
	}
	
	public String getPriavetMessage() {
		return message;
	}

}
