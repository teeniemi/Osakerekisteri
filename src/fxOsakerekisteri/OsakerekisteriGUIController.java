package fxOsakerekisteri;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import javafx.fxml.FXML;
/**
 * @author Jesse Korolainen & Teemu Nieminen
 * @version 18.1.2021
 *
 */
public class OsakerekisteriGUIController {

    /**
     * Näyttää tietoja sovelluksesta.
     */
    @FXML void handleAbout() {
        Dialogs.showMessageDialog("Tietoja sovelluksesta. Tähän varmaan voi suoraan kirjoittaa mitä mieleen tulee. On kyllä pirun hyvän softan Stonkman koodannut. HODL! BUY THE DIP!!!");
    }
    
    /**
     * Avaa osta-osakkeita dialogin.
     */
    @FXML void handleBuyStocks() {
        ModalController.showModal(OsakerekisteriGUIController.class.getResource("OsakerekisteriGUIBuy.fxml"), "Buy STONKS", null, "");
        buy();
    }
    
    /**
     * Poistaa valitun osakkeen.
     */
    @FXML void handleDelete() {
    	ModalController.showModal(OsakerekisteriGUIController.class.getResource("OsakerekisteriGUIDelete.fxml"), "Delete STONKS", null, "");
    	
    }
    
    /**
     * Ylävalikon edit-nappi.
     */
    @FXML void handleEdit() {
        edit();
    }
    
    /**
     * Vie valitun näkymän CSV-muotoon.
     */
    @FXML void handleExport() {
        export();
    }

    /**
     * Ylävalikon File-nappi.
     */
    @FXML void handleFile() {
        file();
    }
    
    /**
     * Ylävalikon Help-nappi.
     */
    @FXML void handleHelp() {
        help();
    }
    
    /**
     * Tulostaa valitun näkymän.
     */
    @FXML void handlePrint() {
      //tuleeko tähän "OsakerekisteriGUIPrint.fxml" vai "OsakerekisteriGUIPView.fxml"
        ModalController.showModal(OsakerekisteriGUIController.class.getResource("OsakerekisteriGUIPrint.fxml"), "Print STONKS", null, ""); 
        print();
    }

    /**
     * Tallentaa tehdyt muokkaukset.
     */
    @FXML void handleSave() {
        save();
    }
    
    /**
     * Hakee tietokannassa olevia osakkeita.
     */
    @FXML void handleSearch() {
        search();
    }

    /**
     * Avaa myy osakkeita dialogin.
     */
    @FXML void handleSellStocks() {
    	ModalController.showModal(OsakerekisteriGUIController.class.getResource("OsakerekisteriGUISell.fxml"), "Sell STONKS", null, ""); 
        sell();
    }
    
    
    // ===============================================================

    private void buy() {
        Dialogs.showMessageDialog("Ostit osakkeen! Mutta äläpä hätäile, ei toimi vielä.");
    }
    
    
    private void edit() {
        Dialogs.showMessageDialog("Edit nappulan takaa aukeava informaatio.");
    }
    
    private void export() {
        Dialogs.showMessageDialog("Ei voi vielä exportata, sori!");
    }
    
    private void file() {
        Dialogs.showMessageDialog("File. Yläpalkin nappula :D.");
    }
    
    private void help() {
        Dialogs.showMessageDialog("Apua ei vielä saatavilla.");
    }
    
    private void print() {
        Dialogs.showMessageDialog("Printataan. Printtaus ei vielä toimi!");
    }

    private void save() {
    	Dialogs.showMessageDialog("Tallennetaan! Mutta ei toimi vielä.");
    }
    
    private void search() {
        Dialogs.showMessageDialog("Hae osakkeita. Ei toimi vielä.");
    }
    
    private void sell() {
        Dialogs.showMessageDialog("Myit osakkeen! Ei toimi vielä.");
    }
    
}
