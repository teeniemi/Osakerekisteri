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

    /** Taulukko harrastuksista */
    private final Collection<Transaktio> alkiot        = new ArrayList<Transaktio>();


    /**
     * Harrastusten alustaminen
     */
    public Transaktiot() {
        // toistaiseksi ei tarvitse tehdä mitään
    }


    /**
     * Lisää uuden harrastuksen tietorakenteeseen.  Ottaa harrastuksen omistukseensa.
     * @param har lisättävä harrastus.  Huom tietorakenne muuttuu omistajaksi
     */
    public void lisaa(Transaktio har) {
        alkiot.add(har);
    }


    /**
     * Lukee jäsenistön tiedostosta.  
     * TODO Kesken.
     * @param hakemisto tiedoston hakemisto
     * @throws StoreException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws StoreException {
        tiedostonNimi = hakemisto + ".har";
        throw new StoreException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
    }


    /**
     * Tallentaa jäsenistön tiedostoon.  
     * TODO Kesken.
     * @throws StoreException jos talletus epäonnistuu
     */
    public void talleta() throws StoreException {
        throw new StoreException("Ei osata vielä tallettaa tiedostoa " + tiedostonNimi);
    }


    /**
     * Palauttaa kerhon harrastusten lukumäärän
     * @return harrastusten lukumäärä
     */
    public int getLkm() {
        return alkiot.size();
    }


    /**
     * Iteraattori kaikkien harrastusten läpikäymiseen
     * @return harrastusiteraattori
     * 
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     *  Harrastukset harrasteet = new Harrastukset();
     *  Harrastus pitsi21 = new Harrastus(2); harrasteet.lisaa(pitsi21);
     *  Harrastus pitsi11 = new Harrastus(1); harrasteet.lisaa(pitsi11);
     *  Harrastus pitsi22 = new Harrastus(2); harrasteet.lisaa(pitsi22);
     *  Harrastus pitsi12 = new Harrastus(1); harrasteet.lisaa(pitsi12);
     *  Harrastus pitsi23 = new Harrastus(2); harrasteet.lisaa(pitsi23);
     * 
     *  Iterator<Harrastus> i2=harrasteet.iterator();
     *  i2.next() === pitsi21;
     *  i2.next() === pitsi11;
     *  i2.next() === pitsi22;
     *  i2.next() === pitsi12;
     *  i2.next() === pitsi23;
     *  i2.next() === pitsi12;  #THROWS NoSuchElementException  
     *  
     *  int n = 0;
     *  int jnrot[] = {2,1,2,1,2};
     *  
     *  for ( Harrastus har:harrasteet ) { 
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
     *  Harrastukset harrasteet = new Harrastukset();
     *  Harrastus pitsi21 = new Harrastus(2); harrasteet.lisaa(pitsi21);
     *  Harrastus pitsi11 = new Harrastus(1); harrasteet.lisaa(pitsi11);
     *  Harrastus pitsi22 = new Harrastus(2); harrasteet.lisaa(pitsi22);
     *  Harrastus pitsi12 = new Harrastus(1); harrasteet.lisaa(pitsi12);
     *  Harrastus pitsi23 = new Harrastus(2); harrasteet.lisaa(pitsi23);
     *  Harrastus pitsi51 = new Harrastus(5); harrasteet.lisaa(pitsi51);
     *  
     *  List<Harrastus> loytyneet;
     *  loytyneet = harrasteet.annaHarrastukset(3);
     *  loytyneet.size() === 0; 
     *  loytyneet = harrasteet.annaHarrastukset(1);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(0) == pitsi11 === true;
     *  loytyneet.get(1) == pitsi12 === true;
     *  loytyneet = harrasteet.annaHarrastukset(5);
     *  loytyneet.size() === 1; 
     *  loytyneet.get(0) == pitsi51 === true;
     * </pre> 
     */
    public List<Transaktio> annaHarrastukset(int tunnusnro) {
        List<Transaktio> loydetyt = new ArrayList<Transaktio>();
        for (Transaktio har : alkiot)
            if (har.getStockId() == tunnusnro) loydetyt.add(har);
        return loydetyt;
    }


    /**
     * Testiohjelma harrastuksille
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Transaktiot harrasteet = new Transaktiot();
        Transaktio pitsi1 = new Transaktio();
        pitsi1.testi(2);
        Transaktio pitsi2 = new Transaktio();
        pitsi2.testi(1);
        Transaktio pitsi3 = new Transaktio();
        pitsi3.testi(2);
        Transaktio pitsi4 = new Transaktio();
        pitsi4.testi(2);

        harrasteet.lisaa(pitsi1);
        harrasteet.lisaa(pitsi2);
        harrasteet.lisaa(pitsi3);
        harrasteet.lisaa(pitsi2);
        harrasteet.lisaa(pitsi4);

        System.out.println("============= Transaktiot testi =================");

        List<Transaktio> harrastukset2 = harrasteet.annaHarrastukset(2);

        for (Transaktio har : harrastukset2) {
            System.out.print(har.getStockId() + " ");
            har.tulosta(System.out);
        }

    }

}