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
		//��ʼ��chatroomObservableList
		
		//��ʼ��TableView
		chatroomIdColumn.setCellValueFactory(cellData->cellData.getValue().chatroomIdProperty());
		usernameColumn.setCellValueFactory(cellData->cellData.getValue().usernameProperty());
		chatroomTable.setItems(chatroomObservableList);
	}
	@FXML
	public void handleAddchatroom() {
		//�ҵ�ѡ�е���һ������
		
		//���뷿����Ҫ���±���MainViewController�Ŀͻ���ӵ�е����з��䣬����ObservableList��List
		
		//���뷿����Ҫ���·������˵ķ����б����user��Ȼ��㲥���������ұ�ʾ�������������û�
		
		//���뷿����Ҫ���·������˿ͻ��б��rooms
		
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
