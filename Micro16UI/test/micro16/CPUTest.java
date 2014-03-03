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
    public void testRegisters() {
        /*
        R10 <- 1 + 1
        R9 <- R10
        R10 <- lsh(R10)
         */
        int[] registers = new int[]{
            0x081e1100,
            0x001d0e00,
            0x021e0e00
        };
        CPU cpu = new CPU(registers);
        cpu.stepUntilCompletion();

        short r9 = cpu.getRegisters()[CPU.R0_IDX + 9];
        short r10 = cpu.getRegisters()[CPU.R0_IDX + 10];

        assertEquals(2, r9);
        assertEquals(4, r10);
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

    @Test
    public void testIfN() {
        /*
        R0 <- -1
        R1 <- 1 + 1
        R0; if N goto 4
        R1 <- R1 + R1
        R1 <- R1 + 1
         */
        int[] ifNTest = new int[]{
            0x00140200,
            0x08151100,
            0x20000404,
            0x08155500,
            0x08151500
        };

        CPU cpu = new CPU(ifNTest);
        cpu.stepUntilCompletion();

        short r1 = cpu.getRegisters()[CPU.R0_IDX + 1];

        assertEquals(r1, 3);
    }

    @Test
    public void testIfZ() {
        /*
        R1 <- 1
        R0 <- R1 + (-1)
        R0; if Z goto 4
        R0 <- R0 + 1
        R2 <- R2 + 1
         */
        int[] ifZTest = new int[]{
            0x00150100,
            0x08142500,
            0x40000404,
            0x08141400,
            0x08161600
        };

        CPU cpu = new CPU(ifZTest);
        cpu.stepUntilCompletion();
        short r0 = cpu.getRegisters()[CPU.R0_IDX];
        assertEquals(r0, 0);
    }
}
