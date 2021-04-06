package osakerekisteri;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Random;

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
    
    private int     transactionId;
    private String  date;
    private String  type;
    private double  stockPrice;
    private int  amount;
    private double  expenses;
    private double  totalPrice;
    private int stockId;
    
    
    private static int nextId = 1;
    
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
     * Alustetaan harrastus.  Toistaiseksi ei tarvitse tehdä mitään
     */
    public Transaktio() {
        // Vielä ei tarvita mitään
    }


    /**
     * Alustetaan tietyn jäsenen harrastus.  
     * @param jasenNro jäsenen viitenumero 
     */
    public Transaktio(int stockId) {
        this.stockId = stockId;
    }


    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot Transaktiolle.
     * Kaikki arvot arvotaan, jotta kahdella transaktiolla ei olisi
     * samoja tietoja.
     * @param nro viite osakkeeseen, jonka transaktiosta on kyse
     */
    public void testi(int nro) {
    	
        stockId = nro;
        stockPrice = Math.random()*100;
        amount = (int)(Math.random()*100);
        expenses = Math.random()*100;
        totalPrice = Math.random()*100;
    }

    
    /**
     * Tulostetaan transaktion tiedot
     * @param os tietovirta mihin tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    /**
     * Antaa transaktion seuraavan rekisterinumeron.
     * @return transaktion uusi tunnus_nro
     * @example
     * <pre name="test">
     *   Transaktio pitsi1 = new Transaktio();
     *   pitsi1.getTransactionId() === 0;
     *   pitsi1.register();
     *   Transaktio pitsi2 = new Transaktio();
     *   pitsi2.register();
     *   int n1 = pitsi1.getTransactionId();
     *   int n2 = pitsi2.getTransactionId();
     *   n1 === n2-1;
     * </pre>
     */
    
    public int register() {
        transactionId = nextId;
        nextId++;
        return transactionId;
    }
    
    /**
     * Palautetaan transaktion oma id
     * @return transaktion id
     */
    public int getTransactionId() {
        return transactionId;
    }


    /**
     * Palautetaan mille osakkeelle transaktio kuuluu
     * @return jäsenen id
     */
    public int getStockId() {
        return stockId;
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