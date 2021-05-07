package osakerekisteri;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * @author Jesse Korolainen & Teemu Nieminen
 * @version 6.5.2021
 *
 */

/**
 * Osakerekisteri-luokka, joka huolehtii osakkeista.  Pääosin kaikki metodit
 * ovat vain "välittäjämetodeja" jäsenistöön.
 *
 * Testien alustus
 * @example
 * <pre name="testJAVA">
 * #import osakerekisteri.StoreException;
 *  private Osakerekisteri osakerekisteri;
 *  private Osake stock1;
 *  private Osake stock2;
 *  private int jid1;
 *  private int jid2;
 *  private Transaktio trans21;
 *  private Transaktio trans11;
 *  private Transaktio trans22; 
 *  private Transaktio trans12; 
 *  private Transaktio trans23;
 *  
 *  @SuppressWarnings("javadoc")
 *  public void alustaOsakerekisteri() {
 *  osakerekisteri = new Osakerekisteri();
 *  stock1 = new Osake(); stock1.testi(1); stock1.register();
 *  stock2 = new Osake(); stock2.testi(2); stock2.register();
 *  trans21 = new Transaktio(); trans21.testi(stock2.getId());
 *  trans11 = new Transaktio(); trans11.testi(stock1.getId());
 *  trans22 = new Transaktio(); trans22.testi(stock2.getId()); 
 *  trans12 = new Transaktio(); trans12.testi(stock1.getId()); 
 *  trans23 = new Transaktio(); trans23.testi(stock2.getId());
 *    try {
 *    osakerekisteri.add(stock1);
 *    osakerekisteri.add(stock2);
 *    osakerekisteri.add(trans21);
 *    osakerekisteri.add(trans11);
 *    osakerekisteri.add(trans22);
 *    osakerekisteri.add(trans12);
 *    osakerekisteri.add(trans23);
 *    } catch ( Exception e) {
 *       System.err.println(e.getMessage());
 *    }
 *  }
 * </pre>
*/
public class Osakerekisteri {
        private Osakkeet stocks = new Osakkeet();
        private Transaktiot transactions = new Transaktiot();

        /**
         * Palautaa osakerekisterin osakemäärän
         * @return osakemäärä
         */
        public int getStocks() {
            return stocks.getAmount();
        }


        /**
         * Poistaa osakkeista ja relaatioista ne joilla on nro. kesken.
         * @param nr viitenumero, jonka mukaan poistetaan
         * @return montako osaketta poistettiin
         */
        public int delete(@SuppressWarnings("unused") int nr) {
            return 0;
        }


        /**
         * Lisää rekisteriin uuden osakkeen
         * @param stock lisättävä osake
         * @throws StoreException jos lisäystä ei voida tehdä
         * @example
         * <pre name="test">
         * #THROWS StoreException
         * Osakerekisteri osakerekisteri = new Osakerekisteri();
         * Osake stock1 = new Osake(), stock2 = new Osake();
         * stock1.register(); stock2.register();
         * osakerekisteri.getStocks() === 0;
         * osakerekisteri.add(stock1); osakerekisteri.getStocks() === 1;
         * osakerekisteri.add(stock2); osakerekisteri.getStocks() === 2;
         * osakerekisteri.add(stock1); osakerekisteri.getStocks() === 3;
         * osakerekisteri.getStocks() === 3;
         * osakerekisteri.giveStock(0) === stock1;
         * osakerekisteri.giveStock(1) === stock2;
         * osakerekisteri.giveStock(2) === stock1;
         * osakerekisteri.giveStock(3) === stock1; #THROWS IndexOutOfBoundsException 
         * osakerekisteri.add(stock1); osakerekisteri.getStocks() === 4;
         * osakerekisteri.add(stock1); osakerekisteri.getStocks() === 5;
         * osakerekisteri.add(stock1);
         * </pre>
         */
        public void add(Osake stock) throws StoreException {
            stocks.add(stock);
        }


        /**
         * Palauttaa i:n osakkeen
         * @param i monesko osake palautetaan
         * @return viite i:teen osakkeeseen
         * @throws IndexOutOfBoundsException jos i väärin
         */
        public Osake giveStock(int i) throws IndexOutOfBoundsException {
            return stocks.give(i);
        }

        /**
         * Tallettaa osakerekisterin tiedot tiedostoon
         * @throws StoreException jos tallettamisessa ongelmia
         */
        public void save() throws StoreException {
            stocks.save();
            transactions.save();
        }
        
