package micro16;

import static java.lang.System.out;

public class Main {
    public static void main(String[] args) {
        int[] memoryWrite = new int[]{
           /* R1 <- lsh(1+1)
              MBR <- R1; wr
              wr */
            0x0a151100,
            0x01200500, // ENS not set, but should still write to MBR
            0x00200000  // Invalid S-Bus value
        };

        int[] lshAndAdd = new int[]{
            0x0a1b1100,
            0x081bbb00,
            0x001c0000,
            0x081a1100
        };

        int[] subtraction = new int[]{
            0x0a141100,
            0x08151100,
            0x18160500,
            0x08161600,
            0x08164600
        };
        CPU c = new CPU(subtraction);
        for (int i = 0; i < c.getProgramLength(); i++) {
            //out.println(c);
            c.step();
        }
        out.println(c);
    }
}
