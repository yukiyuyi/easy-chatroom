package view;



import java.io.IOException;
import java.net.Socket;

import domain.ChatRoom;
import domain.RequestTypeEnum;
import domain.Response;
import domain.ResponseCodeEnum;
import domain.RoomRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import util.SocketUtil;

public class CreateRoomController {
	@FXML
	private TextField chatroomId;
	@FXML
	private TextField portField;
	private Stage stage;
	private String username;
	private ChatRoom chatroom;
	private Socket socket;
	private boolean isOkClick = false;
	
	@FXML
	public void initialize() {
//		username=SocketUtil.socketName;
		chatroom = new ChatRoom();
		try {
			socket = new Socket("127.0.0.1", 11111);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setStage(Stage stage) {
		this.stage=stage;
	}
	@FXML
	public void handleOkButton(ActionEvent event) throws ClassNotFoundException, IOException {
		chatroom.setChatroomId(chatroomId.getText());
		chatroom.setPort(Integer.parseInt(portField.getText()));
		chatroom.setUsername(username);
		RoomRequest request = new RoomRequest(chatroom,RequestTypeEnum.CREATEROOM);
		Response response = SocketUtil.sendRequest(socket, request);
		if(response.getCode()==ResponseCodeEnum.SUCCESS) {
			isOkClick = true;
			stage.close();
		} else {
			isOkClick = false;
			new Alert(AlertType.ERROR,response.getMessage()).showAndWait();
		}
	}
	
	@FXML
	public void handleCancelButton(ActionEvent event) {
		stage.close();
	}
	public ChatRoom getResult() {
		return chatroom;
	}

	public boolean isOkClicked() {
		return isOkClick;
	}

}
