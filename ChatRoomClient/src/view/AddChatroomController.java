package view;

import domain.ChatRoom;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import wrapper.ChatRoomWrapper;

public class AddChatroomController {
	private ObservableList<ChatRoomWrapper> chatroomObservableList = FXCollections.observableArrayList();
	private Stage stage;
	@FXML
	private TableView<ChatRoomWrapper> chatroomTable;
	@FXML
	private TableColumn<ChatRoomWrapper, String> chatroomIdColumn;
	@FXML
	private TableColumn<ChatRoomWrapper, String> usernameColumn;
	@FXML
	public void initialize() {
		//初始化chatroomObservableList
		
		//初始化TableView
		chatroomIdColumn.setCellValueFactory(cellData->cellData.getValue().chatroomIdProperty());
		usernameColumn.setCellValueFactory(cellData->cellData.getValue().usernameProperty());
		chatroomTable.setItems(chatroomObservableList);
	}
	@FXML
	public void handleAddchatroom() {
		//找到选中的哪一个房间
		
		//加入房间需要更新本地MainViewController改客户所拥有的所有房间，包括ObservableList和List
		
		//加入房间需要更新服务器端的房间列表里的user，然后广播所有聊天室表示房间里新增了用户
		
		//加入房间需要更新服务器端客户列表的rooms
		
	}
	@FXML
	public void handleCancel() {
		stage.close();
	}
	public void setStage(Stage stage) {
		this.stage=stage;
	}
	public boolean isOkClicked() {
		//
		return false;
	}
	public ChatRoom getResult() {
		//
		return null;
	}
}
