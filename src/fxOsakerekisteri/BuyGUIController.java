package fxOsakerekisteri;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.ohj2.Mjonot;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import osakerekisteri.Osakerekisteri;
import osakerekisteri.Transaktio;

/**
 * @author Jesse Korolainen & Teemu Nieminen
 * @version 6.5.2021
 *
 */
public class BuyGUIController implements ModalControllerInterface<Transaktio>, Initializable{

    @FXML private DatePicker editDate;
    @FXML private TextField editAmount;
    @FXML private TextField editPrice;
    @FXML private TextField editExpenses;
    @FXML private Label editTotalCost;
    @FXML private Label labelVirhe;

    /**
     * @param url tiedoston osoite
     * @param bundle tiedosto mistä alustetaan
     */
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
    //        
    }
    
	@FXML void handleBuyStocks() {
        ModalController.closeStage(labelVirhe);
    }

    @FXML void handleCancel() {
        transactionAtPlace = null;
        ModalController.closeStage(labelVirhe);
    }

    @Override public void handleShown() {
        alusta();
        
    }

    // =========================================================
    private Transaktio transactionAtPlace;
    private TextField edits[];
   // private Osakerekisteri osakerekisteri; // homma ei toimi jos poistaa
    
    /**
     * Tyhjentään tekstikentät 
     * @param edits taulukko jossa tyhjennettäviä tektsikenttiä
     */
    public static void tyhjenna(TextField[] edits) {
        for (TextField edit : edits)
            edit.setText("");
    }

    /**
     * Tekee tarvittavat muut alustukset. Mm laittaa edit-kentistä tulevan
     * tapahtuman menemään kasitteleMUutosOsakeeseen-metodiin ja vie sille
     * kentännumeron parametrina.
     */
    protected void alusta() {
        edits = new TextField[]{editAmount, editPrice, editExpenses};
        int i = 0;
        for (TextField edit : edits) {
            final int k = ++i;
            edit.setOnKeyReleased( e -> kasitteleMuutosTransaktioon(k, (TextField)(e.getSource())));
        }
        showTransaction(edits, transactionAtPlace);
        showDate(editDate);
    }
    
    /**
     * Näytetään virheilmoitus jos syötetään vääränlaista tietoa.
     * @param virhe virhe
     */
    private void naytaVirhe(String virhe) {
        if ( virhe == null || virhe.isEmpty() ) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }
    
    /**
     * Käsitellään osakkeeseen tullut muutos.
     * @param edit muuttunut kenttä
     */
    private void kasitteleMuutosTransaktioon(int k, TextField edit) {
        if (transactionAtPlace == null) return;
        String s = edit.getText();
        String virhe = null;
        switch (k) {
           case 1 : virhe = transactionAtPlace.setAmount(Mjonot.erotaInt(s, -1)); break;
           case 2 : virhe = transactionAtPlace.setPrice(Mjonot.erotaDouble(s, -1)); break;
           case 3 : virhe = transactionAtPlace.setExpenses(Mjonot.erotaDouble(s, -1)); break;
           default:
        }
        if (virhe == null) {
            Dialogs.setToolTipText(edit,"");
            edit.getStyleClass().removeAll("virhe");
            naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(edit,virhe);
            edit.getStyleClass().add("virhe");
            naytaVirhe(virhe);
        }
    }
    
    /**
     * Näytetään transaktion tiedot StringGrid komponentissa
     * @param edits taulukko jossa tekstikenttiä
     * @param transaction näytettävä transaktio
     */
    public static void showTransaction(TextField[] edits, Transaktio transaction) {
        if (transaction == null) return;
        edits[0].setText(String.valueOf(transaction.getAmount()));
        edits[1].setText(String.valueOf(transaction.getTotalPrice()));
        edits[2].setText(String.valueOf(transaction.getExpenses()));
    }
    
    /**
     * Haetaan päivämäärä.
     * @param date päivämäärä joka alustetaan
     */
    public static void showDate(DatePicker date) {
    	LocalDate pvm = LocalDate.of(21, 1, 1);
    	date.setValue(pvm);
    }
    
    /**
     * Parsitaan osake ja transaktio erikseen, jolloin voidaan palauttaa erikseen transaktio. Tämä palautuu, kun ikkuna sulkeutuu.
     * @param modality mitä modaalisuutta käytetään
     * @param oletus transaktio joka luodaan
     * @param osakerekisteri mistä tiedot haetaan
     * @return palauttaa Transaktio-olion, jolla voidaan katsoa, mitä käyttäjä on kirjoittanut
     */
    public static Transaktio askTransaction(Stage modality, Transaktio oletus, Osakerekisteri osakerekisteri) {
    	return ModalController.<Transaktio, BuyGUIController> showModal(BuyGUIController.class.getResource("OsakerekisteriGUIBuy.fxml"), "Add a stock", modality, oletus, null);
    }
    
    //private void setRegister(Osakerekisteri osakerekisteri) {
		//this.osakerekisteri = osakerekisteri;
	

	@Override
	public Transaktio getResult() {
		return this.transactionAtPlace;
	}

	@Override
	public void setDefault(Transaktio oletus) {
		this.transactionAtPlace = oletus; 
	}   
}