        /** 
         * Palauttaa "taulukossa" hstockehtoon vastaavien jäsenten viitteet 
         * @param hstockehto hstockehto  
         * @param k etsittävän kentän indeksi  
         * @return tietorakenteen löytyneistä jäsenistä 
         * @throws StoreException Jos jotakin menee väärin
         */ 
        public Collection<Osake> etsi(String hstockehto, int k) throws StoreException { 
            return stocks.search(hstockehto, k); 
        } 
        
        /**
         * Asettaa tiedostojen perusnimet
         * @param nimi uusi nimi
         */
        public void setTiedosto(String nimi) {
            File dir = new File(nimi);
            dir.mkdirs();
            String hakemistonNimi = "";
            if ( !nimi.isEmpty() ) hakemistonNimi = nimi +"/";
            stocks.setFileBasicName(hakemistonNimi + "stocks");
            transactions.setFileBasicName(hakemistonNimi + "transactions");
        }
        
        /**
         * Lukee osakerekisterin tiedot tiedostosta
         * @param name jota käyteään lukemisessa
         * @throws StoreException jos lukeminen epäonnistuu
         * 
         * @example
         * <pre name="test">
         * #THROWS StoreException 
         * #import java.io.*;
         * #import java.util.*;
         * osakerekisteri = new Osakerekisteri();
         *  String hakemisto = "testikelmit";
         *  File dir = new File(hakemisto);
         *  File ftied  = new File(hakemisto+"/stocks.dat");
         *  File fhtied = new File(hakemisto+"/transactions.dat");
         *  dir.mkdir();  
         *  ftied.delete();
         *  fhtied.delete();
         *  osakerekisteri.readFromFile(hakemisto); #THROWS StoreException
         *  alustaOsakerekisteri();
         *  osakerekisteri.setTiedosto(hakemisto);
         *  osakerekisteri.tallenna();
         *  osakerekisteri = new Osakerekisteri();
         *  osakerekisteri.readFromFile(hakemisto);
         *  Collection<Osake> kaikki = osakerekisteri.etsi("",-1); 
         *  Iterator<Osake> it = kaikki.iterator();
         *  it.next().toString() === stock1.toString();
         *  it.next().toString() === stock2.toString();
         *  it.hasNext() === false;
         *  List<Transaktio> loytyneet = osakerekisteri.giveTransactions(stock1);
         *  Iterator<Transaktio> ih = loytyneet.iterator();
         *  ih.next().toString() === trans11.toString();
         *  ih.next().toString() === trans12.toString();
         *  ih.hasNext() === false;
         *  loytyneet = osakerekisteri.giveTransactions(stock2);
         *  ih = loytyneet.iterator();
         *  ih.next().toString() === trans21.toString();
         *  ih.next().toString() === trans22.toString();
         *  ih.next().toString() === trans23.toString();
         *  ih.hasNext() === false;
         *  osakerekisteri.add(stock2);
         *  osakerekisteri.add(trans23);
         *  osakerekisteri.tallenna();
         *  ftied.delete()  === true;
         *  fhtied.delete() === true;
         *  File fbak = new File(hakemisto+"/stocks.bak");
         *  File fhbak = new File(hakemisto+"/transactions.bak");
         *  fbak.delete() === true;
         *  fhbak.delete() === true;
         *  dir.delete() === true;
         * </pre>
         */
        public void readFromFile(String name) throws StoreException {
            stocks = new Osakkeet(); // jos luetaan olemassa olevaan niin helpoin tyhjentää näin
            transactions = new Transaktiot();

            setTiedosto(name);
            stocks.readFromFile();
            transactions.readFromFile();
        }

        /**
         * Tallenttaa osakerekisterin tiedot tiedostoon.  
         * Vaikka jäsenten tallettamien epäonistuisi, niin yritetään silti tallettaa
         * harrastuksia ennen poikkeuksen heittämistä.
         * @throws StoreException jos tallettamisessa ongelmia
         */
        public void tallenna() throws StoreException {
            String virhe = "";
            try {
                stocks.save();
            } catch ( StoreException ex ) {
                virhe = ex.getMessage();
            }

            try {
                transactions.save();
            } catch ( StoreException ex ) {
                virhe += ex.getMessage();
            }
            if ( !"".equals(virhe) ) throw new StoreException(virhe);
        }
        
