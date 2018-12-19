package view;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import JavaFxMain.Client;
import domain.ChatMessage;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import util.TimeUtil;
import wrapper.ChatRoomWrapper;
import wrapper.OnlineUserWrapper;

public class MainViewController {
	private ObservableList<OnlineUserWrapper> onlineObservabliList = FXCollections.observableArrayList();
	@FXML
	private TableColumn<ChatRoomWrapper, String> usernameColumn;
	@FXML
	private TableView<OnlineUserWrapper> onlineUserTable; //�����ʾ������
	@FXML
	private TableColumn<OnlineUserWrapper, String> onlineUnameColumn;
	
	@FXML
	private TextArea inputTextArea;//������������
	@FXML
	private TextArea chatMessageText;
	private Stage mainStage;
	private Client client;
	static public Queue<SimpleStringProperty> messageQueue = new LinkedList<SimpleStringProperty>();
	@FXML
	private void initialize() {
		//��ʼ��TextArea
		chatMessageText.setEditable(false);//��Ϣ�б���ֹ�༭
		onlineUnameColumn.setCellValueFactory(cellData->cellData.getValue().usernameProperty());
		onlineUserTable.setItems(onlineObservabliList);
	}
	@FXML
	public void kickoutClient() {
		OnlineUserWrapper onlineCleint = onlineUserTable.getSelectionModel().getSelectedItem();
		if(onlineCleint==null) {
			new Alert(AlertType.ERROR,"��ѡ��һ����Ա").showAndWait();
			return;
		} else if((onlineCleint.getUsername()).equals(client.getClientName())) {
			new Alert(AlertType.ERROR,"�Բ����������߳��Լ���").showAndWait();
			return;
		}
		client.kickout(onlineCleint.getUsername());
	}

	@FXML
	public void sendMEssageButton() {
		client.sendToServer("", inputTextArea.getText(),TimeUtil.getTime());
		inputTextArea.setText("");
	}
	@FXML
	private void privateChat() {		//˽��
		OnlineUserWrapper onlineCleint = onlineUserTable.getSelectionModel().getSelectedItem();
		if(onlineCleint==null) {
			new Alert(AlertType.ERROR,"��ѡ��һ����Ա").showAndWait();
			return;
		} else if((onlineCleint.getUsername()).equals(client.getClientName())) {
			new Alert(AlertType.ERROR,"�Բ���������˽���Լ���").showAndWait();
			return;
		}
    	try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/PrivateChat.fxml"));
			AnchorPane mainViewRoot=(AnchorPane)loader.load();
			Scene scene = new Scene(mainViewRoot);
			Stage mainStage = new Stage();
			mainStage.setTitle("˽��");
			mainStage.setScene(scene);			
			PrivateChatController controller = loader.getController();			
			controller.setStage(mainStage);
			mainStage.close();
			mainStage.showAndWait();
			if(controller.isOkClicked()) {
				String mString = controller.getPriavetMessage();
				String toClient = onlineCleint.getUsername();
				String time = TimeUtil.getTime();
		    	client.sendToServer(toClient, mString,time);
		    	addText(client.getClientName() + "�� " + toClient + " ˽��(  " 
		    				+ time +")\n"+ mString);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void setStage(Stage mainStage) {
		this.mainStage=mainStage;		
	}
	public void setClient(Client client) {		//������Ϣ�ӿ�
		this.client=client;
		mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {  //���������ڹرգ������Ͽ��ͻ����������������
				client.closeClient();
			}
		});
		this.client.update();
	}
	public void updateUserList(String[] users) {	//�û��б����
		onlineObservabliList.clear();
        for (String user : users) {			//ѭ��������������û�
        	onlineObservabliList.add(new OnlineUserWrapper(user,""));
        }
    }
	public void addText(String message) {			//����Ϣ��ӵ���Ϣ����
        chatMessageText.appendText(message + "\n");
        messageQueue.add(new SimpleStringProperty(message));
    }
	public OnlineUserWrapper deleteUser(String clientName) {
		for(OnlineUserWrapper online:onlineObservabliList) {
			if(online.getUsername().equals(clientName)) {
				return online;
			}
		}
		return null;
	}
	//����Ϣ���д���
	public synchronized void dealMessage(ChatMessage chatMessage) {		
		messageQueue.add(new SimpleStringProperty(chatMessage.getMessage()));
        String message;
        String str;
        OnlineUserWrapper onUser;
    	try {
        	switch (chatMessage.getCode()) {
			case PRIVATECHAT:   //˽����Ϣ
				message = chatMessage.getFromUsername() + " �� " + 
						chatMessage.getToUsername() + "˽��( " + chatMessage.getSendTime() + ")\n" 
						+ chatMessage.getMessage();
				addText(message);
				break;
			case BROADCAST:    //�㲥��Ϣ
				message = chatMessage.getFromUsername() + "( " + chatMessage.getSendTime() + ")\n" 
						+ chatMessage.getMessage();
				addText(message);
				break;
			case UPDATEUSERLIST:
				updateUserList(chatMessage.getMessage().split(","));
				break;
			case ADDCLIENT:   //�����û�
				addText(String.format("%s(�� %s ) �Ѽ��뷿�䡣", chatMessage.getMessage(), chatMessage.getSendTime()));
				str = chatMessage.getMessage();
				onUser = new OnlineUserWrapper(str,"");
				onlineObservabliList.add(onUser);
				addText("\n----------------��ǰ��������Ϊ:" + onlineObservabliList.size() + "---------------\n");
				break;
			case DELETECLIENT:  //ɾ���û�
				addText(String.format("%s(�� %s ) ���뿪���䡣\n", 
						chatMessage.getMessage(), chatMessage.getSendTime()));
				str = chatMessage.getMessage();
				if(str.equals(client.getClientName())) {  //����Ǹ��û����߳�����ر�socket��
					client.closeClient();
					System.exit(0);
				}
				onlineObservabliList.remove(deleteUser(str));
				addText("\n----------------��ǰ��������Ϊ:" + onlineObservabliList.size() + "---------------\n");
				break;
			case KICKOUT:
				addText(String.format("%s(�� %s ) ��%s�߳����䡣", 
						chatMessage.getMessage(), chatMessage.getSendTime(),chatMessage.getFromUsername()));
				str = chatMessage.getMessage();
				if(str.equals(client.getClientName())) {  //�û����߳�����ر�socket��
					client.closeClient();
					System.exit(0);
				}
				onUser = new OnlineUserWrapper(str,"");
				onlineObservabliList.remove(deleteUser(str));
				addText("\n----------------��ǰ��������Ϊ:" + onlineObservabliList.size() + "---------------\n");
				break;
			case QUIT:
				addText("ʧȥ����,�����뿪���䡣");
				break;
			case ERROR:
				client.closeClient();
				System.exit(0);
			default:
				break;
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
