package osakerekisteri;

/**
 * @author Jesse Korolainen & Teemu Nieminen
 * @version 1.3.2021
 *
 */
public class Osakerekisteri {
        private final Osakkeet stocks = new Osakkeet();
        private String omistaja;


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
        
        public String setOmistaja(String omistaja) {
        	this.omistaja = omistaja;
        	return omistaja;
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

                osakerekisteri.add(stock1);
                osakerekisteri.add(stock2);

                System.out.println("============= osakerekisterin testi =================");

                for (int i = 0; i < osakerekisteri.getStocks(); i++) {
                    Osake Osake = osakerekisteri.giveStock(i);
                    System.out.println("Stock at: " + i);
                    Osake.print(System.out);
                }

            } catch (StoreException ex) {
                System.out.println(ex.getMessage());
            }
        }

}
