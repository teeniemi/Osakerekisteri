package fxOsakerekisteri;

import fi.jyu.mit.fxgui.Dialogs;
import javafx.fxml.FXML;
/**
 * @author Julio Stonkman
 * @version 18.1.2021
 *
 */
public class OsakerekisteriGUIController {
	
    @FXML
    void handleUusiOsake() {
    	Dialogs.showQuestionDialog("Uusi osake?", "Ostetaanko osake: Gamestop?", "Kyllä", "Eiku");

    }
	
    @FXML
    void handleTallenna() {
    	tallenna();

    }
	
    @FXML void handleOstaOsake() {
    	osta();
    }
    
    // ===============================================================
    
    
    private void osta() {
    	Dialogs.showMessageDialog("Ostit osakkeen! Mutta äläpä hättäile, ei toimi vielä.");
    }
	
    private void tallenna() {
    	Dialogs.showMessageDialog("Tallennetaan! Mutta ei toimi vielä.");
    }
    
    
}
