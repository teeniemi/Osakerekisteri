package fxOsakerekisteri;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;

/**
 * @author Jesse Korolainen & Teemu Nieminen
 * @version 6.5.2021
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
		return null;
	}

	@Override
	public void handleShown() {
	    return;
	}

	@Override
	public void setDefault(String arg0) {
		return;
	}

	
	private void yes(){
        Dialogs.showMessageDialog("Poistit osakkeen, mutta eipä toimikaan vielä!");
	}
	
	
	private void nope() {
        Dialogs.showMessageDialog("Etk� halunnukaan poistaa? Ei toimikaan.");
	}
}
