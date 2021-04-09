package osakerekisteri;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Osakerekisterin transaktiot, joka osaa mm. lisätä uuden transaktion.
 * 
 * |------------------------------------------------------------------------|
 * | Luokan nimi: transaktiot                           | Avustajat:        |
 * |-------------------------------------------------------------------------
 * | Vastuualueet:                                      | - Transaktio      |
 * |                                                    |                   |
 * | - ei tiedä Osakerekisteristä, Transaktiosta eikä   |                   |
 * |   käyttöliittymästä                                |                   |
 * | - osaa tallentaa tiedostoon                        |                   |
 * | - osaa etsiä ja lajitella                          |                   |
 * | - osaa lisätä ja poistaa transaktioita             |                   |
 * |                                                    |                   |
 * |-------------------------------------------------------------------------
 *
 * @author Jesse Korolainen & Teemu Nieminen
 * @version 1.0, 6.4.2021
 */
public class Transaktiot implements Iterable<Transaktio> {

    private String                      fileBasicName = "transactions";
    private boolean                     changed = false;

    /** Taulukko transaktioista */
    private final Collection<Transaktio> entries        = new ArrayList<Transaktio>();


    /**
     * Transaktioiden alustaminen
     */
    public Transaktiot() {
        // toistaiseksi ei tarvitse tehdä mitään
    }


    /**
     * Lisää uuden transaktion tietorakenteeseen.  Ottaa transaktion omistukseensa.
     * @param trans lisättävä transaktio.  Huom tietorakenne muuttuu omistajaksi
     */

    public void add(Transaktio trans) {
        entries.add(trans);
        changed = true;
    }


    /**
     * Lukee transaktiot tiedostosta.
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
            String line;
            while ( (line = fi.readLine()) != null ) {
                line = line.trim();
                if ( "".equals(line) || line.charAt(0) == ';' ) continue;
                Transaktio transaction = new Transaktio();
                transaction.parse(line); // voisi olla virhekäsittely
                add(transaction);
            }
            changed = false;
        } catch ( FileNotFoundException e ) {
            throw new StoreException("File " + getFileName() + " does not open");
        } catch ( IOException e ) {
            throw new StoreException("Problems with the file: " + e.getMessage());
        }
    }

    /**
     * Luetaan aikaisemmin annetun nimisestä tiedostosta
     * @throws StoreException jos tulee poikkeus
     */
    public void readFromFile() throws StoreException {
        readFromFile(getFileBasicName());
    }
    
    
    /**
     * Tallentaa transaktion tiedostoon.
     * <pre>
     * Osakerekisteri
     * 20
     * ; kommenttirivi
     * 1|1|30.11.2007|Osto|27.32|200|54.64|5518.64|
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
            for (Transaktio transaction : this) {
                fo.println(transaction.toString());
            }

        } catch ( FileNotFoundException ex ) {
            throw new StoreException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new StoreException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }

        changed = false;
    }


    /**
     * Palauttaa osakerekisterin transaktioiden lukumäärän
     * @return transaktioiden lukumäärä
     */
    public int getAmount() {
        return entries.size();
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
     * Iteraattori kaikkien transaktioiden läpikäymiseen
     * @return transaktioiteraattori
     * 
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     *  Transaktiot transaktio = new Transaktiot();
     *  Transaktio trans21 = new Transaktio(2); transaktiot.lisaa(trans21);
     *  Transaktio trans11 = new Transaktio(1); transaktiot.lisaa(trans11);
     *  Transaktio trans22 = new Transaktio(2); transaktiot.lisaa(trans22);
     *  Transaktio trans12 = new Transaktio(1); transaktiot.lisaa(trans12);
     *  Transaktio trans23 = new Transaktio(2); transaktiot.lisaa(trans23);
     * 
     *  Iterator<Transaktio> i2=transaktiot.iterator();
     *  i2.next() === trans21;
     *  i2.next() === trans11;
     *  i2.next() === trans22;
     *  i2.next() === trans12;
     *  i2.next() === trans23;
     *  i2.next() === trans12;  #THROWS NoSuchElementException  
     *  
     *  int n = 0;
     *  int jnrot[] = {2,1,2,1,2};
     *  
     *  for ( Transaktio har:transaktiot ) { 
     *    har.getJasenNro() === jnrot[n]; n++;  
     *  }
     *  
     *  n === 5;
     *  
     * </pre>
     */
    @Override
    public Iterator<Transaktio> iterator() {
        return entries.iterator();
    }


    /**
     * Haetaan kaikki osakkeen transaktiot
     * @param stockId osakkeen tunnusnumero jolle transaktioita haetaan
     * @return tietorakenne jossa viiteet löydetteyihin harrastuksiin
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     *  Transaktiot transaktiot = new Transaktiot();
     *  Transaktio trans21 = new Transaktio(2); transaktiot.lisaa(trans21);
     *  Transaktio trans11 = new Transaktio(1); transaktiot.lisaa(trans11);
     *  Transaktio trans22 = new Transaktio(2); transaktiot.lisaa(trans22);
     *  Transaktio trans12 = new Transaktio(1); transaktiot.lisaa(trans12);
     *  Transaktio trans23 = new Transaktio(2); transaktiot.lisaa(trans23);
     *  Transaktio trans51 = new Transaktio(5); transaktiot.lisaa(trans51);
     *  
     *  List<Transaktio> loytyneet;
     *  loytyneet = transaktiot.annaTransaktiot(3);
     *  loytyneet.size() === 0; 
     *  loytyneet = transaktiot.annaTransaktiot(1);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(0) == trans11 === true;
     *  loytyneet.get(1) == trans12 === true;
     *  loytyneet = transaktiot.annaTransaktiot(5);
     *  loytyneet.size() === 1; 
     *  loytyneet.get(0) == trans51 === true;
     * </pre> 
     */

    public List<Transaktio> giveTransactions(int stockId) {
        List<Transaktio> found = new ArrayList<Transaktio>();
        for (Transaktio trans : entries)
            if (trans.getStockId() == stockId) found.add(trans);
        return found;
    }


    /**
     * Testiohjelma harrastuksille
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        
        Transaktiot transaktiot = new Transaktiot();
        Transaktio trans1 = new Transaktio();
        trans1.testi(2);
        Transaktio trans2 = new Transaktio();
        trans2.testi(1);
        Transaktio trans3 = new Transaktio();
        trans3.testi(2);
        Transaktio trans4 = new Transaktio();
        trans4.testi(2);

        transaktiot.add(trans1);
        transaktiot.add(trans2);
        transaktiot.add(trans3);
        transaktiot.add(trans2);
        transaktiot.add(trans4);

        System.out.println("============= Transaktiot testi =================");

        List<Transaktio> transaktiot2 = transaktiot.giveTransactions(2);


        for (Transaktio trans : transaktiot2) {
            System.out.print(trans.getStockId() + " ");
            trans.tulosta(System.out);
        }

    }

}