        /**
         * Testiohjelma osakerekisterista
         * @param args ei käytössä
         */
        public static void main(String args[]) {
            Osakerekisteri osakerekisteri = new Osakerekisteri();

            try {
                // osakerekisteri.readFromFile("osakkeet");

                Osake stock1 = new Osake(), stock2 = new Osake();
                stock1.register();
                stock1.giveStock(0);
                stock2.register();
                stock2.giveStock(2);
                Transaktio transaktio1 = new Transaktio();
                transaktio1.register();
                transaktio1.testi(stock1.getId());
                Transaktio transaktio2 = new Transaktio();
                transaktio2.register();
                transaktio2.testi(stock1.getId());

                osakerekisteri.add(stock1);
                osakerekisteri.add(transaktio1);
                osakerekisteri.add(stock2);
                osakerekisteri.add(transaktio2);

                System.out.println("============= osakerekisterin testi =================");

                for (int i = 0; i < osakerekisteri.getStocks(); i++) {
                    Osake osake = osakerekisteri.giveStock(i);
                    System.out.println("Stock at: " + i);
                    osake.print(System.out);
                    List<Transaktio> transaktiot = osakerekisteri.giveTransactions(osake);
                    for (Transaktio alkio : transaktiot) {
                    	alkio.print(System.out);
                    }
                }
            } catch (StoreException ex) {
                System.out.println(ex.getMessage());
            }
        }


		/**
		 * Lisätään transaktio
		 * @param transaktio lisää transaktion
		 */
		public void add(Transaktio transaktio) {
			transactions.add(transaktio);
		}


		/**
		 * Lisätään transaktiot osakkeelle
		 * @param osake osake
		 * @return osakkeen transaktiot
		 */
		public List<Transaktio> giveTransactions(Osake osake) {
			return transactions.giveTransactions(osake.getId());
		}


		/**
		 * Vaihdetaan transaktio
		 * @param transaction korvaa transaktion
		 */
		public void replace(Transaktio transaction) {
			transactions.replace(transaction);	
		}


		/**
		 * Haetaan osakkeen keskiarvohinta
		 * @param stockAtPlace osake
		 * @return palauttaa osakkeen keskiarvohinnan
		 */
		public double getAverage(Osake stockAtPlace) {
			return transactions.getAverage(stockAtPlace.getId());
		}


		/**
		 * Haetaan päivämäärä
		 * @param stockAtPlace osake
		 * @return palauttaa osakkeen päivämäärän
		 */
		public String getDate(Osake stockAtPlace) {
			return transactions.getDate(stockAtPlace.getId());
		}

		/**
		 * Palautetaan osakkeiden määrä
		 * @param stockAtPlace osake
		 * @return palauttaa osakemäärän
		 */
		public String getStockAmount(Osake stockAtPlace) {
			return transactions.getStockAmount(stockAtPlace.getId());
		}

		/**
		 * Palautetaan osakkeen kulut
		 * @param stockAtPlace osake
		 * @return palauttaa kulut
		 */
		public String getExpenses(Osake stockAtPlace) {
			return transactions.getExpenses(stockAtPlace.getId());
		}

		/**
		 * Palautetaan osakkeiden kokonaishinta
		 * @param stockAtPlace osake
		 * @return palauttaa kokonaishinnan
		 */
		public String getTotalPrice(Osake stockAtPlace) {
			return transactions.getTotalPrice(stockAtPlace.getId());
		}

		/**
		 * Poistetaan osake
		 * @param stock osake
		 * @return poistaa osakkeen
		 *  @example
	     * <pre name="test">
	     * #THROWS Exception
	     *   alustaOsakerekisteri();
	     *   osakerekisteri.giveStock(0) === stock1;
	     *   osakerekisteri.deleteStock(stock1);
	     *   osakerekisteri.giveStock(0).equals(stock1) === false;
	     */ 
		public int deleteStock(Osake stock) {
			if ( stock == null ) return 0;
	        int ret = stocks.delete(stock.getId()); 
	        transactions.deleteStocksTransactions(stock.getId()); 
	        return ret; 
	    }
		
		 /** 
	     * Poistaa tämän transaktion
		 * @param transaction poistettava transaktio
	     * @example
	     * <pre name="test">
	     * #THROWS Exception
	     *   alustaOsakerekisteri();
	     *   osakerekisteri.giveTransactions(stock1).size() === 2;
	     *   osakerekisteri.deleteTransaction(trans11);
	     *   osakerekisteri.giveTransactions(stock1).size() === 1;
	     */ 
	    public void deleteTransaction(Transaktio transaction) { 
	        transactions.delete(transaction); 
	    }
}
