package JavaFxMain;


import view.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class JavaFXAppMain extends Application {

	@Override
	public void start(Stage loginStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/Login.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		loginStage.setScene(scene);
			
		loginStage.setTitle("ÓÃ»§µÇÂ¼");
		LoginController mainViewController = loader.getController();
		mainViewController.setDialogStage(loginStage);
		
		loginStage.show();	
	}

	public static void main(String[] args) {
		launch(args);
	}

}
