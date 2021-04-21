package fxOsakerekisteri;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import osakerekisteri.Osakerekisteri;
import osakerekisteri.Transaktio;

/**
 * @author Jesse Korolainen & Teemu Nieminen
 * @version 2.2.2021
 *
 */
public class BuyGUIController implements ModalControllerInterface<Transaktio>{

    private Osakerekisteri osakerekisteri;

	@FXML void handleBuyStocks() {
        buyStocks();
    }

    @FXML void handleCancel() {
        cancel();
    }

    @Override public void handleShown() {
        // TODO Auto-generated method stub
        
    }


    private void buyStocks() {
        Dialogs.showMessageDialog("Et voi vielä ostaa osakkeita!");
    }
    
    private void cancel() {
        Dialogs.showMessageDialog("Tästä voit peruuttaa ostamisen, mutta ei toimi vielä!");
    }
    /**
     * Parsitaan osake ja transaktio erikseen, jolloin voidaan palauttaa erikseen transaktio. Tämä palautuu, kun ikkuna sulkeutuu.
     * @param modality mitä modaalisuutta käytetään
     * @param oletus transaktio joka luodaan
     * @param osakerekisteri mistä tiedot haetaan
     * @return palauttaa Transaktio-olion, jolla voidaan katsoa, mitä käyttäjä on kirjoittanut
     */
    
    public static Transaktio askTransaction(Stage modality, Transaktio oletus, Osakerekisteri osakerekisteri) {
    	return ModalController.<Transaktio, BuyGUIController> showModal(BuyGUIController.class.getResource("OsakerekisteriGUIBuy.fxml"), "Add a stock", modality, oletus, ctrl -> ctrl.setRegister(osakerekisteri));
    }
    
    private void setRegister(Osakerekisteri osakerekisteri) {
		this.osakerekisteri = osakerekisteri;
	}


	@Override
	public Transaktio getResult() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDefault(Transaktio oletus) {
		// TODO Auto-generated method stub
		
	}
}
