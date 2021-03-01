package osakerekisteri;

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
public class Osakkeet {
    private static final int MAX_STOCKS     = 5;
    private int              amount         = 0;
    private String           fileName       = "";
    private Osake            entries[]      = new Osake[MAX_STOCKS];
    
    /**
     * Oletusmuodostaja
     */
    public Osakkeet() {
        // Attribuuttien oma alustus riittää
    }
    
    /**
     * Lisää uuden osakkeen tietorakenteeseen. Ottaa osakkeen omistukseensa.
     * @param stock lisättävän osakkeen viite.
     * @throws StoreException jos tietorakenne on täynnä
     */
    public void add(Osake stock) throws StoreException {
        if (amount >= entries.length) throw new StoreException("Too many entries");
        entries[amount] = stock;
        amount++;
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
     * Lukee jäsenistön tiedostosta.  Kesken.
     * @param directory tiedoston hakemisto
     * @throws StoreException jos lukeminen epäonnistuu
     */
    public void readFromFile(String directory) throws StoreException {
        fileName = directory + "/nimet.dat";
        throw new StoreException("Ei osata vielä lukea tiedostoa " + fileName);
    }
    
    /**
     * Tallentaa osakkeen tiedostoon.  Kesken.
     * @throws StoreException jos talletus epäonnistuu
     */
    public void save() throws StoreException {
        throw new StoreException("Cannot save file yet " + fileName);
    }
    
    /**
     * Palauttaa osakerekisterin osakkeiden lukumäärän
     * @return osakkeiden lukumäärä
     */
    public int getAmount() {
        return amount;
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

            for (int i = 0; i < stocks.getAmount(); i++) {
                Osake stock = stocks.give(i);
                System.out.println("Stock ID: " + i);
                stock.print(System.out);
            }

        } catch (StoreException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
