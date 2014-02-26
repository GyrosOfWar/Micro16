package micro16;

import static java.lang.System.out;

/**
 * User: Martin
 * Date: 25.02.14
 * Time: 18:25
 */

public class Main {
    public static void main(String[] args) {
        CPU c = new CPU(new int[]{0x0a141100});
        out.println(c);
        c.step();
        out.println(c);
    }
}
