package micro16;

public class Main {
    public static void main(String[] args) {
        int[] memoryWrite = new int[]{
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

        CPU c = new CPU(memoryWrite);
        for (int i = 0; i < c.getProgramLength(); i++) {
            Measurer.measure(c::step);
        }
    }
}
