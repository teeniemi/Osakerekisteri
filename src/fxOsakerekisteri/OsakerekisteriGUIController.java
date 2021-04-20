package fxOsakerekisteri;

import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.fxgui.StringGrid;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import osakerekisteri.Osake;
import osakerekisteri.Osakerekisteri;
import osakerekisteri.StoreException;
import osakerekisteri.Transaktio;
/**
 * @author Jesse Korolainen & Teemu Nieminen
 * @version 18.1.2021
 *
 */
public class OsakerekisteriGUIController implements Initializable {

    @FXML private TextField textSearch;
    @FXML private Label labelError;
    @FXML private ScrollPane panelStock;
    @FXML private ListChooser<Osake> chooserStocks;
    @FXML private StringGrid<Transaktio> gridActions;
    
    
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
       // ModalController.showModal(OsakerekisteriGUIController.class.getResource("OsakerekisteriGUIBuy.fxml"), "Buy STONKS", null, "");
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
      //tuleeko tähän "OsakerekisteriGUIPrint.fxml" vai "OsakerekisteriGUIView.fxml"
       // ModalController.showModal(OsakerekisteriGUIController.class.getResource("OsakerekisteriGUIPrint.fxml"), "Print STONKS", null, ""); 
       // print();
        addStock();
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
    @FXML void handleSearchCriteria() {
        if ( stockAtPlace != null )
            get(stockAtPlace.getId());
    }

    /**
     * Avaa myy osakkeita dialogin.
     */
    @FXML void handleSellStocks() {
    	ModalController.showModal(OsakerekisteriGUIController.class.getResource("OsakerekisteriGUISell.fxml"), "Sell STONKS", null, ""); 
        sell();
    }
    
    
    //============================= Java koodia tästä alaspäin ==================================
    
    private String osakerekisterinNimi = "STONK MANAGER 9000";
    private Osakerekisteri osakerekisteri;
    private Osake stockAtPlace;
    private TextArea areaStock = new TextArea();
    private TextArea areaTransaction = new TextArea();

    /**
     * Tekee tarvittavat muut alustukset, nyt vaihdetaan GridPanen tilalle
     * yksi iso tekstikenttä, johon voidaan tulostaa osakkeiden tiedot.
     * Alustetaan myös osakelistan kuuntelija 
     */
    protected void format() {
        panelStock.setContent(areaStock);
        areaStock.setFont(new Font("Courier New", 12));
        panelStock.setFitToHeight(true);
        var otsikot = new String[]{"Transaction ID", "Type", "Date", "Amount", "Stock Price €", "Expenses €", "Total Price €"};
        gridActions.initTable(otsikot);


        chooserStocks.clear();
        chooserStocks.addSelectionListener(e -> showStock());
    }
    
