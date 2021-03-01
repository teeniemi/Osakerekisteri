package fxOsakerekisteri;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


/**
 * @author Jesse Korolainen & Teemu Nieminen
 * @version 18.1.2021
 *
 */
public class OsakerekisteriMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("OsakerekisteriGUIStart.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("osakerekisteri.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	i
	/**
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
 