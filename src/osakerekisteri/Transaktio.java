package osakerekisteri;

import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

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
public class Transaktio implements Cloneable {
    
    private int     transactionId;
    private LocalDate  date = LocalDate.now();
    private String  type;
    private double  stockPrice;
    private int     amount;
    private double  expenses;
    private double  totalPrice;
    private int     stockId;
    
    
    private static int nextId = 1;
    
    /**
     * Tulostetaan transaktion tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(" ID " + String.format("%03d", transactionId));
        out.println(" Type " + type);
        out.println(" Date " + date);
        out.println(" Amount " + amount + " kpl");
        out.println(" Price " + String.format("%4.2f", stockPrice) + " €");
        out.println(" Expenses " + String.format("%4.2f", expenses) + " €");
        out.println(" Total Price " + String.format("%4.2f", totalPrice) + " €");
        out.println("--------------------------");
    }

    
    /**
     * Alustetaan tietyn osakkeen transaktio.  
     * @param stockId osakkeen id
     */
    public Transaktio(int stockId) {
        this.stockId = stockId;
    }


    /**
     * Alustetaan transaktio. Toistaiseksi ei tarvitse tehdä mitään
     */
    public Transaktio() {
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
     * @return transaktion uusi id
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
     * Palautetaan mille osakkeelle transaktio kuuluu
     * @return osakkeen id
     */
    public int getStockId() {
        return stockId;
    }
    
    
    /**
     * @return tyyppi
     */
    public String getType() {
    	return type;
    }
    
    
    /**
     * @return pvm
     */
    public LocalDate getDate() {
    	return date;
    }
    
    
    /**
     * @return määrä
     */
    public int getAmount() {
    	return amount;
    }
    
    
    /**
     * @return hinta
     */
    public double getStockPrice() {
    	return stockPrice;
    }
    
    
    /**
     * @return kulut
     */
    public double getExpenses() {
    	return expenses;
    }
    
    
    /**
     * @return kokonaishinta
     */
    public double getTotalPrice() {
        double total = 0;
        total = amount * stockPrice + expenses;
    	return total;
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
     *   transaktio.parse("1 | 1 | 2007-11-30 | Osto | 27.32 | 200 | 54.64 | 5518.64 |");
     *   transaktio.toString()    === "1|1|2007-11-30|Osto|27.32|200|54.64|5518.64|";
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
     * Selvittää transaktion tiedot | erotellusta merkkijonosta.
     * Pitää huolen että nextId on suurempi kuin tuleva transactionId.
     * @param line josta transaktion tiedot otetaan
     * @example
     * <pre name="test">
     *   Transaktio transaction = new Transaktio();
     *   transaction.parse("1 | 1 | 30.11.2007 | Osto | 27.32 | 200 | 54.64 | 5518.64 |");
     *   transaction.getTransactionId() === 1;
     *   transaction.toString().startsWith("1 | 1 | 30.11.2007 | Osto | 27.32 | 200 | 54.64 | 5518.64 |") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
     *
     *   transaction.register();
     *   int n = transaction.getTransactionId();
     *   transaction.parse(""+(n+20));       // Otetaan merkkijonosta vain tunnusnumero
     *   transaction.register();            // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi
     *   transaction.getTransactionId() === n+20+1;
     *     
     * </pre>
     */
    public void parse(String line) {
        StringBuilder sb = new StringBuilder(line);
        setTransactionId(Mjonot.erota(sb, '|', getTransactionId()));
        setStockId(Mjonot.erota(sb, '|', getStockId()));
        date = LocalDate.parse(Mjonot.erota(sb, '|', date.toString()));
        type = Mjonot.erota(sb, '|', type);
        stockPrice = Mjonot.erota(sb, '|', stockPrice);
        amount = Mjonot.erota(sb, '|', amount);
        expenses = Mjonot.erota(sb, '|', expenses);
        totalPrice = Mjonot.erota(sb, '|', totalPrice);
        
    }
    
    
    /**
     * @param newId asetetaan osakkeelle uusi id.
     */
    public void setStockId(int newId) {
		stockId = newId;
	}
    

    /**
     * @param s määrä
     * @return määrä
     */
    public String setAmount(int s) {
        if (s < 0) return "";
        this.amount = s;
        return null;
    }


    /**
     * @param s osakkeen hinta
     * @return osakkeen hinta
     */
    public String setPrice(double s) {
        if (s < 0) return "";
        this.stockPrice = s;
        return null;
    }
    
    /**
     * Tehdään identtinen klooni transaktiosta
     * @return Object kloonattu transaktio
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Transaktio trans = new Transaktio();
     *   trans.parse("1|30.11.2007|Osto|27.32|200|54.64|5518.64|1");
     *   Transaktio klooni = trans.clone();
     *   klooni.toString() === trans.toString();
     *   trans.parse("2|30.11.2007|Myynti|25.00|100|10.00|2500.00|1");
     *   klooni.toString().equals(trans.toString()) === false;
     * </pre>
     */
    @Override
    public Transaktio clone() throws CloneNotSupportedException { 
        Transaktio klooni = new Transaktio();
        klooni.parse(this.toString());
        return klooni;
    }

    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Transaktio transaktio = new Transaktio();
        Transaktio transaktio2 = new Transaktio();
        transaktio.tulosta(System.out);
        transaktio2.tulosta(System.out);
    }

	/**
	 * @param s kulut
	 * @return osakkeen kulut
	 */
	public String setExpenses(double s) {
		if (s < 0) return "";
		this.expenses = s;
		return null;
	}

	/**
     * @return transaktioiden kenttien lukumäärä
     */
	public int getFields() {
		return 6;
	}
	
	/**
     * @return ensimmäinen käyttäjän syötettävän kentän indeksi
     */
    public int firstField() {
        return 1;
    }

    /**
     * @param k Minkä kentän sisältö halutaan
     * @return valitun kentän sisältö
     * @example
     * <pre name="test">
     *   Transaktio trans = new Transaktio();
     *   trans.parse("Osto|30.11.2007|200|27.32|54.64|5518.64|");
     *   trans.giveTransaction(0) === "Osto";   
     *   trans.giveTransaction(1) === "30.11.2007";   
     *   trans.giveTransaction(2) === "200";   
     *   trans.giveTransaction(3) === "27.32";   
     *   trans.giveTransaction(4) === "54.64";
     *   trans.giveTransaction(5) === "5518.64";     
     * </pre>
     */

	public String giveTransaction(int k) {
		switch (k) {
        case 0:
            return "" + type;
        case 1:
            return "" + date;
        case 2:
            return "" + amount;
        case 3:
            return "" + stockPrice;
        case 4:
            return "" + expenses;
        case 5:
            return "" + totalPrice;
        default:
            return "???";
	}
    }	
}