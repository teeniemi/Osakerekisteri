package fxOsakerekisteri;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import osakerekisteri.Osakerekisteri;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author Jesse Korolainen & Teemu Nieminen
 * @version 6.5.2021
 *
 */
public class OsakerekisteriMain extends Application {

		@Override
	    public void start(Stage primaryStage) {
	        try {
	            final FXMLLoader ldr = new FXMLLoader(getClass().getResource("OsakerekisteriGUIView.fxml"));
	            final Pane root = (Pane)ldr.load();
	            final OsakerekisteriGUIController osakerekisteriCtrl = (OsakerekisteriGUIController)ldr.getController();

	            final Scene scene = new Scene(root);
	            scene.getStylesheets().add(getClass().getResource("osakerekisteri.css").toExternalForm());
	            primaryStage.setScene(scene);
	            primaryStage.setTitle("Osakerekisteri");
	           primaryStage.setOnCloseRequest((event) -> {
	                    if ( !osakerekisteriCtrl.voikoSulkea() ) event.consume();
	                });
	            
	            Osakerekisteri osakekanta = new Osakerekisteri();
	            osakerekisteriCtrl.setOsakerekisteri(osakekanta); 
	            
	            primaryStage.show();
	            
	            Application.Parameters params = getParameters(); 
	            if ( params.getRaw().size() > 0 ) 
	            	osakerekisteriCtrl.readFile(params.getRaw().get(0));  
	            else
	                if ( !osakerekisteriCtrl.open() ) Platform.exit();
	            
	        } catch(Exception e) {
	            e.printStackTrace();
	        }
	    }
	
	/**
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		launch(args);
	}
}

 