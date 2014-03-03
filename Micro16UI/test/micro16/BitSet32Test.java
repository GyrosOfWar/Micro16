package micro16;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * User: Martin
 * Date: 03.03.14
 * Time: 17:12
 */
@RunWith(JUnit4.class)
public class BitSet32Test {
    @Test
    public void testGetOne() {
        BitSet32 b = new BitSet32(0xF);
        boolean firstBit = b.get(0);
        boolean secondBit = b.get(1);
        boolean thirdBit = b.get(2);
        boolean forthBit = b.get(3);
        boolean fifthBit = b.get(4);
        assertTrue(firstBit && secondBit && thirdBit && forthBit && !fifthBit);
    }

    @Test
    public void testGetMany() {
        BitSet32 b = new BitSet32(0xC0);
        byte result = b.get(4, 8);

        assertEquals(result, (byte) 0xC);
    }

    @Test
    public void testAnd() {
        int x = 0xABCDEF;
        int y = 0xFEDCBA;
        int result = x & y;

        BitSet32 a = new BitSet32(x);
        BitSet32 b = new BitSet32(y);
        BitSet32 c = a.and(b);

        assertEquals(result, c.getValue());
    }

    @Test
    public void testOr() {
        int x = 0xCAFFEE;
        int y = 0xEEFFAA;
        int result = x | y;

        BitSet32 a = new BitSet32(x);
        BitSet32 b = new BitSet32(y);
        BitSet32 c = a.or(b);

        assertEquals(result, c.getValue());
    }

    @Test
    public void testXor() {
        int x = 0x0FFAAC;
        int y = 0xFFAECD;
        int result = x ^ y;

        BitSet32 a = new BitSet32(x);
        BitSet32 b = new BitSet32(y);
        BitSet32 c = a.xor(b);

        assertEquals(result, c.getValue());
    }

    @Test
    public void testNot() {
        int x = 0xABCEDF;
        int notX = ~x;
        BitSet32 b = new BitSet32(x);
        BitSet32 notB = b.not();

        assertEquals(notX, notB.getValue());
    }
}
