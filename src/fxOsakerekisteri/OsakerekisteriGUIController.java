package fxOsakerekisteri;

import fi.jyu.mit.fxgui.Dialogs;
import javafx.fxml.FXML;
/**
 * @author Julio Stonkman
 * @version 18.1.2021
 *
 */
public class OsakerekisteriGUIController {
	
    
    @FXML void handleBuyStocks() {
        buy();
    }

    @FXML void handlePrint() {
        print();
    }

    @FXML void handleSave() {
        save();
    }

    @FXML void handleSellStocks() {
        sell();
    }
    
    @FXML void handleAddNewStock() {
        Dialogs.showQuestionDialog("Uusi osake?", "Ostetaanko osake: Gamestop?", "Kyllä", "Eiku");
    }

    
    // ===============================================================
    
    private void print() {
        Dialogs.showMessageDialog("Printataan. Printtaus ei vielä toimi!");
    }
    
    private void buy() {
    	Dialogs.showMessageDialog("Ostit osakkeen! Mutta äläpä hätäile, ei toimi vielä.");
    }
	
    private void save() {
    	Dialogs.showMessageDialog("Tallennetaan! Mutta ei toimi vielä.");
    }
    
    private void sell() {
        Dialogs.showMessageDialog("Myit osakkeen! Ei toimi vielä.");
    }
    
}
