package osakerekisteri;

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

    private String                      tiedostonNimi = "";

    /** Taulukko transaktioista */
    private final Collection<Transaktio> alkiot        = new ArrayList<Transaktio>();


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
        alkiot.add(trans);
    }


    /**
     * Lukee transaktiot tiedostosta.  
     * TODO Kesken.
     * @param hakemisto tiedoston hakemisto
     * @throws StoreException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws StoreException {
        tiedostonNimi = hakemisto + ".har";
        throw new StoreException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
    }


    /**
     * Tallentaa transaktiot tiedostoon.  
     * TODO Kesken.
     * @throws StoreException jos talletus epäonnistuu
     */
    public void talleta() throws StoreException {
        throw new StoreException("Ei osata vielä tallettaa tiedostoa " + tiedostonNimi);
    }


    /**
     * Palauttaa osakerekisterin transaktioiden lukumäärän
     * @return transaktioiden lukumäärä
     */
    public int getLkm() {
        return alkiot.size();
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
        return alkiot.iterator();
    }


    /**
     * Haetaan kaikki jäsen harrastukset
     * @param tunnusnro jäsenen tunnusnumero jolle harrastuksia haetaan
     * @return tietorakenne jossa viiteet löydetteyihin harrastuksiin
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     *  Harrastukset transaktiot = new Harrastukset();
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

    public List<Transaktio> giveTransactions(int tunnusnro) {
        List<Transaktio> loydetyt = new ArrayList<Transaktio>();
        for (Transaktio trans : alkiot)
            if (trans.getStockId() == tunnusnro) loydetyt.add(trans);
        return loydetyt;
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