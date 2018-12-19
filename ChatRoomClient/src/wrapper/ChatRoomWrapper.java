package wrapper;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ChatRoomWrapper {
	private StringProperty chatroomIdProperty;
	private StringProperty usernameProperty;
	public ChatRoomWrapper(String chatroomId,String username) {
		chatroomIdProperty = new SimpleStringProperty(chatroomId);
		usernameProperty = new SimpleStringProperty(username);
	}
	public ChatRoomWrapper() {
		this("","");
	}
	public String getChatroomId() {
		return chatroomIdProperty.get();
	}
	public StringProperty chatroomIdProperty() {
		return chatroomIdProperty;
	}
	public void setChatroomId(String chatroomId) {
		chatroomIdProperty.set(chatroomId);
	}

	public String getUsername() {
		return usernameProperty.get();
	}
	public StringProperty usernameProperty() {
		return usernameProperty;
	}
	public void setUsernameProperty(String username) {
		usernameProperty.set(username);;
	}

}
