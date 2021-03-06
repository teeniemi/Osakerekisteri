package fxOsakerekisteri;

import java.io.PrintStream;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.StringGrid;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
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
 * @version 6.5.2021
 *
 */
public class OsakerekisteriGUIController implements Initializable {

    @FXML private TextField textSearch;
    @FXML private Label labelError;
    @FXML private ScrollPane panelStock;
    @FXML private ListChooser<Osake> chooserStocks;
    @FXML private StringGrid<Transaktio> gridActions;
    @FXML private ComboBoxChooser<String> cbListSearch;
    
    
    @FXML
    private Label labelStock;

    @FXML
    private Label labelOwned;

    @FXML
    private Label labelAmount;

    @FXML
    private Label labelAvgPrice;

    @FXML
    private Label labelExpenses;
    

    @FXML
    private Label labelTotalPrice;

    
    
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
    	delete();
    }
    
    @FXML void handleAddCompany() {
       // ModalController.showModal(OsakerekisteriGUIController.class.getResource("OsakeDialogView.fxml"), "Add Company", null, "");
        addCompany();
    }


    /**
     * Ylävalikon edit-nappi.
     * @throws CloneNotSupportedException 
     */
    @FXML void handleEdit() {
        try {
			edit();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
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
        PrintGUIController printCtrl = PrintGUIController.print(null); 
        printChosen(printCtrl.getTextArea()); 
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
    	ModalController.showModal(OsakerekisteriGUIController.class.getResource("OsakerekisteriGUISell.fxml"), "Sell STONKS", null, osakerekisteri); 
        // sell();
    }
    
    @FXML void handleListSearch() {
        search(0);
    }


    //============================= Java koodia tästä alaspäin ==================================
    
    private String osakerekisterinNimi = "stocks";
    private Osakerekisteri osakerekisteri;
    private Osake stockAtPlace;
    private TextArea areaStock = new TextArea();
    private static Osake apuStock = new Osake();

    /**
     * Tekee tarvittavat muut alustukset, nyt vaihdetaan GridPanen tilalle
     * yksi iso tekstikenttä, johon voidaan tulostaa osakkeiden tiedot.
     * Alustetaan myös osakelistan kuuntelija 
     */
    protected void format() {
        // panelStock.setContent(areaStock);
        areaStock.setFont(new Font("Courier New", 12));
        panelStock.setFitToHeight(true);
        var otsikot = new String[]{"Type", "Date", "Amount", "Stock Price €", "Expenses €", "Total Price €"};
        gridActions.initTable(otsikot);


        chooserStocks.clear();
        cbListSearch.clear(); 
        for (int k = apuStock.firstField(); k < apuStock.getFields(); k++) 
        	cbListSearch.add(apuStock.getQuestion(k), null); 
        cbListSearch.getSelectionModel().select(0); 
        chooserStocks.addSelectionListener(e -> showStock());
    }
    
    /**
     * Näyttää listasta valitun osakkeen tiedot
     */
    protected void showStock() {
        stockAtPlace = chooserStocks.getSelectedObject();
        
        if (stockAtPlace == null) return;
        labelStock.setText(stockAtPlace.giveStock(0));
        double average = osakerekisteri.getAverage(stockAtPlace);
        labelAvgPrice.setText(average+"");
        labelOwned.setText(osakerekisteri.getDate(stockAtPlace));
        labelAmount.setText(osakerekisteri.getStockAmount(stockAtPlace));
        labelExpenses.setText(osakerekisteri.getExpenses(stockAtPlace));
        labelTotalPrice.setText(osakerekisteri.getTotalPrice(stockAtPlace));
        updateTransactions();
    }

    
    /**
     * Alustaa osakerekisterin lukemalla sen valitun nimisestä tiedostosta
     * @param name tiedosto josta kerhon tiedot luetaan
     * @return null jos onnistuu, muuten virhe tekstinä
     */
    protected String readFile(String name) {
    	osakerekisterinNimi = name;
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
        int k  = cbListSearch.getSelectedIndex(); // ei toteutettu koskaan, joten turha
        
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
     * Luo uuden osakkeen jota aletaan editoimaan 
     */
    protected void newStock() {
        Osake newStock = new Osake();
        newStock.register();
        newStock.giveStock(0);
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
            for (Osake stock : chooserStocks.getObjects()) {
                print(os, stock);
                os.println("\n\n");
            }
        }
    }
    
    /**
     *  luodaan uusi osake ja rekisteröidään se 
     */
    private void buy() {
        Transaktio transaction = new Transaktio();
        transaction = BuyGUIController.askTransaction(null, transaction, osakerekisteri);
        if (transaction == null) return;
        transaction.setStockId(stockAtPlace.getId());
		transaction.register();
		osakerekisteri.add(transaction);
		updateTransactions();
    }
    
    
    /**
     * Luodaan uusi Osake, jolle voi myöhemmin lisätä transaktioita.
     */
    private void addCompany() {
        try {
            Osake stock = new Osake();
            stock = OsakeDialogController.askStock(null, stock); // TÄTÄ EI VARMAANKAAN TARVITA KOSKA EI TARVITSE TARKISTAA OSAKKEITA?
            if (stock == null) return;
            stock.register();
            osakerekisteri.add(stock);
            get(stock.getId());             //PITÄÄKÖ OLLA?
        } catch (StoreException e) {
            Dialogs.showMessageDialog("Too many entries");
        }
    }
    
    /**
     * Hakee osakkeiden tiedot listaan
     * @param stockId osakkeen id, joka aktivoidaan haun jälkeen  
     */
    private void search(int stockId) {
            int id = stockId; // id osakkeen numero, joka aktivoidaan haun jälkeen 
            if ( id <= 0 ) { 
                Osake kohdalla = stockAtPlace; 
                if ( kohdalla != null ) id = kohdalla.getId(); 
            }
            
            int k = cbListSearch.getSelectionModel().getSelectedIndex() + apuStock.firstField(); 
            String ehto = textSearch.getText(); 
            if (ehto.indexOf('*') < 0) ehto = "*" + ehto + "*"; 
            
            chooserStocks.clear();

            int index = 0;
            Collection<Osake> stocks;
            try {
                stocks = osakerekisteri.etsi(ehto, k);
                int i = 0;
                for (Osake stock:stocks) {
                    if (stock.getId() == id) index = i;
                    chooserStocks.add(stock.getName(), stock);
                    i++;
                }
            } catch (StoreException ex) {
                Dialogs.showMessageDialog("Osakkeen hakemisessa ongelmia! " + ex.getMessage());
            }
            chooserStocks.setSelectedIndex(index); // tästä tulee muutosviesti joka näyttää jäsenen
        }
    
    /**
     * Muokkaa transaktioita
     * @throws CloneNotSupportedException
     */
    private void edit() throws CloneNotSupportedException {
    	int r = gridActions.getRowNr();
    	Transaktio transaction = gridActions.getObject(r);
    	if (transaction == null) return; // voisi avata dialogi-ikkunan, jossa viesti
    	transaction = transaction.clone();
    	
        transaction = BuyGUIController.askTransaction(null, transaction, osakerekisteri);
        if (transaction == null) return;
		osakerekisteri.replace(transaction);
		updateTransactions();
    }
    
    /**
     * Päivittää osakkeen transaktiolistan, kun sitä klikataan
     */
    private void updateTransactions() {
    	List <Transaktio> transactions = osakerekisteri.giveTransactions(stockAtPlace);
        gridActions.clear();
		for (Transaktio transaction:transactions) {
			gridActions.add(transaction, transaction.getType(), transaction.getDate().toString(), transaction.getAmount()+"", transaction.getStockPrice()+"", transaction.getExpenses()+"", transaction.getTotalPrice()+"");
		}
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
    
    /**
     * Poistaa osakkeen
     */
    private void delete() {
        Osake stock = chooserStocks.getSelectedObject();
        if (stock == null) return;
        if (( !Dialogs.showQuestionDialog("Delete", "Delete stonk? " + stock.getName(), "Yes", "Nope") ))
        return;
        osakerekisteri.deleteStock(stock);
        int index = chooserStocks.getSelectedIndex();
        search(0);
        chooserStocks.setSelectedIndex(index);
    }
    

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        format();
        
    }
}
