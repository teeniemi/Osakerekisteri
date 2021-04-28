package osakerekisteri;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * @author Jesse Korolainen & Teemu Nieminen
 * @version 1.3.2021
 *
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
         * osakerekisteri.add(stock1); #THROWS StoreException
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
         * #THROWS SailoException 
         * #import java.io.*;
         * #import java.util.*;
         * 
         *  Osakerekisteri osakerekisteri = new Osakerekisteri();
         *  
         *  Osake stock1 = new Osake(); stock1.testi(); stock1.register();
         *  Osake stock2 = new Osake(); stock2.testi(); stock2.register();
         *  Transaktio trans21 = new Transaktio(); trans21.testi(stock2.getId());
         *  Transaktio trans11 = new Transaktio(); trans11.testi(stock1.getId());
         *  Transaktio trans22 = new Transaktio(); trans22.testi(stock2.getId()); 
         *  Transaktio trans12 = new Transaktio(); trans12.testi(stock1.getId()); 
         *  Transaktio trans23 = new Transaktio(); trans23.testi(stock2.getId());
         *   
         *  String hakemisto = "testikelmit";
         *  File dir = new File(hakemisto);
         *  File ftied  = new File(hakemisto+"/nimet.dat");
         *  File fhtied = new File(hakemisto+"/harrastukset.dat");
         *  dir.mkdir();  
         *  ftied.delete();
         *  fhtied.delete();
         *  osakerekisteri.readFromFile(hakemisto); #THROWS SailoException
         *  osakerekisteri.add(stock1);
         *  osakerekisteri.add(stock2);
         *  osakerekisteri.add(trans21);
         *  osakerekisteri.add(trans11);
         *  osakerekisteri.add(trans22);
         *  osakerekisteri.add(trans12);
         *  osakerekisteri.add(trans23);
         *  osakerekisteri.tallenna();
         *  osakerekisteri = new Osakerekisteri();
         *  osakerekisteri.readFromFile(hakemisto);
         *  Collection<Osake> kaikki = osakerekisteri.etsi("",-1); 
         *  Iterator<Osake> it = kaikki.iterator();
         *  it.next() === stock1;
         *  it.next() === stock2;
         *  it.hasNext() === false;
         *  List<Transaktio> loytyneet = osakerekisteri.giveTransactions(stock1);
         *  Iterator<Transaktio> ih = loytyneet.iterator();
         *  ih.next() === trans11;
         *  ih.next() === trans12;
         *  ih.hasNext() === false;
         *  loytyneet = osakerekisteri.giveTransactions(stock2);
         *  ih = loytyneet.iterator();
         *  ih.next() === trans21;
         *  ih.next() === trans22;
         *  ih.next() === trans23;
         *  ih.hasNext() === false;
         *  osakerekisteri.add(stock2);
         *  osakerekisteri.add(trans23);
         *  osakerekisteri.tallenna();
         *  ftied.delete()  === true;
         *  fhtied.delete() === true;
         *  File fbak = new File(hakemisto+"/nimet.bak");
         *  File fhbak = new File(hakemisto+"/harrastukset.bak");
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
		 * @param transaktio lisää transaktion
		 */
		public void add(Transaktio transaktio) {
			transactions.add(transaktio);
		}


		/**
		 * @param osake osake
		 * @return osakkeen transaktiot
		 */
		public List<Transaktio> giveTransactions(Osake osake) {
			return transactions.giveTransactions(osake.getId());
		}


		/**
		 * @param transaction korvaa transaktion
		 */
		public void replace(Transaktio transaction) {
			transactions.replace(transaction);
			
		}


		/**
		 * @param stockAtPlace osake
		 * @return palauttaa osakkeen keskiarvohinnan
		 */
		public double getAverage(Osake stockAtPlace) {
			return transactions.getAverage(stockAtPlace.getId());
			
		}


		/**
		 * @param stockAtPlace osake
		 * @return palauttaa osakkeen päivämäärän
		 */
		public String getDate(Osake stockAtPlace) {
			return transactions.getDate(stockAtPlace.getId());
		}


		/**
		 * @param stockAtPlace osake
		 * @return palauttaa osakemäärän
		 */
		public String getStockAmount(Osake stockAtPlace) {
			return transactions.getStockAmount(stockAtPlace.getId());
		}


		/**
		 * @param stockAtPlace osake
		 * @return palauttaa kulut
		 */
		public String getExpenses(Osake stockAtPlace) {
			return transactions.getExpenses(stockAtPlace.getId());
		}


		/**
		 * @param stockAtPlace osake
		 * @return palauttaa kokonaishinnan
		 */
		public String getTotalPrice(Osake stockAtPlace) {
			return transactions.getTotalPrice(stockAtPlace.getId());
		}


		/**
		 * @param stock osake
		 * @return poistaa osakkeen
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
	     *   osakerekisteri.poistaTransaktio(trans11);
	     *   osakerekisteri.giveTransactions(stock1).size() === 1;
	     */ 
	    public void deleteTransaction(Transaktio transaction) { 
	        transactions.delete(transaction); 
	    }

}
