package osakerekisteri.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import osakerekisteri.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2021.05.04 20:27:22 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class TransaktioTest {



  // Generated by ComTest BEGIN
  /** testRegister109 */
  @Test
  public void testRegister109() {    // Transaktio: 109
    Transaktio trans1 = new Transaktio(); 
    assertEquals("From: Transaktio line: 111", 0, trans1.getTransactionId()); 
    trans1.register(); 
    Transaktio trans2 = new Transaktio(); 
    trans2.register(); 
    int n1 = trans1.getTransactionId(); 
    int n2 = trans2.getTransactionId(); 
    assertEquals("From: Transaktio line: 117", n2-1, n1); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testToString210 */
  @Test
  public void testToString210() {    // Transaktio: 210
    Transaktio transaktio = new Transaktio(); 
    transaktio.parse("1 | 1 | 2007-11-30 | Osto | 27.32 | 200 | 54.64 | 5518.64 |"); 
    assertEquals("From: Transaktio line: 213", "1|1|2007-11-30|Osto|27.32|200|54.64|5518.64|", transaktio.toString()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testParse235 */
  @Test
  public void testParse235() {    // Transaktio: 235
    Transaktio transaction = new Transaktio(); 
    transaction.parse("1 | 1 | 30.11.2007 | Osto | 27.32 | 200 | 54.64 | 5518.64 |"); 
    assertEquals("From: Transaktio line: 238", 3, transaction.getTransactionId()); 
    assertEquals("From: Transaktio line: 239", true, transaction.toString().startsWith("1 | 1 | 30.11.2007 | Osto | 27.32 | 200 | 54.64 | 5518.64 |"));  // on enemmäkin kuin 3 kenttää, siksi loppu |
    transaction.register(); 
    int n = transaction.getTransactionId(); 
    transaction.parse(""+(n+20));  // Otetaan merkkijonosta vain tunnusnumero
    transaction.register();  // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi
    assertEquals("From: Transaktio line: 245", n+20+1, transaction.getTransactionId()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testClone296 
   * @throws CloneNotSupportedException when error
   */
  @Test
  public void testClone296() throws CloneNotSupportedException {    // Transaktio: 296
    Transaktio trans = new Transaktio(); 
    trans.parse("1 | 1 | 30.11.2007 | Osto | 27.32 | 200 | 54.64 | 5518.64 |"); 
    Transaktio klooni = trans.clone(); 
    assertEquals("From: Transaktio line: 301", trans.toString(), klooni.toString()); 
    trans.parse("2 | 1 | 30.11.2007 | Osto | 27.32 | 200 | 54.64 | 5518.64 |"); 
    assertEquals("From: Transaktio line: 303", false, klooni.toString().equals(trans.toString())); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testGiveTransaction361 */
  @Test
  public void testGiveTransaction361() {    // Transaktio: 361
    Transaktio trans = new Transaktio(); 
    trans.parse("1 | 30.11.2007 | Osto | 27.32 | 200 | 54.64 | 5518.64 | 1 |"); 
    assertEquals("From: Transaktio line: 364", "Osto", trans.giveTransaction(0)); 
    assertEquals("From: Transaktio line: 365", "30.11.2007", trans.giveTransaction(1)); 
    assertEquals("From: Transaktio line: 366", "200", trans.giveTransaction(2)); 
    assertEquals("From: Transaktio line: 367", "27.32", trans.giveTransaction(3)); 
    assertEquals("From: Transaktio line: 368", "54.64", trans.giveTransaction(4)); 
    assertEquals("From: Transaktio line: 369", "5518.64", trans.giveTransaction(5)); 
  } // Generated by ComTest END
}