package micro16;

import java.time.Instant;

import static java.lang.System.out;

public class Main {
    public static void main(String[] args) {
        int[] memoryWrite = new int[]{
           /* R1 <- lsh(1+1)
              MBR <- R1; wr
              wr */
            0x0a151100,
            0x01200500,
            0x00200000
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
        CPU c = new CPU(memoryWrite);
        for (int n = 0; n < 20; n++) {
            int k = 0;
            Instant t = Instant.now();
            Instant end = t.plusSeconds(1);
            while (end.isAfter(Instant.now())) {
                c.reset();
                for (int i = 0; i < c.getProgramLength(); i++) {
                    c.step();
                    k++;
                }
            }
            out.println(k);
        }
    }
}
