package osakerekisteri;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Comparator;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * |------------------------------------------------------------------------|
 * | Luokan nimi: Osake                                 | Avustajat:        |
 * |-------------------------------------------------------------------------
 * | Vastuualueet:                                      |                   |
 * |                                                    |                   |
 * | - ei tiedä Osakerekisteristä, osakkeista eikä      |                   |
 * |   käyttöliittymästä                                |                   |
 * | - tietää Osakkeen kentät (stockId, stockName,      |                   |
 * |   amount, averagePrice, totalPrice)                |                   | 
 * | - osaa tarkistaa tietyn kentän                     |                   |
 * |   oikeellisuuden (syntaksin)                       |                   |
 * | - osaa muuttaa |Nordea Oyj|..| -                   |                   |
 * |   merkkijonon osakkeen tiedoiksi                   |                   |
 * | - osaa antaa merkkijonona i:n kentän               |                   |
 * |   tiedot                                           |                   |
 * | - osaa laittaa merkkijonon i:neksi                 |                   |
 * |   kentäksi                                         |                   |
 * | - osaa muuttaa olion tiedoston riviksi             |                   |
 * |                                                    |                   |
 * |-------------------------------------------------------------------------
 * @author Teemu & Jesse
 * @version 1.3.2021
 *
 */

public class Osake implements Cloneable{
	
	private int 		stockId = 0;
	private String 		stockName = "";
	private int 		amount = 0;
	private double 		averagePrice = 0;
	private double 		totalPrice = 0;
	
	private static int nextId = 1;
	
    /**
     * @return osakkeen nimi
     * @example
     * <pre name="test">
     *   Osake stock = new Osake();
     *   stock.giveStock(0);
     *   stock.getName() =R= "";
     * </pre>
     */
    public String getName() {
        return stockName;
    }
	
    
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot Osakkeelle
     * Kaikki arvot arvotaan, jotta kahdella transaktiolla ei olisi
     * samoja tietoja.
     */

   public void testi() {
	    stockName = "Nokia Oyj";
	    amount = 100;
   }


	/**
	 * Tulostetaan osakkeen tiedot
	 * @param out tietovirta, johon tulostetaan
	 */
	public void print(PrintStream out) {
		out.println("ID " + String.format("%03d", stockId));
		out.println(" Name " + stockName);
		out.println(" Amount " + amount);
		out.println(" Average price " + String.format("%4.2f",averagePrice) + " €"); 
		out.println(" Total price " + String.format("%4.2f", totalPrice) + " €");
		out.println("--------------------------------------");
		
	}
	
	/**
	 * Tulostetaan osakkeen tiedot
	 * @param os tietovirta, johon tulostetaan
	 */
	
	public void print (OutputStream os) {
		print(new PrintStream(os));
	}
	
	/**
	 * Rekisteröi osakkeelle ID:n ja kasvattaa sitä yhdellä
	 * @return stock Id
	 * @example
	 * <pre name="test">
	 * Osake osake1 = new Osake();
	 * osake1.getId() === 0;
	 * osake1.register() === 2;
	 * Osake osake2 = new Osake();
	 * osake2.register();
	 * int n1 = osake1.getId();
	 * int n2 = osake2.getId();
	 * n1 === n2-1;
	 * </pre>
	 */
	
	public int register() {
		this.stockId = nextId;
		nextId++;
		return this.stockId;
	}
	
	/**
	 * Haetaan osakkeelle id
	 * @return osakeid
	 */
	
	public int getId() {
		return stockId;
	}
	
    /**
     * Eka kenttä joka on mielekäs kysyttäväksi
     * @return ekan kentän indeksi
     */
    public int firstField() {
        return 1;
    }

	 /** 
     * Jäsenten vertailija 
     */ 
    public static class Compare implements Comparator<Osake> { 
        private int k;  
         
        @SuppressWarnings("javadoc") 
        public Compare(int k) { 
            this.k = k; 
        } 
         
        @Override 
        public int compare(Osake stock1, Osake stock2) { 
            return stock1.giveStock(k).compareToIgnoreCase(stock2.giveStock(k)); 
        }
    } 

	/**
	 * Antaa k:n kentän sisällön merkkijonona
	 * @param k monennenko kentän sisältö palautetaan
	 * @return kentän sisältö merkkijonona
	 */
	public String giveStock(int k) {
		switch ( k ) {
		case 0: return "" + stockName;
		case 1: return "" + amount;
		case 2: return "" + averagePrice;
		case 3: return "" + totalPrice;
		default: return "Ee oo";
		}
	}
	
