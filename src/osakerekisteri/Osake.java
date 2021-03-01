package osakerekisteri;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * |------------------------------------------------------------------------|
 * | Luokan nimi: Osake                                 | Avustajat:        |
 * |-------------------------------------------------------------------------
 * | Vastuualueet:                                      |                   |
 * |                                                    |                   |
 * | - ei tied� Osakerekisterist�, osakkeista eik�      |                   |
 * |   k�ytt�liittym�st�                                |                   |
 * | - tiet�� Osakkeen kent�t (stockId, stockName,      |                   |
 * |   amount, averagePrice, totalPrice)                |                   | 
 * | - osaa tarkistaa tietyn kent�n                     |                   |
 * |   oikeellisuuden (syntaksin)                       |                   |
 * | - osaa muuttaa |Nordea Oyj|..| -                   |                   |
 * |   merkkijonon osakkeen tiedoiksi                   |                   |
 * | - osaa antaa merkkijonona i:n kent�n               |                   |
 * |   tiedot                                           |                   |
 * | - osaa laittaa merkkijonon i:neksi                 |                   |
 * |   kent�ksi                                         |                   |
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
	
	/**
	 * Tulostetaan osakkeen tiedot
	 * @param out tietovirta, johon tulostetaan
	 */
	public void tulosta(PrintStream out) {
		out.println("ID " + String.format("%03d", stockId));
		out.println(" Name " + stockName);
		out.println(" Amount " + amount);
		out.println(" Average price " + String.format("%4.2f",averagePrice) + "�"); 
		out.println(" Total price " + String.format("%4.2f", totalPrice) + "�");
		out.println("--------------------------------------");
		
	}
	
	/**
	 * Tulostetaan osakkeen tiedot
	 * @param os tietovirta, johon tulostetaan
	 */
	
	public void tulosta (OutputStream os) {
		tulosta(new PrintStream(os));
	}
	
	/**
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		
		
		Osake osake = new Osake();
		Osake osake2 = new Osake();
		
		// osake.rekisteroi();
		// osake2.rekisteroi();
		
		osake.tulosta(System.out);
		// osake.vastaaOsake();
		osake.tulosta(System.out);
		
		osake2.tulosta(System.out);
		// osake2.vastaaOsake();
		osake.tulosta(System.out);
	}

}
