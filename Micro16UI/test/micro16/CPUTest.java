package micro16;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * User: Martin
 * Date: 02.03.14
 * Time: 22:12
 */
@RunWith(JUnit4.class)
public class CPUTest {
    @Test
    public void testAdd() {
        /*
        R0 <- lsh(1+1)
        R0 <- R0 + R0
        R0 <- R0 + R0
         */
        int[] addProgram = new int[]{
            0x0a141100,
            0x08144400,
            0x08144400
        };

        CPU cpu = new CPU(addProgram);
        cpu.stepUntilCompletion();
        short r0 = cpu.getRegisters()[CPU.R0_IDX];
        assertEquals(r0, 16);
    }

    @Test
    public void testMemory() {
           /*
            R1 <- lsh(1+1)
            MBR <- R1; wr
            wr
            */
        int[] writeToMemory = new int[]{
            0x0a151100,
            0x01200500,
            0x00200000
        };

        CPU cpu = new CPU(writeToMemory);
        cpu.stepUntilCompletion();

        short memoryValue = cpu.getMemory().get(0);

        assertEquals(memoryValue, 4);
    }

    @Test
    public void testGoto() {
        // Why is goto a keyword in Java?
        /*
        R0 <- lsh(1+1)
        goto 3
        R0 <- R0 + R0
        R0 <- R0 + R0
         */
        int[] gotoTest = new int[]{
            0x0a141100,
            0x60000003,
            0x08144400,
            0x08144400
        };

        CPU cpu = new CPU(gotoTest);
        cpu.stepUntilCompletion();

        short r0 = cpu.getRegisters()[CPU.R0_IDX];
        assertEquals(r0, 8);
    }
}