	/**
	 * @param k listaan tulevat arvot
	 * @return listan arvot
	 */
	public String getQuestion(int k) {
		switch ( k ) {
		case 0: return "ID";
		case 1: return "Stock Name";
		case 2: return "Amount";
		case 3: return "Average Price";
		case 4: return "Total Price";
		default: return "Ee oo";
		}
	}
	
	/**
     * Tehdään identtinen klooni osakkeesta
     * @return Object kloonattu osake
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Osake stock = new Osake();
     *   stock.parse("1|Nokia Oyj|200|3.12|624.00|"); 
     *   Osake kopio = stock.clone();
     *   kopio.toString() === stock.toString();
     *   stock.parse("2|Olvi Oyj|20|20.00|400.00|");
     *   kopio.toString().equals(stock.toString()) === false;
     * </pre>
     */
    @Override
    public Osake clone() throws CloneNotSupportedException {
        Osake uusi;
        uusi = (Osake) super.clone();
        return uusi;
    }
    
	
	/**
	* Asettaa tunnusnumeron ja samalla varmistaa että
	* seuraava numero on aina suurempi kuin tähän mennessä suurin.
	* @param id asetettava tunnusnumero
	*/
	
	private void setId(int id) {
		this.stockId = id;
		if (stockId >= nextId) nextId = stockId + 1;
	}
	
	/**
     * Palauttaa osakkeen tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return osake tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Osake stock = new Osake();
     *   stock.parse("1|Nokia Oyj|200|3.12|624.00|");
     *   stock.toString().startsWith("1|Nokia Oyj|200|3.12|624.00|") === false; // on enemmäkin kuin 3 kenttää, siksi loppu |
     * </pre>  
     */
    @Override
    public String toString() {
        return "" +
                getId() + "|" +
                getName() + "|" +
                amount + "|" +
                averagePrice + "|" +
                totalPrice + "|";
    }
    
    
    /**
     * Selvittää osakkeen tiedot | erotellusta merkkijonosta
     * Pitää huolen että nextId on suurempi kuin tuleva stockId.
     * @param line josta osakkeen tiedot otetaan
     * 
     * @example
     * <pre name="test">
     *   Osake stock = new Osake();
     *   stock.parse("1|Nokia Oyj|200|3.12|624.00|");
     *   stock.getId() === 1;
     *   stock.toString().startsWith("1|Nokia Oyj|200|3.12|") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
     *
     *   stock.register();
     *   int n = stock.getId();
     *   stock.parse(""+(n+20));       // Otetaan merkkijonosta vain tunnusnumero
     *   stock.register();           // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi
     *   stock.getId() === n+20+1;
     *     
     * </pre>
     */
    public void parse(String line) {
        StringBuilder sb = new StringBuilder(line);
        setId(Mjonot.erota(sb, '|', getId()));
        stockName = Mjonot.erota(sb, '|', stockName);
        amount = Mjonot.erota(sb, '|', amount);
        averagePrice = Mjonot.erota(sb, '|', averagePrice);
        totalPrice = Mjonot.erota(sb, '|', totalPrice);
    }
    
	
	
	/**
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		
		
		Osake stock = new Osake();
		Osake stock2 = new Osake();
		
		stock.register();
		stock2.register();
		
	}

/**
* @param s osakkeelle laitettava nimi
* @return virheilmoitus, null jos ok
*/
public String setName(String s) {
if (s == " " || s == "") return "";
return this.stockName = s;
 }

/**
* @param s määrä
* @return virheilmoitus, null jos ok
*/
public String setAmount(int s) {
    if (s < 0) return "";
    this.amount = s;
    return null;
}

/**
* @param s keskihinta
* @return virheilmoitus, null jos ok
*/
public String setAveragePrice(double s) {
    if (s < 0) return "";
    this.averagePrice = s;
    return null;
}

/**
* @param s kokonaishinta
* @return virheilmoitus, null jos ok
*/
public String setTotalPrice(double s) {
    if (s < 0) return "";
    this.totalPrice = s;
    return null;
}


/**
 * @return määrä
 */
public String getAmount() {
    return amount+"";
}


/**
 * @return keskihinta
 */
public String getAveragePrice() {
    return averagePrice+"";
}


/**
 * @return kokonaishinta
 */
public String getTotalPrice() {
    return totalPrice+"";
}

/**
 * @return 5 kenttää
 */
public int getFields() {
	return 5;
}
}
