package fxOsakerekisteri;


import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Jesse Korolainen & Teemu Nieminen
 * @version 14.4.2021
 *
 */
public class NameController implements ModalControllerInterface<String> {
    
    @FXML private TextField textAnswer;
    private String answer = null;

    
    @FXML private void handleOK() {
    	answer = textAnswer.getText();
        ModalController.closeStage(textAnswer);
    }

    
    @FXML private void handleCancel() {
        ModalController.closeStage(textAnswer);
    }


    @Override
    public String getResult() {
        return answer;
    }

    
    @Override
    public void setDefault(String oletus) {
    	textAnswer.setText(oletus);
    }

    
    /**
     * Mitä tehdään kun dialogi on näytetty
     */
    @Override
    public void handleShown() {
    	textAnswer.requestFocus();
    }
    
    
    /**
     * Luodaan nimenkysymisdialogi ja palautetaan siihen kirjoitettu nimi tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä nimeä näytetään oletuksena
     * @return null jos painetaan Cancel, muuten kirjoitettu nimi
     */
    public static String askName(Stage modalityStage, String oletus) {
        return ModalController.showModal(
        		NameController.class.getResource("OsakerekisteriNameController.fxml"),
        		// NameController.class.getResource("OsakerekisteriGUIBuy.fxml"), PITÄISIKÖ OLLA TÄMÄ?
                "Osakerekisteri",
                modalityStage, oletus);
    }
}