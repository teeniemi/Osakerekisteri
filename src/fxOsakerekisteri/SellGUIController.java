package fxOsakerekisteri;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import osakerekisteri.Osakerekisteri;

/**
 * @author jesse korolainen & teemu nieminen
 * @version 4.5.2021
 *
 */
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
			//
		}

		@Override
		public void setDefault(Osakerekisteri osakerekisteri) {
			this.osakerekisteri = osakerekisteri;
		}
}
