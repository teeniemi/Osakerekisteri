package fxOsakerekisteri;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import osakerekisteri.Osake;
import osakerekisteri.Osakerekisteri;

/**
 * @author jesse korolainen & teemu nieminen
 * @version 5.2.2021
 * Sovelluksen aloitusikkuna.
 */
public class StartGUIController  implements ModalControllerInterface<Osakerekisteri> {
	private Osakerekisteri osakekanta = null;
    @FXML private ListChooser<Osake> listOmistaja;
    @FXML void handleCreateNew() {
        newPortfolio();
    }

    @FXML void handleGoToPortfolio() {
    	osakekanta.setOwner(listOmistaja.getSelectedText());
      //  ModalController.showModal(OsakerekisteriGUIController.class.getResource("OsakerekisteriGUIView.fxml"), "Portfolio", null, ""); 
    }
    
    private void newPortfolio() {
        Dialogs.showMessageDialog("Vielä ei voi tehdä uutta portfoliota!");
    }

	@Override
	public Osakerekisteri getResult() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void handleShown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDefault(Osakerekisteri osakerekisteri) {
		osakekanta = osakerekisteri;
		
	}
}
