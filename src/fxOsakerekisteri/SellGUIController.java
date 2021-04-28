package fxOsakerekisteri;

import java.awt.event.ActionEvent;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import osakerekisteri.Osakerekisteri;

public class SellGUIController implements ModalControllerInterface<Osakerekisteri>{
	
		private Osakerekisteri osakerekisteri;

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
		public Osakerekisteri getResult() {
			return osakerekisteri;
		}

		@Override
		public void handleShown() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setDefault(Osakerekisteri osakerekisteri) {
			this.osakerekisteri = osakerekisteri;
		}
}
