package osakerekisteri.test;
// Generated by ComTest BEGIN
import java.io.File;
import osakerekisteri.*;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2021.04.27 19:05:58 // Generated by ComTest
 *
 */
@SuppressWarnings("all")
public class OsakkeetTest {



  // Generated by ComTest BEGIN
  /** 
   * testClone54 
   * @throws CloneNotSupportedException when error
   */
  @Test
  public void testClone54() throws CloneNotSupportedException {    // Osakkeet: 54
    Osake stock = new Osake(); 
    stock.parse("1|Nokia Oyj|200|3.12|624.00|"); 
    Osake kopio = stock.clone(); 
    assertEquals("From: Osakkeet line: 59", stock.toString(), kopio.toString()); 
    stock.parse("2|Olvi Oyj|20|20.00|400.00|"); 
    assertEquals("From: Osakkeet line: 61", false, kopio.toString().equals(stock.toString())); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testAdd76 
   * @throws StoreException when error
   */
  @Test
  public void testAdd76() throws StoreException {    // Osakkeet: 76
    Osakkeet stocks = new Osakkeet(); 
    Osake stock1 = new Osake(), stock2 = new Osake(); 
    assertEquals("From: Osakkeet line: 80", 0, stocks.getAmount()); 
    stocks.add(stock1); assertEquals("From: Osakkeet line: 81", 1, stocks.getAmount()); 
    stocks.add(stock2); assertEquals("From: Osakkeet line: 82", 2, stocks.getAmount()); 
    stocks.add(stock1); assertEquals("From: Osakkeet line: 83", 3, stocks.getAmount()); 
    assertEquals("From: Osakkeet line: 84", stock1, stocks.give(0)); 
    assertEquals("From: Osakkeet line: 85", stock2, stocks.give(1)); 
    assertEquals("From: Osakkeet line: 86", stock1, stocks.give(2)); 
    assertEquals("From: Osakkeet line: 87", false, stocks.give(1) == stock1); 
    assertEquals("From: Osakkeet line: 88", true, stocks.give(1) == stock2); 
    try {
    assertEquals("From: Osakkeet line: 89", stock1, stocks.give(3)); 
    fail("Osakkeet: 89 Did not throw IndexOutOfBoundsException");
    } catch(IndexOutOfBoundsException _e_){ _e_.getMessage(); }
    stocks.add(stock1); assertEquals("From: Osakkeet line: 90", 4, stocks.getAmount()); 
    stocks.add(stock1); assertEquals("From: Osakkeet line: 91", 5, stocks.getAmount()); 
    stocks.add(stock1); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testReadFromFile132 
   * @throws StoreException when error
   */
  @Test
  public void testReadFromFile132() throws StoreException {    // Osakkeet: 132
    Osakeet stocks = new Osakeet(); 
    Osake stock1 = new Osake(), stock2 = new Osake(); 
    stock1.giveStock(); 
    stock2.giveStock(); 
    String directory = "teststocks"; 
    String fileName = directory+"/stocks"; 
    File ftied = new File(fileName+".dat"); 
    File dir = new File(directory); 
    dir.mkdir(); 
    ftied.delete(); 
    try {
    stocks.readFromFile(fileName); 
    fail("Osakkeet: 146 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
    stocks.add(stock1); 
    stocks.add(stock2); 
    stocks.save(); 
    stocks = new Osakeet();  // Poistetaan vanhat luomalla uusi
    stocks.readFromFile(fileName);  // johon ladataan tiedot tiedostosta.
    Iterator<Osake> i = stocks.iterator(); 
    assertEquals("From: Osakkeet line: 153", stock1, i.next()); 
    assertEquals("From: Osakkeet line: 154", stock2, i.next()); 
    assertEquals("From: Osakkeet line: 155", false, i.hasNext()); 
    stocks.add(stock2); 
    stocks.save(); 
    assertEquals("From: Osakkeet line: 158", true, ftied.delete()); 
    File fbak = new File(fileName+".bak"); 
    assertEquals("From: Osakkeet line: 160", true, fbak.delete()); 
    assertEquals("From: Osakkeet line: 161", true, dir.delete()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testOsakkeetIterator322 
   * @throws SailoException when error
   */
  @Test
  public void testOsakkeetIterator322() throws SailoException {    // Osakkeet: 322
    Osakeet stocks = new Osakeet(); 
    Osake stock1 = new Osake(), stock2 = new Osake(); 
    stock1.rekisteroi(); stock2.rekisteroi(); 
    stocks.add(stock1); 
    stocks.add(stock2); 
    stocks.add(stock1); 
    StringBuffer ids = new StringBuffer(30); 
    for (Osake stock:stocks) // Kokeillaan for-silmukan toimintaa
    ids.append(" "+stock.getTunnusNro()); 
    String tulos = " " + stock1.getTunnusNro() + " " + stock2.getTunnusNro() + " " + stock1.getTunnusNro(); 
    assertEquals("From: Osakkeet line: 341", tulos, ids.toString()); 
    ids = new StringBuffer(30); 
    for (Iterator<Osake>  i=stocks.iterator(); i.hasNext(); ) { // ja iteraattorin toimintaa
    Osake stock = i.next(); 
    ids.append(" "+stock.getTunnusNro()); 
    }
    assertEquals("From: Osakkeet line: 349", tulos, ids.toString()); 
    Iterator<Osake>  i=stocks.iterator(); 
    assertEquals("From: Osakkeet line: 352", true, i.next() == stock1); 
    assertEquals("From: Osakkeet line: 353", true, i.next() == stock2); 
    assertEquals("From: Osakkeet line: 354", true, i.next() == stock1); 
    try {
    i.next(); 
    fail("Osakkeet: 356 Did not throw NoSuchElementException");
    } catch(NoSuchElementException _e_){ _e_.getMessage(); }
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testSearch406 
   * @throws SailoException when error
   */
  @Test
  public void testSearch406() throws SailoException {    // Osakkeet: 406
    Osake stock4 = new Osake(); stock4.parse("1|Nokia Oyj|200|3.12|624.00|"); 
    Osake stock5 = new Osake(); stock5.parse("2|Olvi Oyj|80|35.00|2800.00|"); 
    stocks.add(stock1); stocks.add(stock2); stocks.add(stock3); stocks.add(stock4); stocks.add(stock5)
    List<Osake> loytyneet; 
    loytyneet = (List<Osake>)stocks.search("*s*",1); 
    assertEquals("From: Osakkeet line: 413", 2, loytyneet.size()); 
    assertEquals("From: Osakkeet line: 414", true, loytyneet.get(0) == stock3); 
    assertEquals("From: Osakkeet line: 415", true, loytyneet.get(1) == stock4); 
    loytyneet = (List<Osake>)stocks.search("*7-*",2); 
    assertEquals("From: Osakkeet line: 417", 2, loytyneet.size()); 
    assertEquals("From: Osakkeet line: 418", true, loytyneet.get(0) == stock3); 
    assertEquals("From: Osakkeet line: 419", true, loytyneet.get(1) == stock5); 
    loytyneet = (List<Osake>)stocks.search(null,-1); 
    assertEquals("From: Osakkeet line: 421", 5, loytyneet.size()); 
  } // Generated by ComTest END
}