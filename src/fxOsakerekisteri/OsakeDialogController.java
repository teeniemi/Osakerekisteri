package fxOsakerekisteri;

import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.ohj2.Mjonot;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import osakerekisteri.Osake;

/**
 * @author jesse korolainen & teemu nieminen
 * @version 21.4.2021
 *
 */
public class OsakeDialogController implements ModalControllerInterface<Osake>, Initializable { 
	
	
@FXML private TextField editName;
@FXML private TextField editAmount;
@FXML private TextField editAveragePrice;
@FXML private TextField editTotalPrice;    
@FXML private Label labelVirhe;

    /**
     * @param url tiedoston osoite
     * @param bundle tiedosto mistä alustetaan
     */
	@Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();  
    }
    
    @FXML private void handleOK() {
        ModalController.closeStage(labelVirhe);
    }

    
    @FXML private void handleCancel() {
    	stockAtPlace = null;
        ModalController.closeStage(labelVirhe);
    }

// ========================================================    
    private Osake stockAtPlace;
    private TextField edits[];
   

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
        edits = new TextField[]{editName, editAmount, editAveragePrice, editTotalPrice};
        int i = 0;
        for (TextField edit : edits) {
            final int k = ++i;
            edit.setOnKeyReleased( e -> kasitteleMuutosOsakkeeseen(k, (TextField)(e.getSource())));
        }
        showStock(edits, stockAtPlace);
    }
    
    
    @Override
    public void setDefault(Osake oletus) {
    	stockAtPlace = oletus;
       // showStock(edits, stockAtPlace);
    }

    
    @Override
    public Osake getResult() {
        return stockAtPlace;
    }
    
    
    /**
     * Mitä tehdään kun dialogi on näytetty
     */
    @Override
    public void handleShown() {
        editName.requestFocus();
    }
    
    
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
     * Käsitellään osakkeeseen tullut muutos
     * @param edit muuttunut kenttä
     */
    private void kasitteleMuutosOsakkeeseen(int k, TextField edit) {
        if (stockAtPlace == null) return;
        String s = edit.getText();
        String virhe = null;
        switch (k) {
           case 1 : virhe = stockAtPlace.setName(s); break;
           case 2 : virhe = stockAtPlace.setAmount(Mjonot.erotaInt(s, -1)); break;
           case 3 : virhe = stockAtPlace.setAveragePrice(Mjonot.erotaDouble(s, -1)); break;
           case 4 : virhe = stockAtPlace.setTotalPrice(Mjonot.erotaDouble(s, -1)); break;
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
     * Näytetään osakkeen tiedot TextField komponentteihin
     * @param edits taulukko jossa tekstikenttiä
     * @param stock näytettävä osake
     */
    public static void showStock(TextField[] edits, Osake stock) {
        if (stock == null) return;
        edits[0].setText(stock.getName());
        edits[1].setText(stock.getAmount());
        edits[2].setText(stock.getAveragePrice());
        edits[3].setText(stock.getTotalPrice());
    }
    
    
    /**
     * Luodaan osakkeen kysymisdialogi ja palautetaan sama tietue muutettuna tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä dataan näytetään oletuksena
     * @return null jos painetaan Cancel, muuten täytetty tietue
     */
    public static Osake askStock(Stage modalityStage, Osake oletus) {
        return ModalController.<Osake, OsakeDialogController>showModal(
                    OsakeDialogController.class.getResource("OsakeDialogView.fxml"),
                    "Osakerekisteri",
                    modalityStage, oletus, null 
                );
    }
}