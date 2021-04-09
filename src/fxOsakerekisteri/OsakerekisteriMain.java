package fxOsakerekisteri;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import osakerekisteri.Osakerekisteri;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author Jesse Korolainen & Teemu Nieminen
 * @version 3.3.2021
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
	            
	            // Platform.setImplicitExit(false); // tätä ei kai saa laittaa

	           primaryStage.setOnCloseRequest((event) -> {
	                    if ( !osakerekisteriCtrl.voikoSulkea() ) event.consume();
	                });
	            
	            Osakerekisteri osakekanta = new Osakerekisteri();
	            osakekanta.readFromFile("");
	            osakerekisteriCtrl.setOsakerekisteri(osakekanta); 
	            
	            primaryStage.show();
	            
	            Application.Parameters params = getParameters(); 
	            if ( params.getRaw().size() > 0 ) 
	            	osakerekisteriCtrl.readFromFile(params.getRaw().get(0));  
	            else
	                if ( !osakerekisteriCtrl.open() ) Platform.exit();
	            
	        } catch(Exception e) {
	            e.printStackTrace();
	        }
	    }
		/**
	            Application.Parameters params = getParameters(); 
	            if ( !osakerekisteriCtrl.avaa() ) Platform.exit();
	            osakerekisteriCtrl.lueTiedosto(params.getRaw().get(0)); 
	        } catch(Exception e) {
	            e.printStackTrace();
	        }
	    }
	    */

	
	/**
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		launch(args);
	}
}

 