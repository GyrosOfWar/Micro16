package micro16;

import java.util.BitSet;

/**
 * User: Martin
 * Date: 25.02.14
 * Time: 20:24
 */

class Instruction {
    private final BitSet bits;

    public Instruction(int raw) {
        bits = convert(raw);
    }

    private static BitSet convert(long value) {
        BitSet bits = new BitSet(32);
        int index = 0;
        while (value != 0L) {
            if (value % 2L != 0) {
                bits.set(index);
            }
            ++index;
            value = value >>> 1;
        }
        return bits;
    }

    public byte ADDR() {
        byte[] v = bits.get(0, 8).toByteArray();
        return v.length == 1 ?
                v[0]
                : 0;
    }

    public byte A_BUS() {
        byte[] v = bits.get(8, 12).toByteArray();
        return v.length == 1 ?
                v[0]
                : 0;
    }

    public byte B_BUS() {
        byte[] v = bits.get(12, 16).toByteArray();
        return v.length == 1 ?
                v[0]
                : 0;
    }

    public byte S_BUS() {
        byte[] v = bits.get(16, 20).toByteArray();
        return v.length == 1 ?
                v[0]
                : 0;
    }

    public boolean ENS() {
        return bits.get(20);
    }

    public boolean MS() {
        return bits.get(21);
    }

    public boolean RD_WR() {
        return bits.get(22);
    }

    public boolean MAR() {
        return bits.get(23);
    }

    public boolean MBR() {
        return bits.get(24);
    }

    public byte SH() {
        byte[] v = bits.get(25, 27).toByteArray();
        return v.length == 1 ?
                v[0]
                : 0;
    }

    public byte ALU() {
        byte[] v = bits.get(27, 29).toByteArray();
        return v.length == 1 ?
                v[0]
                : 0;
    }

    public byte COND() {
        byte[] v = bits.get(29, 31).toByteArray();
        return v.length == 1 ?
                v[0]
                : 0;
    }

    public boolean A_MUX() {
        return bits.get(31);
    }
}
