package fxOsakerekisteri;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * @author jesse
 * @version 2.2.2021
 *
 */
public class PrintGUIController implements ModalControllerInterface<String> {

    @FXML
    void handleNope() {
        nope();
    }

    @FXML
    void handlePrint() {
        print();
    }

    @Override
    public String getResult() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
    }

    @Override
    public void setDefault(String oletus) {
        // TODO Auto-generated method stub
    }
    
    private void nope(){
        Dialogs.showMessageDialog("Ei pääse vielä palaamaan päänäkymään!");
    }
    
    private void print(){
        Dialogs.showMessageDialog("Printtaus ei vielä toimi!");
    }
    
    

}