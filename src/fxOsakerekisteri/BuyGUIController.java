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
public class BuyGUIController implements ModalControllerInterface<String>{

    @FXML void handleBuyStocks(ActionEvent event) {
        buyStocks();
    }

    @FXML void handleCancel(ActionEvent event) {
        cancel();
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

    private void buyStocks() {
        Dialogs.showMessageDialog("Et voi vielä ostaa osakkeita!");
    }
    
    private void cancel() {
        Dialogs.showMessageDialog("Tästä voit peruuttaa ostamisen, mutta ei toimi vielä!");
    }
}
