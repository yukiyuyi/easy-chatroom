package view;

import java.io.IOException;
import java.net.Socket;

import domain.Request;
import domain.RequestTypeEnum;
import domain.Response;
import domain.ResponseCodeEnum;
import domain.User;
import domain.UserRequest;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import util.SocketUtil;

public class RegisterController {
	private Stage dialogStage;
	private boolean okClicked = false;
	private User user;
	
	@FXML
	private TextField unameField;
	@FXML
	private TextField upassField;
	@FXML
	private TextField upassAgainField;
	
	private Socket socket = null;

	@FXML
	public void initialize() {
		user = new User();
	}
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public void setUser(User user) {
		this.user = user;
		
		unameField.setText(user.getUsername());
		upassField.setText(user.getPassword());
		upassAgainField.setText(user.getPassword());
	}
	
	public boolean isOkClicked() {
		return okClicked;
	}
	
	@FXML
	private void handleOk() throws ClassNotFoundException, IOException {
		String uname = unameField.getText();
		if(isInputValid()) {
			try {
				socket = new Socket("127.0.0.1", 11111);
			} catch (IOException e) {
				e.printStackTrace();
			}
			user.setUsername(uname);
			user.setPassword(upassField.getText());
			Request request = new UserRequest(user, RequestTypeEnum.REGISTER);
			Response response = SocketUtil.sendRequest(socket, request);
			if(response.getCode()==ResponseCodeEnum.SUCCESS) {
				okClicked = true;
			} else {
				okClicked = false;
				new Alert(AlertType.ERROR,response.getMessage()).showAndWait();
				return;
			}			
			dialogStage.close();
		}
	}
	
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}
	
	private boolean isInputValid() throws ClassNotFoundException, IOException {
		String errorMessage = "";
		if(unameField.getText()==null||"".equals(unameField.getText())) {
			errorMessage+="账号不能为空\n";
		} else {
			if(upassField.getText()==null||"".equals(upassField.getText())) {
				errorMessage+="密码不能为空\n";
			}
			if(upassAgainField.getText()==null||"".equals(upassAgainField.getText())) {
				errorMessage+="请确认密码\n";
			} else if(!upassAgainField.getText().equals(upassField.getText())) {
				errorMessage+="密码和再次输入的密码不一致\n";
			}
		}
		
		if(errorMessage.length()==0) {
			return true;
		} else {
			new Alert(AlertType.ERROR,errorMessage).showAndWait();
			return false;
		}
	}
}
