package osakerekisteri;

/**
 * Poikkeusluokka tietorakenteesta aiheutuville poikkeuksille.
 * @author Jesse Korolainen & Teemu Nieminen
 * @version 1.3.2021
 *
 */
public class StoreException extends Exception {
    private static final long serialVersionUID = 1L;


    /**
     * Poikkeuksen muodostaja jolle tuodaan poikkeuksessa
     * käytettävä viesti
     * @param message Poikkeuksen viesti
     */
    public StoreException(String message) {
        super(message);
    }
}
