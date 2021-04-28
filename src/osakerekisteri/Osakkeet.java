package osakerekisteri;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import fi.jyu.mit.ohj2.WildChars;

/**
 * |------------------------------------------------------------------------|
 * | Luokan nimi: osakkeet                              | Avustajat:        |
 * |-------------------------------------------------------------------------
 * | Vastuualueet:                                      | - Osake           |
 * |                                                    |                   |
 * | - ei tiedä Osakerekisteristä, Osakkeesta eikä      |                   |
 * |   käyttöliittymästä                                |                   |
 * | - osaa tallentaa tiedostoon                        |                   |
 * | - osaa searchä ja lajitella                          |                   |
 * | - osaa lisätä ja poistaa osakkeita                 |                   |
 * |                                                    |                   |
 * |-------------------------------------------------------------------------
 * @author Jesse Korolainen & Teemu Nieminen
 * @version 1.3.2021
 *
 */
public class Osakkeet implements Iterable<Osake> { 
    private static final int MAX_STOCKS     = 5;
    private int              amount         = 0;
    private String           fileName       = "";
    private Osake            entries[]      = new Osake[MAX_STOCKS];
    private boolean changed 				= false;
    private String fileBasicName 			= "stocks";
    private int lkm							= 0;
    
    /**
     * Oletusmuodostaja
     */
    public Osakkeet() {
        // Attribuuttien oma alustus riittää
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
     * Lisää uuden osakkeen tietorakenteeseen. Ottaa osakkeen omistukseensa.
     * @param stock lisättävän osakkeen viite.
     * @throws StoreException jos tietorakenne on täynnä
     * @example
     * <pre name="test">
     * #THROWS StoreException 
     * Osakkeet stocks = new Osakkeet();
     * Osake stock1 = new Osake(), stock2 = new Osake();
     * stocks.getAmount() === 0;
     * stocks.add(stock1); stocks.getAmount() === 1;
     * stocks.add(stock2); stocks.getAmount() === 2;
     * stocks.add(stock1); stocks.getAmount() === 3;
     * stocks.give(0) === stock1;
     * stocks.give(1) === stock2;
     * stocks.give(2) === stock1;
     * stocks.give(1) == stock1 === false;
     * stocks.give(1) == stock2 === true;
     * stocks.give(3) === stock1; #THROWS IndexOutOfBoundsException 
     * stocks.add(stock1); stocks.getAmount() === 4;
     * stocks.add(stock1); stocks.getAmount() === 5;
     * stocks.add(stock1);
     * </pre>
     */
    public void add(Osake stock) throws StoreException {
        if (amount >= entries.length) entries = Arrays.copyOf(entries, amount+20);
        entries[amount] = stock;
        amount++;
        changed = true;
    }
    
    

    /**
     * Palauttaa viitteen i:teen osakkeeseen.
     * @param i monennenko osakkeen viite halutaan
     * @return viite osakkeeseen, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella  
     */
    public Osake give(int i) throws IndexOutOfBoundsException {
        if (i < 0 || amount <= i)
            throw new IndexOutOfBoundsException("Forbidden index: " + i);
        return entries[i];
    }
    
   
    
    /**
     * Palauttaa osakerekisterin osakkeiden lukumäärän
     * @return osakkeiden lukumäärä
     */
    public int getAmount() {
        return amount;
    }
    
    /**
     * Lukee osakkeet tiedostosta.
     * @param file tiedoston perusnimi
     * @throws StoreException jos lukeminen epäonnistuu
     * 
     * @example
     * <pre name="test">
     * #THROWS StoreException 
     * #import java.io.File;
     * 
     *  Osakeet stocks = new Osakeet();
     *  Osake stock1 = new Osake(), stock2 = new Osake();
     *  stock1.giveStock();
     *  stock2.giveStock();
     *  String directory = "teststocks";
     *  String fileName = directory+"/stocks";
     *  File ftied = new File(fileName+".dat");
     *  File dir = new File(directory);
     *  dir.mkdir();
     *  ftied.delete();
     *  stocks.readFromFile(fileName); #THROWS SailoException
     *  stocks.add(stock1);
     *  stocks.add(stock2);
     *  stocks.save();
     *  stocks = new Osakeet();            // Poistetaan vanhat luomalla uusi
     *  stocks.readFromFile(fileName);  // johon ladataan tiedot tiedostosta.
     *  Iterator<Osake> i = stocks.iterator();
     *  i.next() === stock1;
     *  i.next() === stock2;
     *  i.hasNext() === false;
     *  stocks.add(stock2);
     *  stocks.save();
     *  ftied.delete() === true;
     *  File fbak = new File(fileName+".bak");
     *  fbak.delete() === true;
     *  dir.delete() === true;
     * </pre>
     */
    public void readFromFile(String file) throws StoreException {
        setFileBasicName(file);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getFileName()))) {
            fileName = fi.readLine();
            if ( fileName == null ) throw new StoreException("Osakerekisterin nimi puuttuu");
            String line = fi.readLine();
            if ( line == null ) throw new StoreException("Maksimikoko puuttuu");
            // int maxKoko = Mjonot.erotaInt(rivi,10); // tehdään jotakin

            while ( (line = fi.readLine()) != null ) {
            	line = line.trim();
                if ( "".equals(line) || line.charAt(0) == ';' ) continue;
                Osake stock = new Osake();
                stock.parse(line); // voisi olla virhekäsittely
                add(stock);
            }
            changed = false;
        } catch ( FileNotFoundException e ) {
            throw new StoreException("File " + getFileName() + " does not open");
        } catch ( IOException e ) {
            throw new StoreException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }
    
  
    /**
     * Asettaa tiedoston perusnimen ilman tarkenninta
     * @param file tallennustiedoston perusnimi
     */

    public void setFileBasicName(String file) {
		fileBasicName = file;
	}
    
    
    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    
    public String getFileBasicName() {
    	return fileBasicName;
    }
    
    /**
    * Palauttaa tiedoston nimen, jota käytetään tallennukseen
    * @return tallennustiedoston nimi
    */
   public String getFileName() {
       return getFileBasicName() + ".dat";
   }


   /**
    * Palauttaa varakopiotiedoston nimen
    * @return varakopiotiedoston nimi
    */
   public String getBakName() {
       return fileBasicName + ".bak";
   }

	/**
     * Luetaan aikaisemmin annetun nimisestä tiedostosta
     * @throws StoreException jos tulee poikkeus
     */
    public void readFromFile() throws StoreException {
        readFromFile(getFileBasicName());
    }
    
    
    /**
     * Tallentaa osakkeen tiedostoon.
     * <pre>
     * Osakerekisteri
     * 20
     * ; kommenttirivi
     * 1|Nordea Oyj|1243|1231.0|124.0|
     * 5|Olvi Oyj|22|49.0|1000.0|
     * </pre>
     * @throws StoreException jos talletus epäonnistuu
     */
    public void save() throws StoreException {
        if ( !changed ) return;

        File fbak = new File(getBakName());
        File ftied = new File(getFileName());
        fbak.delete(); // if .. System.err.println("Ei voi tuhota");
        ftied.renameTo(fbak); // if .. System.err.println("Ei voi nimetä");

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            fo.println(getWholeFileName());
            fo.println(entries.length);
            for (Osake stock : this) {
                fo.println(stock.toString());
            }
            //} catch ( IOException e ) { // ei heitä poikkeusta
            //  throw new SailoException("Tallettamisessa ongelmia: " + e.getMessage());
        } catch ( FileNotFoundException ex ) {
            throw new StoreException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new StoreException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }

        changed = false;
    }
    
    /**
     * Palauttaa rekisterin koko nimen
     * @return osakerekisterin koko nimen merkkijonona
     */
    
    public String getWholeFileName() {
    	return fileName;
    }
    

    /**
     * Testiohjelma osakkeille
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Osakkeet stocks = new Osakkeet();

        Osake stock1 = new Osake(), stock2 = new Osake();
        stock1.register();
         //stock1.giveStock(); --> stock1.give(); ??? TODO
        stock2.register();
        //stock2.giveStock();


        try {
            stocks.add(stock1);
            stocks.add(stock2);
            

            System.out.println("============= Osakkeet test =================");
            
            // save tässä tiedosto
            stocks.save();
            stocks = new Osakkeet();
            // lueppa tiedosto
            stocks.readFromFile();

            for (int i = 0; i < stocks.getAmount(); i++) {
                Osake stock = stocks.give(i);
                System.out.println("Stock ID: " + i);
                stock.print(System.out);
            }

        } catch (StoreException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
	
	/**
     * Luokka osakkeiden iteroimiseksi.
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * Osakeet stocks = new Osakeet();
     * Osake stock1 = new Osake(), stock2 = new Osake();
     * stock1.rekisteroi(); stock2.rekisteroi();
     *
     * stocks.add(stock1); 
     * stocks.add(stock2); 
     * stocks.add(stock1); 
     * 
     * StringBuffer ids = new StringBuffer(30);
     * for (Osake stock:stocks)   // Kokeillaan for-silmukan toimintaa
     *   ids.append(" "+stock.getTunnusNro());           
     * 
     * String tulos = " " + stock1.getTunnusNro() + " " + stock2.getTunnusNro() + " " + stock1.getTunnusNro();
     * 
     * ids.toString() === tulos; 
     * 
     * ids = new StringBuffer(30);
     * for (Iterator<Osake>  i=stocks.iterator(); i.hasNext(); ) { // ja iteraattorin toimintaa
     *   Osake stock = i.next();
     *   ids.append(" "+stock.getTunnusNro());           
     * }
     * 
     * ids.toString() === tulos;
     * 
     * Iterator<Osake>  i=stocks.iterator();
     * i.next() == stock1  === true;
     * i.next() == stock2  === true;
     * i.next() == stock1  === true;
     * 
     * i.next();  #THROWS NoSuchElementException
     *  
     * </pre>
     */
    public class OsakkeetIterator implements Iterator<Osake> {
        private int place = 0;

		@Override
		public boolean hasNext() {
			return place < getAmount();
		}

		/**
         * Annetaan seuraava osake
         * @return seuraava osake
         * @throws NoSuchElementException jos seuraava osaketta ei enää ole
         * @see java.util.Iterator#next()
         */
        @Override
        public Osake next() throws NoSuchElementException {
            if ( !hasNext() ) throw new NoSuchElementException("Ei oo");
            return give(place++);
        }
        
        /**
         * Tuhoamista ei ole toteutettu
         * @throws UnsupportedOperationException aina
         * @see java.util.Iterator#remove()
         */
        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Me ei poisteta");
        }
    }


    /**
     * Palautetaan iteraattori osakkeista
     * @return Osakeiteraattori
     */
    @Override
    public Iterator<Osake> iterator() {
        return new OsakkeetIterator();
    }
    
    /**
     * @param hakuehto käyttäjän syöttämä hakuehto
     * @param k searchttävän kentän indeksi  
     * @return tietorakenteen löytyneistä osakkeista
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     * Osake stock4 = new Osake(); stock4.parse("1|Nokia Oyj|200|3.12|624.00|"); 
     * Osake stock5 = new Osake(); stock5.parse("2|Olvi Oyj|80|35.00|2800.00|"); 
     * stocks.add(stock1); stocks.add(stock2); stocks.add(stock3); stocks.add(stock4); stocks.add(stock5)
     * List<Osake> loytyneet;  
     * loytyneet = (List<Osake>)stocks.search("*s*",1);  
     * loytyneet.size() === 2;  
     * loytyneet.get(0) == stock3 === true;  
     * loytyneet.get(1) == stock4 === true;      
     * loytyneet = (List<Osake>)stocks.search("*7-*",2);  
     * loytyneet.size() === 2;  
     * loytyneet.get(0) == stock3 === true;  
     * loytyneet.get(1) == stock5 === true;    
     * loytyneet = (List<Osake>)stocks.search(null,-1);  
     * loytyneet.size() === 5;  
     * </pre> 
     */ 
    
        public Collection<Osake> search(String hakuehto, int k) { 
            String ehto = "*"; 
            if ( hakuehto != null && hakuehto.length() > 0 ) ehto = hakuehto + "*"; 
            int hk = k; 
            if ( hk < 0 ) hk = 1;
            List<Osake> loytyneet = new ArrayList<Osake>(); 
            for (Osake stock : this) { 
                if (WildChars.onkoSamat(stock.giveStock(hk), ehto)) loytyneet.add(stock);   
            } 
            Collections.sort(loytyneet, new Osake.Compare(hk)); 
            return loytyneet; 
        }


	public int delete(int id) {
		int ind = searchId(id); 
        if (ind < 0) return 0; 
        lkm--; 
        for (int i = ind; i < lkm; i++) 
            entries[i] = entries[i + 1]; 
        entries[lkm] = null; 
        changed = true; 
        return 1; 
    }

	
	/** 
     * Etsii jäsenen id:n perusteella 
     * @param id tunnusnumero, jonka mukaan etsitään 
     * @return löytyneen jäsenen indeksi tai -1 jos ei löydy 
     * <pre name="test"> 
     * #THROWS SailoException  
     * Jasenet jasenet = new Jasenet(); 
     * Jasen aku1 = new Jasen(), aku2 = new Jasen(), aku3 = new Jasen(); 
     * aku1.rekisteroi(); aku2.rekisteroi(); aku3.rekisteroi(); 
     * int id1 = aku1.getTunnusNro(); 
     * jasenet.lisaa(aku1); jasenet.lisaa(aku2); jasenet.lisaa(aku3); 
     * jasenet.etsiId(id1+1) === 1; 
     * jasenet.etsiId(id1+2) === 2; 
     * </pre> 
     */ 

	private int searchId(int id) {
		for (int i = 0; i < lkm; i++) 
            if (id == entries[i].getId()) return i; 
        return -1; 
    } 
	
	/** TÄLLÄ HETKELLÄ TÄTÄ EI TAIDETA KUTSUA MISSÄÄN
     * Etsii jäsenen id:n perusteella 
     * @param id tunnusnumero, jonka mukaan etsitään 
     * @return jäsen jolla etsittävä id tai null 
     * <pre name="test"> 
     * #THROWS SailoException  
     * Jasenet jasenet = new Jasenet(); 
     * Jasen aku1 = new Jasen(), aku2 = new Jasen(), aku3 = new Jasen(); 
     * aku1.rekisteroi(); aku2.rekisteroi(); aku3.rekisteroi(); 
     * int id1 = aku1.getTunnusNro(); 
     * jasenet.lisaa(aku1); jasenet.lisaa(aku2); jasenet.lisaa(aku3); 
     * jasenet.annaId(id1  ) == aku1 === true; 
     * jasenet.annaId(id1+1) == aku2 === true; 
     * jasenet.annaId(id1+2) == aku3 === true; 
     * </pre> 
     */ 
    public Osake giveId(int id) { 
        for (Osake stock : this) { 
            if (id == stock.getId()) return stock; 
        } 
        return null; 
    } 



}
