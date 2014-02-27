package micro16;

import java.time.Duration;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class Main {
    public static void main(String[] args) {
        CPU c = new CPU(new int[]{
                0x0a1b1100,
                0x081bbb00,
                0x001c0000,
                0x081a1100
        });
        Stopwatch s = new Stopwatch();
        for (int i = 0; i < c.getProgramLength(); i++) {
            s.start();

            c.step();

            s.stop();
            s.reset();
        }
        out.println(s);
    }
}
