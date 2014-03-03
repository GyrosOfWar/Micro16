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


    @Override
    public int hashCode() {
        return bits.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return bits.equals(obj);
    }

    @Override
    public String toString() {
        return "0x" + Integer.toHexString((int) bits.toLongArray()[0]);
    }

    private static BitSet convert(long value) {
        BitSet bits = new BitSet(CPU.INSTRUCTION_LENGTH);
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

    /**
     * The ADDR part of the instruction determines, if COND != 0, where in the program
     * to jump to.
     */
    public byte ADDR() {
        byte[] v = bits.get(0, 8).toByteArray();
        return v.length == 1 ?
            v[0]
            : 0;
    }

    /**
     * The A-BUS is the register index of the first operand.
     */
    public byte A_BUS() {
        byte[] v = bits.get(8, 12).toByteArray();
        return v.length == 1 ?
            v[0]
            : 0;
    }

    /**
     * The B-BUS is the register index of the second operand
     */
    public byte B_BUS() {
        byte[] v = bits.get(12, 16).toByteArray();
        return v.length == 1 ?
            v[0]
            : 0;
    }

    /**
     * The S-BUS is the register index of the result.
     */
    public byte S_BUS() {
        byte[] v = bits.get(16, 20).toByteArray();
        return v.length == 1 ?
            v[0]
            : 0;
    }

    /**
     * ENS (Enable S-BUS) is true if the result of the ALU/Shifter
     * should be written to the registers. If it is false, it will be ignored
     * (but the N/Z flags will still be set)
     */
    public boolean ENS() {
        return bits.get(20);
    }

    /**
     * Memory select is true if any memory operation should happen, false otherwise.
     */
    public boolean MS() {
        return bits.get(21);
    }

    /**
     * RD_WR is true for reading from memory, false for writing to memory.
     */
    public boolean RD_WR() {
        return bits.get(22);
    }

    /**
     *
     */
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
