package osakerekisteri;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * |------------------------------------------------------------------------|
 * | Luokan nimi: Transaktio                            | Avustajat:        |
 * |-------------------------------------------------------------------------
 * | Vastuualueet:                                      |                   |
 * |                                                    |                   |
 * | - ei tiedä osakkeista, eikä                        |                   |
 * |   käyttöliittymästä                                |                   |
 * | - tietää Transaktion kentät (transactionId         |                   |
 * |   date, type, stockPrice, amount, expenses,        |                   |
 * |   totalPrice)                                      |                   |
 * | - osaa tarkistaa tietyn kentän                     |                   |
 * |   oikeellisuuden (syntaksin)                       |                   |
 * | - osaa muuttaa |Nordea Oyj|..| -                   |                   |
 * |   merkkijonon osakkeen tiedoiksi                   |                   |
 * | - osaa antaa merkkijonona i:n kentän               |                   |
 * |   tiedot                                           |                   |
 * | - osaa laittaa merkkijonon i:neksi                 |                   |
 * |   kentäksi                                         |                   |
 * |                                                    |                   |
 * |-------------------------------------------------------------------------
 * @author Jesse Korolainen & Teemu Nieminen
 * @version 1.3.2021
 *
 */
public class Transaktio {
    
    private int     transactionId  = 0;
    private String  date           = "yyyy-MM-dd";
    private String  type           = "";
    private double  stockPrice     = 0;
    private double  amount         = 0;
    private double  expenses       = 0;
    private double  totalPrice     = 0;
    
    /**
     * Tulostetaan transaktion tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(" ID " + String.format("%03d", transactionId));
        out.println(" Date " + date);
        out.println(" Type " + type);
        out.println(" Price " + String.format("%4.2f", stockPrice) + " €");
        out.println(" Amount " + amount + " €");
        out.println(" Expenses " + String.format("%4.2f", expenses) + " €");
        out.println(" Total Price " + String.format("%4.2f", totalPrice) + " €");
        out.println("--------------------------");
    }
    
    /**
     * Tulostetaan transaktion tiedot
     * @param os tietovirta mihin tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Transaktio transaktio = new Transaktio();
        Transaktio transaktio2 = new Transaktio();
        
        // transaktio.rekisteroi();
        // transaktio2.rekisteroi();
        
        transaktio.tulosta(System.out);
        // transaktio.vastaaTransaktio();
        transaktio.tulosta(System.out);
        
        transaktio2.tulosta(System.out);
        // transaktio2.vastaaTransaktio();
        transaktio2.tulosta(System.out);
    }
}
