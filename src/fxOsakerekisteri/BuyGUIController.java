package fxOsakerekisteri;

import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * @author jesse
 * @version 2.2.2021
 *
 */
public class BuyGUIController implements ModalControllerInterface<String>{

    @FXML void handleBuyStocks(ActionEvent event) {
        //
    }

    @FXML void handleCancel(ActionEvent event) {
        //
    }

    @Override public String getResult() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public void handleShown() {
        // TODO Auto-generated method stub
        
    }

    @Override public void setDefault(String oletus) {
        // TODO Auto-generated method stub
        
    }

}
