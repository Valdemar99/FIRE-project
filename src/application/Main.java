package application;
	
import controller.Controller;
import controller.ScenarioController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			// getting loader and a pane for the first scene. 
	        // loader will then give a possibility to get related controller
			FXMLLoader firstLoader = new FXMLLoader(getClass().getResource("/view/view.fxml"));
			Parent firstPane = firstLoader.load();
			Scene firstScene = new Scene(firstPane, 900, 600);

	        // getting loader and a pane for the second scene
			FXMLLoader secondLoader = new FXMLLoader(getClass().getResource("/view/scenarioView.fxml"));
			Parent secondPane = secondLoader.load();
	        Scene secondScene = new Scene(secondPane, 900, 600);

	        // injecting second scene into the controller of the first scene
	        Controller firstPaneController = (Controller) firstLoader.getController();
	        firstPaneController.setScenarioScene(secondScene);

	        // injecting first scene into the controller of the second scene
	        ScenarioController secondPaneController = (ScenarioController) secondLoader.getController();
	        secondPaneController.setMainScene(firstScene);
	        //injecting the second controller into the first one
	        firstPaneController.setScenarioController(secondPaneController);
	        //injecting data access layer into the second scene
	        secondPaneController.setData(firstPaneController.getData());

	        
	        firstScene.getStylesheets().add("/css/application.css");
	        primaryStage.setTitle("The FIRE project");
	        primaryStage.setScene(firstScene);
	        primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
