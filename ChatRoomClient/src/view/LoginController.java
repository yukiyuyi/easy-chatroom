package view;

import java.io.IOException;
import java.net.Socket;
import JavaFxMain.Client;
import domain.Request;
import domain.RequestTypeEnum;
import domain.Response;
import domain.ResponseCodeEnum;
import domain.User;
import domain.UserRequest;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.SocketUtil;
public class LoginController {
	private User user;
	@FXML
	private TextField unameField;
	@FXML
	private TextField upassField;
	Stage currentStage;
	public static boolean isLoginSuccess = false;
	@FXML
	private boolean isLogine = true;
	Socket socket = null;
	private Client client;
	@FXML
	public void initialize() {
		user = new User("", "");
	}
	public void setDialogStage(Stage dialogStage) {
		this.currentStage = dialogStage;
	}
	@FXML
	private void handleOk() {
		if(isInputValid()) {
			try {
				socket = new Socket("127.0.0.1", 11111);
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				this.user.setUsername(unameField.getText());
				this.user.setPassword(upassField.getText());
				Request request = new UserRequest(user, RequestTypeEnum.LOGIN);
				Response response = SocketUtil.sendRequest(socket, request);
				if(response.getCode()==ResponseCodeEnum.SUCCESS) {
					client = new Client(user.getUsername());
					client.connect("127.0.0.1", 10001);
					enterMainView();
				} else {
					new Alert(AlertType.ERROR,response.getMessage()).showAndWait();
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	@FXML
	private void handleCancel() {
		currentStage.close();
	}
	//注册按钮
	@FXML
	private void register() {
		User rUser = new User("", "");
		showRegisterView(rUser);
	}
	public boolean showRegisterView(User user) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/Register.fxml"));
			AnchorPane page = loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Register");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(currentStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			//controller
			RegisterController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setUser(user);
			
	        dialogStage.showAndWait();

	        return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	private boolean isInputValid() {
		String errorMessage = "";
		if(unameField.getText()==null||"".equals(unameField.getText())) {
			errorMessage+="账号不能为空\n";
		}
		if(upassField.getText()==null||"".equals(upassField.getText())) {
			errorMessage+="密码不能为空\n";
		}
		if(errorMessage.length()==0) {
			return true;
		} else {
			new Alert(AlertType.ERROR,errorMessage).showAndWait();
			return false;
		}
	}
	public void enterMainView() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/MainView.fxml"));
		try {
			AnchorPane mainViewRoot=(AnchorPane)loader.load();
			Scene scene = new Scene(mainViewRoot);
			Stage mainStage = new Stage();
			mainStage.setTitle(client.getClientName() + "  的聊天室");
			mainStage.setScene(scene);
			MainViewController controller = loader.getController();
			controller.setStage(mainStage);
			client.setController(controller);
			controller.setClient(client);
			currentStage.close();
			mainStage.showAndWait();
		}catch(IOException e) {
			e.printStackTrace();
		}

	} 
	
}
