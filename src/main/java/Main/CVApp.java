package Main;


import Personal.PersonalController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CVApp extends Application{

	private MainController mainController;


	@Override
	public void start(Stage primaryStage) throws Exception {
		mainController = new MainController();
		
		Scene scene = new Scene(mainController.getView(), 700, 500);
		
		primaryStage.setTitle("MiCV");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
		
	}

}
