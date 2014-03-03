package micro16;

/**
 * User: Martin
 * Date: 25.02.14
 * Time: 20:24
 */

class Instruction {
    private final BitSet32 bits;

    public Instruction(int raw) {
        bits = new BitSet32(raw);
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
        return bits.toString();
    }

    /**
     * The ADDR part of the instruction determines, if COND != 0, where in the program
     * to jump to.
     */
    public byte ADDR() {
        return bits.get(0, 8);
    }

    /**
     * The A-BUS is the register index of the first operand.
     */
    public byte A_BUS() {
        return bits.get(8, 12);
    }

    /**
     * The B-BUS is the register index of the second operand
     */
    public byte B_BUS() {
        return bits.get(12, 16);
    }

    /**
     * The S-BUS is the register index of the result.
     */
    public byte S_BUS() {
        return bits.get(16, 20);
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
        return bits.get(25, 27);
    }

    public byte ALU() {
        return bits.get(27, 29);
    }

    public byte COND() {
        return bits.get(29, 31);
    }

    public boolean A_MUX() {
        return bits.get(31);
    }
}
