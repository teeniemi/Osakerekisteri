package fxOsakerekisteri;

import fi.jyu.mit.fxgui.Dialogs;
import javafx.fxml.FXML;
/**
 * @author Julio Stonkman
 * @version 18.1.2021
 *
 */
public class OsakerekisteriGUIController {
	
    @FXML void handleUusiOsake() {
    	Dialogs.showQuestionDialog("Uusi osake?", "Ostetaanko osake: Gamestop?", "Kyll�", "Eiku");
    }
	
    @FXML void handleTallenna() {
    	tallenna();
    }
	
    @FXML void handleOstaOsake() {
    	osta();
    }
    
    // ===============================================================
    
    
    private void osta() {
    	Dialogs.showMessageDialog("Ostit osakkeen! Mutta �l�p� h�tt�ile, ei toimi viel�.");
    }
	
    private void tallenna() {
    	Dialogs.showMessageDialog("Tallennetaan! Mutta ei toimi viel�.");
    }
    
    
}
