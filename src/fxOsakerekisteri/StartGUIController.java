package fxOsakerekisteri;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import javafx.fxml.FXML;

/**
 * @author jesse korolainen & teemu nieminen
 * @version 5.2.2021
 * Sovelluksen aloitusikkuna.
 */
public class StartGUIController {
    
    @FXML void handleCreateNew() {
        newPortfolio();
    }

    @FXML void handleGoToPortfolio() {
        ModalController.showModal(OsakerekisteriGUIController.class.getResource("OsakerekisteriGUIView.fxml"), "Portfolio", null, ""); 
    }
    
    private void newPortfolio() {
        Dialogs.showMessageDialog("Vielä ei voi tehdä uutta portfoliota!");
    }
}
