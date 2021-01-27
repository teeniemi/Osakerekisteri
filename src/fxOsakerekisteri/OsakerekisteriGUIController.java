package fxOsakerekisteri;

import fi.jyu.mit.fxgui.Dialogs;
import javafx.fxml.FXML;
/**
 * @author Julio Stonkman
 * @version 18.1.2021
 *
 */
public class OsakerekisteriGUIController {

    @FXML void handleAbout() {
        Dialogs.showMessageDialog("Tietoja sovelluksesta. Tähän varmaan voi suoraan kirjoittaa mitä mieleen tulee. On kyllä pirun hyvän softan Stonkman koodannut.");
    }
    
    @FXML void handleBuyStocks() {
        buy();
    }
    
    @FXML void handleDelete() {
        delete();
    }
    
    @FXML void handleEdit() {
        edit();
    }
    
    @FXML void handleExport() {
        export();
    }

    
    @FXML void handleFile() {
        file();
    }
    
    @FXML void handleHelp() {
        help();
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
    
    private void sell() {
        Dialogs.showMessageDialog("Myit osakkeen! Ei toimi vielä.");
    }
    
}
