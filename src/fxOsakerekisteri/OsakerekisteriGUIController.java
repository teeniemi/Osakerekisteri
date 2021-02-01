package fxOsakerekisteri;

import fi.jyu.mit.fxgui.Dialogs;
import javafx.fxml.FXML;
/**
 * @author Julio Stonkman
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
        buy();
    }
    
    /**
     * Poistaa valitun osakkeen.
     */
    @FXML void handleDelete() {
        delete();
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
        sell();
    }
    
    /**
     * Avaa lisää osakkeita dialogin.
     */
    @FXML void handleAddNewStock() {
        addNew();
    }

    
    // ===============================================================

    private void buy() {
        Dialogs.showMessageDialog("Ostit osakkeen! Mutta äläpä hätäile, ei toimi vielä.");
    }
    
    private void delete() {
        Dialogs.showMessageDialog("Poista. Ei toimi osakkeen poisto vielä!");
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
    
    private void addNew() {
        Dialogs.showMessageDialog("Uusi osake? Ostetaanko osake: GameSTONKS? Kyllä / Eiku");
    }
}
