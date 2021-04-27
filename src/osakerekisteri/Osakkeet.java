package osakerekisteri;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
 * | - osaa etsiä ja lajitella                          |                   |
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
    
    /**
     * Oletusmuodostaja
     */
    public Osakkeet() {
        // Attribuuttien oma alustus riittää
    }
    
    
    /**
     * Tehdään identtinen klooni jäsenestä
     * @return Object kloonattu jäsen
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Jasen jasen = new Jasen();
     *   jasen.parse("   3  |  Ankka Aku   | 123");
     *   Jasen kopio = jasen.clone();
     *   kopio.toString() === jasen.toString();
     *   jasen.parse("   4  |  Ankka Tupu   | 123");
     *   kopio.toString().equals(jasen.toString()) === false;
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
     * stocks.add(stock1);  #THROWS StoreException
     * </pre>
     */
    public void add(Osake stock) throws StoreException {
        if (amount >= entries.length) throw new StoreException("Too many entries");
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
     * #THROWS SailoException 
     * #import java.io.File;
     * 
     *  Jasenet jasenet = new Jasenet();
     *  Jasen aku1 = new Jasen(), aku2 = new Jasen();
     *  aku1.vastaaAkuAnkka();
     *  aku2.vastaaAkuAnkka();
     *  String hakemisto = "testikelmit";
     *  String tiedNimi = hakemisto+"/nimet";
     *  File ftied = new File(tiedNimi+".dat");
     *  File dir = new File(hakemisto);
     *  dir.mkdir();
     *  ftied.delete();
     *  jasenet.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  jasenet.lisaa(aku1);
     *  jasenet.lisaa(aku2);
     *  jasenet.tallenna();
     *  jasenet = new Jasenet();            // Poistetaan vanhat luomalla uusi
     *  jasenet.lueTiedostosta(tiedNimi);  // johon ladataan tiedot tiedostosta.
     *  Iterator<Jasen> i = jasenet.iterator();
     *  i.next() === aku1;
     *  i.next() === aku2;
     *  i.hasNext() === false;
     *  jasenet.lisaa(aku2);
     *  jasenet.tallenna();
     *  ftied.delete() === true;
     *  File fbak = new File(tiedNimi+".bak");
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
     * Kelmien kerho
     * 20
     * ; kommenttirivi
     * 2|Ankka Aku|121103-706Y|Paratiisitie 13|12345|ANKKALINNA|12-1234|||1996|50.0|30.0|Velkaa Roopelle
     * 3|Ankka Tupu|121153-706Y|Paratiisitie 13|12345|ANKKALINNA|12-1234|||1996|50.0|30.0|Velkaa Roopelle
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
        stock1.giveStock();
        stock2.register();
        stock2.giveStock();


        try {
            stocks.add(stock1);
            stocks.add(stock2);
            

            System.out.println("============= Osakkeet test =================");
            
            // tallenna tässä tiedosto
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
     * Luokka jäsenten iteroimiseksi.
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * Jasenet jasenet = new Jasenet();
     * Jasen aku1 = new Jasen(), aku2 = new Jasen();
     * aku1.rekisteroi(); aku2.rekisteroi();
     *
     * jasenet.lisaa(aku1); 
     * jasenet.lisaa(aku2); 
     * jasenet.lisaa(aku1); 
     * 
     * StringBuffer ids = new StringBuffer(30);
     * for (Jasen jasen:jasenet)   // Kokeillaan for-silmukan toimintaa
     *   ids.append(" "+jasen.getTunnusNro());           
     * 
     * String tulos = " " + aku1.getTunnusNro() + " " + aku2.getTunnusNro() + " " + aku1.getTunnusNro();
     * 
     * ids.toString() === tulos; 
     * 
     * ids = new StringBuffer(30);
     * for (Iterator<Jasen>  i=jasenet.iterator(); i.hasNext(); ) { // ja iteraattorin toimintaa
     *   Jasen jasen = i.next();
     *   ids.append(" "+jasen.getTunnusNro());           
     * }
     * 
     * ids.toString() === tulos;
     * 
     * Iterator<Jasen>  i=jasenet.iterator();
     * i.next() == aku1  === true;
     * i.next() == aku2  === true;
     * i.next() == aku1  === true;
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
         * Annetaan seuraava jäsen
         * @return seuraava jäsen
         * @throws NoSuchElementException jos seuraava alkiota ei enää ole
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
     * @param k etsittävän kentän indeksi  
     * @return tietorakenteen löytyneistä osakkeista
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     * Jasen jasen4 = new Jasen(); jasen4.parse("4|Ankka Iines|030245-115V|Ankkakuja 9"); 
     * Jasen jasen5 = new Jasen(); jasen5.parse("5|Ankka Roope|091007-408U|Ankkakuja 12"); 
     * jasenet.lisaa(jasen1); jasenet.lisaa(jasen2); jasenet.lisaa(jasen3); jasenet.lisaa(jasen4); jasenet.lisaa(jasen5)
     * List<Jasen> loytyneet;  
     * loytyneet = (List<Jasen>)jasenet.etsi("*s*",1);  
     * loytyneet.size() === 2;  
     * loytyneet.get(0) == jasen3 === true;  
     * loytyneet.get(1) == jasen4 === true;      
     * loytyneet = (List<Jasen>)jasenet.etsi("*7-*",2);  
     * loytyneet.size() === 2;  
     * loytyneet.get(0) == jasen3 === true;  
     * loytyneet.get(1) == jasen5 === true;    
     * loytyneet = (List<Jasen>)jasenet.etsi(null,-1);  
     * loytyneet.size() === 5;  
     * </pre> 
     */ 
    
        public Collection<Osake> search(String hakuehto, int k) { 
            String ehto = "*"; 
            if ( hakuehto != null && hakuehto.length() > 0 ) ehto = hakuehto; 
            int hk = k; 
            if ( hk < 0 ) hk = 1;
            Collection<Osake> loytyneet = new ArrayList<Osake>(); 
            for (Osake stock : this) { 
                if (WildChars.onkoSamat(stock.giveStock(hk), ehto)) loytyneet.add(stock);   
            } 
            //  TODO: lajittelua varten vertailija  
            return loytyneet; 
        }


}
