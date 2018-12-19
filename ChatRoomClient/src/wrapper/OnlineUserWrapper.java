package wrapper;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OnlineUserWrapper {
	private StringProperty usernameProperty;
	private StringProperty timeProperty;
	public OnlineUserWrapper(String username, String time) {
		super();
		usernameProperty = new SimpleStringProperty(username);
		timeProperty = new SimpleStringProperty(time);
	}
	public OnlineUserWrapper() {
		this("","");
	}
	
	public String getUsername() {
		return usernameProperty.get();
	}
	public StringProperty usernameProperty() {
		return usernameProperty;
	}
	public void setUsername(String username) {
		usernameProperty.set(username);
	}
	
	public String getTime() {
		return timeProperty.get();
	}
	public StringProperty timeProperty() {
		return timeProperty;
	}
	public void setTimeProperty(String time) {
		timeProperty.set(time);
	}

}
