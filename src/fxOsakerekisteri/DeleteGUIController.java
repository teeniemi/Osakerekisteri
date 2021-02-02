package fxOsakerekisteri;

import java.awt.event.ActionEvent;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;

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
        Dialogs.showMessageDialog("Poistit osakkeen, mutta eipä toimikaan vielä HÄHÄHÄ");

		
	}
	
	
	private void nope() {
        Dialogs.showMessageDialog("Etkö halunnukaan poistaa? Ei toimikaan.");

		
	}
}
