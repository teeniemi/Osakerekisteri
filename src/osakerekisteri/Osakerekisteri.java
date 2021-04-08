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
        private String owner;


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
         * 
         * @param owner eli osakerekisterin omistaja, joka valitaan, kun käyttäjä käynnistää ohjelman
         * @return owner
         */
        
        public String setOwner(String owner) {
        	this.owner = owner;
        	return owner;
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
         * Lukee osakerekisterin tiedot tiedostosta
         * @param name jota käyteään lukemisessa
         * @throws StoreException jos lukeminen epäonnistuu
         */
        public void readFromFile(String name) throws StoreException {
            stocks.readFromFile(name);
        }


        /**
         * Tallettaa osakerekisterin tiedot tiedostoon
         * @throws StoreException jos tallettamisessa ongelmia
         */
        public void save() throws StoreException {
            stocks.save();
            // TODO: yritä tallettaa toinen vaikka toinen epäonnistuisi
        }
        
        /** 
         * Palauttaa "taulukossa" hakuehtoon vastaavien jäsenten viitteet 
         * @param hakuehto hakuehto  
         * @param k etsittävän kentän indeksi  
         * @return tietorakenteen löytyneistä jäsenistä 
         * @throws SailoException Jos jotakin menee väärin
         */ 
        public Collection<Osake> etsi(String hakuehto, int k) throws StoreException { 
            return stocks.etsi(hakuehto, k); 
        } 
        
        
        /**
         * Haetaan kaikki jäsen harrastukset
         * @param jasen jäsen jolle harrastuksia haetaan
         * @return tietorakenne jossa viiteet löydetteyihin harrastuksiin
         * @throws SailoException jos tulee ongelmia
         * @example
         * <pre name="test">
         * #THROWS SailoException
         * #import java.util.*;
         * 
         *  Kerho kerho = new Kerho();
         *  Jasen aku1 = new Jasen(), aku2 = new Jasen(), aku3 = new Jasen();
         *  aku1.rekisteroi(); aku2.rekisteroi(); aku3.rekisteroi();
         *  int id1 = aku1.getTunnusNro();
         *  int id2 = aku2.getTunnusNro();
         *  Harrastus pitsi11 = new Harrastus(id1); kerho.lisaa(pitsi11);
         *  Harrastus pitsi12 = new Harrastus(id1); kerho.lisaa(pitsi12);
         *  Harrastus pitsi21 = new Harrastus(id2); kerho.lisaa(pitsi21);
         *  Harrastus pitsi22 = new Harrastus(id2); kerho.lisaa(pitsi22);
         *  Harrastus pitsi23 = new Harrastus(id2); kerho.lisaa(pitsi23);
         *  
         *  List<Harrastus> loytyneet;
         *  loytyneet = kerho.annaHarrastukset(aku3);
         *  loytyneet.size() === 0; 
         *  loytyneet = kerho.annaHarrastukset(aku1);
         *  loytyneet.size() === 2; 
         *  loytyneet.get(0) == pitsi11 === true;
         *  loytyneet.get(1) == pitsi12 === true;
         *  loytyneet = kerho.annaHarrastukset(aku2);
         *  loytyneet.size() === 3; 
         *  loytyneet.get(0) == pitsi21 === true;
         * </pre> 
         */
        public List<Transaktio> annaHarrastukset(Osake stock) throws StoreException {
            return transactions.giveTransactions(stock.getId());
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
            stocks.setFileBasicName(hakemistonNimi + "osakkeet");
            transactions.setFileBasicName(hakemistonNimi + "transaktiot");
        }
        
        
        
        /**
         * Lukee kerhon tiedot tiedostosta
         * @param nimi jota käyteään lukemisessa
         * @throws SailoException jos lukeminen epäonnistuu
         * 
         * @example
         * <pre name="test">
         * #THROWS SailoException 
         * #import java.io.*;
         * #import java.util.*;
         * 
         *  Kerho kerho = new Kerho();
         *  
         *  Jasen aku1 = new Jasen(); aku1.vastaaAkuAnkka(); aku1.rekisteroi();
         *  Jasen aku2 = new Jasen(); aku2.vastaaAkuAnkka(); aku2.rekisteroi();
         *  Harrastus pitsi21 = new Harrastus(); pitsi21.vastaaPitsinNyplays(aku2.getTunnusNro());
         *  Harrastus pitsi11 = new Harrastus(); pitsi11.vastaaPitsinNyplays(aku1.getTunnusNro());
         *  Harrastus pitsi22 = new Harrastus(); pitsi22.vastaaPitsinNyplays(aku2.getTunnusNro()); 
         *  Harrastus pitsi12 = new Harrastus(); pitsi12.vastaaPitsinNyplays(aku1.getTunnusNro()); 
         *  Harrastus pitsi23 = new Harrastus(); pitsi23.vastaaPitsinNyplays(aku2.getTunnusNro());
         *   
         *  String hakemisto = "testikelmit";
         *  File dir = new File(hakemisto);
         *  File ftied  = new File(hakemisto+"/nimet.dat");
         *  File fhtied = new File(hakemisto+"/harrastukset.dat");
         *  dir.mkdir();  
         *  ftied.delete();
         *  fhtied.delete();
         *  kerho.lueTiedostosta(hakemisto); #THROWS SailoException
         *  kerho.lisaa(aku1);
         *  kerho.lisaa(aku2);
         *  kerho.lisaa(pitsi21);
         *  kerho.lisaa(pitsi11);
         *  kerho.lisaa(pitsi22);
         *  kerho.lisaa(pitsi12);
         *  kerho.lisaa(pitsi23);
         *  kerho.tallenna();
         *  kerho = new Kerho();
         *  kerho.lueTiedostosta(hakemisto);
         *  Collection<Jasen> kaikki = kerho.etsi("",-1); 
         *  Iterator<Jasen> it = kaikki.iterator();
         *  it.next() === aku1;
         *  it.next() === aku2;
         *  it.hasNext() === false;
         *  List<Harrastus> loytyneet = kerho.annaHarrastukset(aku1);
         *  Iterator<Harrastus> ih = loytyneet.iterator();
         *  ih.next() === pitsi11;
         *  ih.next() === pitsi12;
         *  ih.hasNext() === false;
         *  loytyneet = kerho.annaHarrastukset(aku2);
         *  ih = loytyneet.iterator();
         *  ih.next() === pitsi21;
         *  ih.next() === pitsi22;
         *  ih.next() === pitsi23;
         *  ih.hasNext() === false;
         *  kerho.lisaa(aku2);
         *  kerho.lisaa(pitsi23);
         *  kerho.tallenna();
         *  ftied.delete()  === true;
         *  fhtied.delete() === true;
         *  File fbak = new File(hakemisto+"/nimet.bak");
         *  File fhbak = new File(hakemisto+"/harrastukset.bak");
         *  fbak.delete() === true;
         *  fhbak.delete() === true;
         *  dir.delete() === true;
         * </pre>
         */
        public void lueTiedostosta(String nimi) throws StoreException {
            stocks = new Osakkeet(); // jos luetaan olemassa olevaan niin helpoin tyhjentää näin
            transactions = new Transaktiot();

            setTiedosto(nimi);
            stocks.readFromFile();
            transactions.readFromFile();
        }


        /**
         * Tallenttaa kerhon tiedot tiedostoon.  
         * Vaikka jäsenten tallettamien epäonistuisi, niin yritetään silti tallettaa
         * harrastuksia ennen poikkeuksen heittämistä.
         * @throws SailoException jos tallettamisessa ongelmia
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
                stock1.giveStock();
                stock2.register();
                stock2.giveStock();
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


		private void add(Transaktio transaktio1) {
			transactions.add(transaktio1);
			
		}


		private List<Transaktio> giveTransactions(Osake osake) {
			return transactions.giveTransactions(osake.getId());
		}

}
