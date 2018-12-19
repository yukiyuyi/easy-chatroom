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
	private TableView<OnlineUserWrapper> onlineUserTable; //左侧显示聊天室
	@FXML
	private TableColumn<OnlineUserWrapper, String> onlineUnameColumn;
	
	@FXML
	private TextArea inputTextArea;//输入聊天内容
	@FXML
	private TextArea chatMessageText;
	private Stage mainStage;
	private Client client;
	static public Queue<SimpleStringProperty> messageQueue = new LinkedList<SimpleStringProperty>();
	@FXML
	private void initialize() {
		//初始化TextArea
		chatMessageText.setEditable(false);//消息列表框禁止编辑
		onlineUnameColumn.setCellValueFactory(cellData->cellData.getValue().usernameProperty());
		onlineUserTable.setItems(onlineObservabliList);
	}
	@FXML
	public void kickoutClient() {
		OnlineUserWrapper onlineCleint = onlineUserTable.getSelectionModel().getSelectedItem();
		if(onlineCleint==null) {
			new Alert(AlertType.ERROR,"请选中一名成员").showAndWait();
			return;
		} else if((onlineCleint.getUsername()).equals(client.getClientName())) {
			new Alert(AlertType.ERROR,"对不起，您不能踢出自己！").showAndWait();
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
	private void privateChat() {		//私聊
		OnlineUserWrapper onlineCleint = onlineUserTable.getSelectionModel().getSelectedItem();
		if(onlineCleint==null) {
			new Alert(AlertType.ERROR,"请选中一名成员").showAndWait();
			return;
		} else if((onlineCleint.getUsername()).equals(client.getClientName())) {
			new Alert(AlertType.ERROR,"对不起，您不能私聊自己！").showAndWait();
			return;
		}
    	try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/PrivateChat.fxml"));
			AnchorPane mainViewRoot=(AnchorPane)loader.load();
			Scene scene = new Scene(mainViewRoot);
			Stage mainStage = new Stage();
			mainStage.setTitle("私聊");
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
		    	addText(client.getClientName() + "对 " + toClient + " 私聊(  " 
		    				+ time +")\n"+ mString);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void setStage(Stage mainStage) {
		this.mainStage=mainStage;		
	}
	public void setClient(Client client) {		//设置消息接口
		this.client=client;
		mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {  //监听到窗口关闭，主动断开客户端与服务器的连接
				client.closeClient();
			}
		});
		this.client.update();
	}
	public void updateUserList(String[] users) {	//用户列表更新
		onlineObservabliList.clear();
        for (String user : users) {			//循环遍历添加在线用户
        	onlineObservabliList.add(new OnlineUserWrapper(user,""));
        }
    }
	public void addText(String message) {			//将消息添加到消息队列
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
	//对消息进行处理
	public synchronized void dealMessage(ChatMessage chatMessage) {		
		messageQueue.add(new SimpleStringProperty(chatMessage.getMessage()));
        String message;
        String str;
        OnlineUserWrapper onUser;
    	try {
        	switch (chatMessage.getCode()) {
			case PRIVATECHAT:   //私聊消息
				message = chatMessage.getFromUsername() + " 对 " + 
						chatMessage.getToUsername() + "私聊( " + chatMessage.getSendTime() + ")\n" 
						+ chatMessage.getMessage();
				addText(message);
				break;
			case BROADCAST:    //广播消息
				message = chatMessage.getFromUsername() + "( " + chatMessage.getSendTime() + ")\n" 
						+ chatMessage.getMessage();
				addText(message);
				break;
			case UPDATEUSERLIST:
				updateUserList(chatMessage.getMessage().split(","));
				break;
			case ADDCLIENT:   //新增用户
				addText(String.format("%s(于 %s ) 已加入房间。", chatMessage.getMessage(), chatMessage.getSendTime()));
				str = chatMessage.getMessage();
				onUser = new OnlineUserWrapper(str,"");
				onlineObservabliList.add(onUser);
				addText("\n----------------当前在线人数为:" + onlineObservabliList.size() + "---------------\n");
				break;
			case DELETECLIENT:  //删除用户
				addText(String.format("%s(于 %s ) 已离开房间。\n", 
						chatMessage.getMessage(), chatMessage.getSendTime()));
				str = chatMessage.getMessage();
				if(str.equals(client.getClientName())) {  //如果是该用户被踢出，则关闭socket等
					client.closeClient();
					System.exit(0);
				}
				onlineObservabliList.remove(deleteUser(str));
				addText("\n----------------当前在线人数为:" + onlineObservabliList.size() + "---------------\n");
				break;
			case KICKOUT:
				addText(String.format("%s(于 %s ) 被%s踢出房间。", 
						chatMessage.getMessage(), chatMessage.getSendTime(),chatMessage.getFromUsername()));
				str = chatMessage.getMessage();
				if(str.equals(client.getClientName())) {  //用户被踢出，则关闭socket等
					client.closeClient();
					System.exit(0);
				}
				onUser = new OnlineUserWrapper(str,"");
				onlineObservabliList.remove(deleteUser(str));
				addText("\n----------------当前在线人数为:" + onlineObservabliList.size() + "---------------\n");
				break;
			case QUIT:
				addText("失去连接,您已离开房间。");
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