    private void showError(String error) {
        if ( error == null || error.isEmpty() ) {
            labelError.setText("");
            labelError.getStyleClass().removeAll("error");
            return;
        }
        labelError.setText(error);
        labelError.getStyleClass().add("error");
    }
    
    
    /**
     * Näyttää listasta valitun osakkeen tiedot, tilapäisesti yhteen isoon edit-kenttään
     */
    protected void showStock() {
        stockAtPlace = chooserStocks.getSelectedObject();
        
        if (stockAtPlace == null) return;

        areaStock.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaStock)) {
            stockAtPlace.print(os);
        }
        List <Transaktio> transactions = osakerekisteri.giveTransactions(stockAtPlace);
        gridActions.clear();
		for (Transaktio transaction:transactions) {
			gridActions.add(transaction, transaction.getTransactionId()+"", transaction.getType(), transaction.getDate(), transaction.getAmount()+"", transaction.getStockPrice()+"", transaction.getExpenses()+"", transaction.getTotalPrice()+"");
		}
			
    
    }

    
    /**
     * Alustaa osakerekisterin lukemalla sen valitun nimisestä tiedostosta
     * @param name tiedosto josta kerhon tiedot luetaan
     * @return null jos onnistuu, muuten virhe tekstinä
     */
    protected String readFile(String name) {
    	osakerekisterinNimi = name;
        setTitle("Osakerekisteri - " + osakerekisterinNimi);
        try {
            osakerekisteri.readFromFile(name); // lukee nimen osakerekisteri.java aliohjelmaa käyttäen
            get(0);
            return null;
        } catch (StoreException e) {
            get(0);
            String error = e.getMessage(); 
            if ( error != null ) Dialogs.showMessageDialog(error);
            return error;
        }
     }

    /**
     * TODO
     * Kysytään tiedoston nimi ja luetaan se
     * @return true jos onnistui, false jos ei
         */
    public boolean open() {
        String newName = NameController.askName(null, osakerekisterinNimi);
        if (newName == null) return false;
        readFile(newName);
        return true;
    }
  
    
    /**
     * Tietojen tallennus
     * @return null jos onnistuu, muuten virhe tekstinä
     */
    private String save() {
        try {
            osakerekisteri.save();
            return null;
        } catch (StoreException ex) {
            Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + ex.getMessage());
            return ex.getMessage();
        }
    }


    /**
     * Tarkistetaan onko tallennus tehty
     * @return true jos saa sulkea sovelluksen, false jos ei
     */
    public boolean voikoSulkea() {
        save();
        return true;
    }
    
    /**
     * Hakee osakkeiden tiedot listaan
     * @param stockId osakkeen id, joka aktivoidaan haun jälkeen
     */
    protected void get(int stockId) {
    	
        String ehto = textSearch.getText(); 
        int k  = 0; // ei toteutettu koskaan, joten turha
        
        chooserStocks.clear();

        int index = 0;
        Collection<Osake> osakkeet;
        try {
            osakkeet = osakerekisteri.etsi(ehto, k);
            int i = 0;
            for (Osake osake:osakkeet) {
                if (osake.getId() == stockId) index = i;
                chooserStocks.add(osake.getName(), osake);
                i++;
            }
        } catch (StoreException ex) {
            Dialogs.showMessageDialog("Osakkeen hakemisessa ongelmia! " + ex.getMessage());
        }
        chooserStocks.setSelectedIndex(index); // tästä tulee muutosviesti joka näyttää jäsenen
    }

    
    
    /**
     * Hakee osakkeen tiedot listaan
     * @param stockId osakkeen Id, joka aktivoidaan haun jälkeen

    protected void get(int stockId) {
        chooserStocks.clear();

        int index = 0;
        for (int i = 0; i < osakerekisteri.getStocks(); i++) {
            Osake stock = osakerekisteri.giveStock(i);
            if (stock.getId() == stockId) index = i;
            chooserStocks.add(stock.getName(), stock);
        }
        chooserStocks.setSelectedIndex(index); // tästä tulee muutosviesti joka näyttää jäsenen
    }
     */
    
    
    /**
     * Luo uuden osakkeen jota aletaan editoimaan 
     */
    protected void newStock() {
        Osake newStock = new Osake();
        newStock.register();
        newStock.giveStock();
        try {
            osakerekisteri.add(newStock);
        } catch (StoreException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
            return;
        }
        get(newStock.getId());
    }

    /**
     * @param osakerekisteri Osakerekisteri jota käytetään tässä käyttöliittymässä
     */
    public void setOsakerekisteri(Osakerekisteri osakerekisteri) {
        this.osakerekisteri = osakerekisteri;
        showStock();
       // ModalController.showModal(StartGUIController.class.getResource("OsakerekisteriGUIStart.fxml"), "Portfolio", null, osakerekisteri); 
    }
    


    /**
     * Tulostaa osakkeen tiedot
     * @param os tietovirta johon tulostetaan
     * @param stock tulostettava osake
     */
    public void print(PrintStream os, final Osake stock) {
        os.println("----------------------------------------------");
        stock.print(os);
        os.println("----------------------------------------------");
        List <Transaktio> transactions = osakerekisteri.giveTransactions(stock);
		for (Transaktio transaction:transactions)
			transaction.tulosta(os);
    }
    
    /**
     * Tulostaa listassa olevat jäsenet tekstialueeseen
     * @param text alue johon tulostetaan
     */
    public void printChosen(TextArea text) {
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(text)) {
            os.println("Tulostetaan kaikki osakkeet");
            for (int i = 0; i < osakerekisteri.getStocks(); i++) {
                Osake stock = osakerekisteri.giveStock(i);
                print(os, stock);
                os.println("\n\n");
            }
        }
    }
    
    private void setTitle(String title) {
        // ModalController.getStage(searchCriteria).setTitle(title);
    }
    
    // luodaan uusi osake ja rekisteröidään se 
    
    private void buy() {
        Transaktio transaction = new Transaktio();
        transaction = BuyGUIController.askTransaction(null, transaction, osakerekisteri);
        transaction.testi(stockAtPlace.getId()); // POISTA TÄMÄ RIVI MYÖHEMMIN, KOSKA TESTI
		transaction.setStockId(stockAtPlace.getId());
		transaction.register();
		osakerekisteri.add(transaction);
		// TÄHÄN handlebuystocks -KUTSU JA PÄIVITÄ STRING GRID TÄHÄN, KOSKA EI OSAA MUUTEN NÄYTTÄÄ get(transaction.getId());
		// kpl-hinta ja nimi, oma lisää osake -dialogi?
    }
    /**
     * TESTIMIELESSÄ TEHTY METODI
     */
    private void addStock() {
        try {
            Osake osake = new Osake();
            osake.register();
            osake.giveStock();
			osakerekisteri.add(osake);
			get(osake.getId());
		} catch (StoreException e) {
			Dialogs.showMessageDialog("Too many entries");
		}
    }
    
    private void edit() {
    	Dialogs.showMessageDialog("Edit-nappulan takaa aukeava informaatio");
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

    
    private void sell() {
        Dialogs.showMessageDialog("Myit osakkeen! Ei toimi vielä.");
    }
    

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        format();
        
    }

	

}
