package osakerekisteri.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import osakerekisteri.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2021.05.07 16:48:00 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class OsakeTest {



  // Generated by ComTest BEGIN
  /** testGetName52 */
  @Test
  public void testGetName52() {    // Osake: 52
    Osake stock = new Osake(); 
    stock.giveStock(0); 
    { String _l_=stock.getName(),_r_=""; if ( !_l_.matches(_r_) ) fail("From: Osake line: 55" + " does not match: ["+ _l_ + "] != [" + _r_ + "]");}; 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testRegister104 */
  @Test
  public void testRegister104() {    // Osake: 104
    Osake osake1 = new Osake(); 
    assertEquals("From: Osake line: 106", 0, osake1.getId()); 
    assertEquals("From: Osake line: 107", 2, osake1.register()); 
    Osake osake2 = new Osake(); 
    osake2.register(); 
    int n1 = osake1.getId(); 
    int n2 = osake2.getId(); 
    assertEquals("From: Osake line: 112", n2-1, n1); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testClone189 
   * @throws CloneNotSupportedException when error
   */
  @Test
  public void testClone189() throws CloneNotSupportedException {    // Osake: 189
    Osake stock = new Osake(); 
    stock.parse("1|Nokia Oyj|200|3.12|624.00|"); 
    Osake kopio = stock.clone(); 
    assertEquals("From: Osake line: 194", stock.toString(), kopio.toString()); 
    stock.parse("2|Olvi Oyj|20|20.00|400.00|"); 
    assertEquals("From: Osake line: 196", false, kopio.toString().equals(stock.toString())); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testToString221 */
  @Test
  public void testToString221() {    // Osake: 221
    Osake stock = new Osake(); 
    stock.parse("1|Nokia Oyj|200|3.12|624.00|"); 
    assertEquals("From: Osake line: 224", false, stock.toString().startsWith("1|Nokia Oyj|200|3.12|624.00|"));  // on enemmäkin kuin 3 kenttää, siksi loppu |
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testParse244 */
  @Test
  public void testParse244() {    // Osake: 244
    Osake stock = new Osake(); 
    stock.parse("1|Nokia Oyj|200|3.12|624.00|"); 
    assertEquals("From: Osake line: 247", 1, stock.getId()); 
    assertEquals("From: Osake line: 248", true, stock.toString().startsWith("1|Nokia Oyj|200|3.12|"));  // on enemmäkin kuin 3 kenttää, siksi loppu |
    stock.register(); 
    int n = stock.getId(); 
    stock.parse(""+(n+20));  // Otetaan merkkijonosta vain tunnusnumero
    stock.register();  // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi
    assertEquals("From: Osake line: 254", n+20+1, stock.getId()); 
  } // Generated by ComTest END
}