package osakerekisteri;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Random;

import fi.jyu.mit.ohj2.Mjonot;

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
        out.println(" Amount " + amount + " kpl");
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
     * Alustetaan tietyn osakkeen transaktio.  
     * @param stockId osakkeen viitenumero
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
        totalPrice = (stockPrice * amount) + expenses;
    }

    
    /**
     * Tulostetaan transaktion tiedot
     * @param os tietovirta mihin tulostetaan
     */
    public void print(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    /**
     * Antaa transaktion seuraavan rekisterinumeron.
     * @return transaktion uusi tunnus_nro
     * @example
     * <pre name="test">
     *   Transaktio trans1 = new Transaktio();
     *   trans1.getTransactionId() === 0;
     *   trans1.register();
     *   Transaktio trans2 = new Transaktio();
     *   trans2.register();
     *   int n1 = trans1.getTransactionId();
     *   int n2 = trans2.getTransactionId();
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
     * TODO: Hakeeko tämä saman ID:n mikä osakkeessa on?
     * Palautetaan mille osakkeelle transaktio kuuluu
     * @return osakkeen id
     */
    public int getStockId() {
        return stockId;
    }
    
    /**
    * Asettaa tunnusnumeron ja samalla varmistaa että
    * seuraava numero on aina suurempi kuin tähän mennessä suurin.
    * @param id asetettava tunnusnumero
    */
    
    public void setTransactionId(int id) {
        this.transactionId = id;
        if (transactionId >= nextId) nextId = transactionId + 1;
    }

    /**
     * Palauttaa transaktion tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return transaktio tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Transaktio transaktio = new Transaktio();
     *   transaktio.parse("1 | 1 | 30.11.2007 | Osto | 27.32 | 200 | 54.64 | 5518.64 |");
     *   transaktio.toString()    === "1 | 1 | 30.11.2007 | Osto | 27.32 | 200 | 54.64 | 5518.64 |";
     * </pre>
     */
    @Override
    public String toString() {
        return "" + 
                getTransactionId() + "|" +
                getStockId() + "|" +
                date + "|" +
                type + "|" +
                stockPrice + "|" +
                amount + "|" +
                expenses + "|" +
                totalPrice + "|";
    }
    
    /**
     * Selvittää transaktion tiedot | erotellusta merkkijonosta
     * Pitää huolen että nextId on suurempi kuin tuleva transactionId.
     * @param line josta transaktion tiedot otetaan
     * 
     * @example
     * <pre name="test">
     *   Transaktio transaction = new Transaktio();
     *   transaction.parse("1 | 1 | 30.11.2007 | Osto | 27.32 | 200 | 54.64 | 5518.64 |");
     *   transaction.getId() === 3;
     *   transaction.toString().startsWith("1 | 1 | 30.11.2007 | Osto | 27.32 | 200 | 54.64 | 5518.64 |") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
     *
     *   transaction.register();
     *   int n = transaction.getId();
     *   transaction.parse(""+(n+20));       // Otetaan merkkijonosta vain tunnusnumero
     *   transaction.register();           // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi
     *   transaction.getId() === n+20+1;
     *     
     * </pre>
     */
    public void parse(String line) {
        StringBuilder sb = new StringBuilder(line);
        setTransactionId(Mjonot.erota(sb, '|', getTransactionId()));
        setStockId(Mjonot.erota(sb, '|', getStockId()));
        date = Mjonot.erota(sb, '|', date);
        type = Mjonot.erota(sb, '|', type);
        stockPrice = Mjonot.erota(sb, '|', stockPrice);
        amount = Mjonot.erota(sb, '|', amount);
        expenses = Mjonot.erota(sb, '|', expenses);
        totalPrice = Mjonot.erota(sb, '|', totalPrice);
        
    }
    
    public void setStockId(int newId) {
		stockId = newId;
		
	}


	/**
     * TODO: Mitä pääohjelmaan?
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Transaktio transaktio = new Transaktio();
        Transaktio transaktio2 = new Transaktio();
        
        // transaktio.rekisteroi();
        // transaktio2.rekisteroi();
        
        transaktio.tulosta(System.out);
        // transaktio.vastaaTransaktio();

        transaktio2.tulosta(System.out);
        // transaktio2.vastaaTransaktio();
    }
}