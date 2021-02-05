package fxOsakerekisteri;

import java.awt.event.ActionEvent;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;

public class SellGUIController implements ModalControllerInterface<String>{

	 	@FXML void handleNope() {
		 	nope();
	    }

	    @FXML void handleSave() {
	    	save();
	    }

	    
	    private void nope() {
	        Dialogs.showMessageDialog("Nössö.");
	    }
	    
	    private void save() {
	        Dialogs.showMessageDialog("Et sää mitään myymässä oo kuitenkaan!");
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
}
