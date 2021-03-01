package osakerekisteri;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * |------------------------------------------------------------------------|
 * | Luokan nimi: Osake                                 | Avustajat:        |
 * |-------------------------------------------------------------------------
 * | Vastuualueet:                                      |                   |
 * |                                                    |                   |
 * | - ei tiedï¿½ Osakerekisteristï¿½, osakkeista eikï¿½|                   |
 * |   kï¿½yttï¿½liittymï¿½stï¿½                        |                   |
 * | - tietï¿½ï¿½ Osakkeen kentï¿½t (stockId, stockName,|                   |
 * |   amount, averagePrice, totalPrice)                |                   | 
 * | - osaa tarkistaa tietyn kentï¿½n                   |                   |
 * |   oikeellisuuden (syntaksin)                       |                   |
 * | - osaa muuttaa |Nordea Oyj|..| -                   |                   |
 * |   merkkijonon osakkeen tiedoiksi                   |                   |
 * | - osaa antaa merkkijonona i:n kentï¿½n             |                   |
 * |   tiedot                                           |                   |
 * | - osaa laittaa merkkijonon i:neksi                 |                   |
 * |   kentï¿½ksi                                       |                   |
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
	 * Tulostetaan osakkeen tiedot
	 * @param out tietovirta, johon tulostetaan
	 */
	public void print(PrintStream out) {
		out.println("ID " + String.format("%03d", stockId));
		out.println(" Name " + stockName);
		out.println(" Amount " + amount);
		out.println(" Average price " + String.format("%4.2f",averagePrice) + "€"); 
		out.println(" Total price " + String.format("%4.2f", totalPrice) + "€");
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
	 * Rekisteröi osakkeelle id:n ja vierittää kiveä eteenpäin
	 * @return osakeid
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
	
	public int getNextId() {
		return stockId;
	}
	
	/**
	 * Haetaan osakkeen tiedot
	 * @return osakkeen tiedot
	 */
	
	
	public void giveStock() {
		stockId = 001;
		stockName = "Nokia Oyj";
		amount = 500;
		averagePrice = 2.20;
		totalPrice = 1100.00;
	}
	
	
	/**
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		
		
		Osake osake = new Osake();
		Osake osake2 = new Osake();
		
		osake.register();
		osake2.register();
		
		osake.print(System.out);
		osake.giveStock();
		osake.print(System.out);
		
		osake2.print(System.out);
		osake2.giveStock();
		osake2.print(System.out);
	}

}
