package fxOsakerekisteri;


import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebEngine;
import osakerekisteri.Transaktio;

/**
 * @author jesse korolainen & teemu nieminen
 * @version 28.4.2021
 *
 */
public class PrintGUIController implements ModalControllerInterface<String> {
    
    @FXML TextArea printArea;
    
    @FXML void handleOK() {
        ModalController.closeStage(printArea);
    }

    @FXML private void handlePrint() {
        PrinterJob job = PrinterJob.createPrinterJob();
        if ( job != null && job.showPrintDialog(null) ) {
            WebEngine webEngine = new WebEngine();
            webEngine.loadContent("<pre>" + printArea.getText() + "</pre>");
            webEngine.print(job);
            job.endJob();
        }
    }

    
    @Override
    public String getResult() {
        return null;
    } 

    
    @Override
    public void setDefault(String oletus) {
        printArea.setText(oletus);
    }

    
    /**
     * Mitä tehdään kun dialogi on näytetty
     */ 
    @Override
    public void handleShown() {
        //
    }
    
    /**
     * @return alue johon tulostetaan
     */
    public TextArea getTextArea() {
        return printArea;
    }
    
    
    /**
     * Näyttää tulostusalueessa tekstin
     * @param printing tulostettava teksti
     * @return kontrolleri, jolta voidaan pyytää lisää tietoa
     */
    public static PrintGUIController print(String printing) {
        PrintGUIController printingCtrl = 
          ModalController.showModeless(PrintGUIController.class.getResource("OsakerekisteriGUIPrint.fxml"),
                                       "Tulostus", printing);
        return printingCtrl;
    }
}