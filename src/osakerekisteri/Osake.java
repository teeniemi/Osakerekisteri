package osakerekisteri;

import java.io.OutputStream;
import java.io.PrintStream;

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

public class Osake {
	
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
     *   stock.giveStock();
     *   stock.getName() =R= "Nokia Oyj.*";
     * </pre>
     */
    public String getName() {
        return stockName;
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
	 * osake1.getNextId() === 0;
	 * osake1.register() === 1;
	 * Osake osake2 = new Osake();
	 * osake2.register();
	 * int n1 = osake1.getNextId();
	 * int n2 = osake2.getNextId();
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
	 * Haetaan osakkeen tiedot
	 */
	
	public void giveStock() {
		stockName = "Nokia Oyj";
		amount = 500;
		averagePrice = 2.20;
		totalPrice = 1100.00;
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
     *   stock.toString().startsWith("1|Nokia Oyj|200|3.12|624.00|") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
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
     *   stock.getId() === 3;
     *   stock.toString().startsWith("1|Nokia Oyj|200|3.12|624.00|") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
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
		
		stock.print(System.out);
		stock.giveStock();
		stock.print(System.out);
		
		stock2.print(System.out);
		stock2.giveStock();
		stock2.print(System.out);
	}

		/**
		* @param s osakkeelle laitettava nimi
		* @return virheilmoitus, null jos ok
		*/
	public String setName(String s) {
		if (s == " " || s == "") return "nyt v**** oikeesti. yritäppä vielä...";
		return this.stockName = s;
		 }

		public String setAmount(int s) {
			if (s < 0) return "nyt v**** oikeesti. yritäppä vielä...";
			this.amount = s;
			return null;
		}

		public String setAveragePrice(double s) {
			if (s < 0) return "nyt v**** oikeesti. yritäppä vielä...";
			this.averagePrice = s;
			return null;
		}

		public String setTotalPrice(double s) {
			if (s < 0) return "nyt v**** oikeesti. yritäppä vielä...";
			this.totalPrice = s;
			return null;
		}

		public String getAmount() {
			return amount+"";
		}

		public String getAveragePrice() {
			return averagePrice+"";
		}

		public String getTotalPrice() {
			return totalPrice+"";
		}
		
		
}
