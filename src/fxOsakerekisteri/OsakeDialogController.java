package fxOsakerekisteri;

import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.ohj2.Mjonot;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import osakerekisteri.Osake;

public class OsakeDialogController implements ModalControllerInterface<Osake> { 
	
	
@FXML private TextField editNimi;
@FXML private TextField editHetu;
@FXML private TextField editKatuosoite;
@FXML private TextField editPostinumero;    
@FXML private Label labelVirhe;

    public void initialize(URL url, ResourceBundle bundle) {
        alusta();  
    }
    
    @FXML private void handleOK() {
        if ( stockAtPlace != null && stockAtPlace.getName().trim().equals("") ) {
            naytaVirhe("Nimi ei saa olla tyhjä");
            return;
        }
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
     * @param edits tauluko jossa tyhjennettäviä tektsikenttiä
     */
    public static void tyhjenna(TextField[] edits) {
        for (TextField edit : edits)
            edit.setText("");
    }


    /**
     * Tekee tarvittavat muut alustukset. Mm laittaa edit-kentistä tulevan
     * tapahtuman menemään kasitteleMuutosJaseneen-metodiin ja vie sille
     * kentännumeron parametrina.
     */
    protected void alusta() {
        edits = new TextField[]{editNimi, editHetu, editKatuosoite, editPostinumero};
        int i = 0;
        for (TextField edit : edits) {
            final int k = ++i;
            edit.setOnKeyReleased( e -> kasitteleMuutosJaseneen(k, (TextField)(e.getSource())));
        }
    }
    
    
    @Override
    public void setDefault(Osake oletus) {
    	stockAtPlace = oletus;
        showStock(edits, stockAtPlace);
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
        editNimi.requestFocus();
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
     * Käsitellään jäseneen tullut muutos
     * @param edit muuttunut kenttä
     */
    private void kasitteleMuutosJaseneen(int k, TextField edit) {
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
     * Näytetään jäsenen tiedot TextField komponentteihin
     * @param edits taulukko jossa tekstikenttiä
     * @param jasen näytettävä jäsen
     */
    public static void showStock(TextField[] edits, Osake osake) {
        if (osake == null) return;
        edits[0].setText(osake.getName());
        edits[1].setText(osake.getAmount());
        edits[2].setText(osake.getAveragePrice());
        edits[3].setText(osake.getTotalPrice());
    }
    
    
    /**
     * Luodaan jäsenen kysymisdialogi ja palautetaan sama tietue muutettuna tai null
     * TODO: korjattava toimimaan
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


