package fxOsakerekisteri;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * @author teemu
 * @version 2.2.2021
 *
 */
public class DeleteGUIController implements ModalControllerInterface<String> {

	
	@FXML
    void handleNope() {
		nope();
    }

    @FXML
    void handleYes() {
    	yes();
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
	public void setDefault(String arg0) {
		// TODO Auto-generated method stub
		
	}

	
	private void yes(){
        Dialogs.showMessageDialog("Poistit osakkeen, mutta eipä toimikaan vielä!");

		
	}
	
	
	private void nope() {
        Dialogs.showMessageDialog("Etk� halunnukaan poistaa? Ei toimikaan.");

		
	}
}
