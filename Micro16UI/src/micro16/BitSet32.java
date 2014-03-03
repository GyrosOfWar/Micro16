package micro16;

/**
 *
 */
class BitSet32 {
    private final int value;

    public BitSet32(int value) {
        this.value = value;
    }

    public boolean get(int idx) {
        return (value & (1 << idx)) != 0;
    }

    public byte get(int start, int end) {
        if (end - start > 8) throw new IllegalArgumentException("Range must fit into one byte!");
        int mask = 0;
        for (int i = start; i < end; i++) {
            mask |= (1 << i);
        }
        int result = value & mask;
        result >>= start;
        return (byte) result;
    }

    public int getValue() {
        return value;
    }

    public BitSet32 and(BitSet32 other) {
        return new BitSet32(this.getValue() & other.getValue());
    }

    public BitSet32 or(BitSet32 other) {
        return new BitSet32(this.getValue() | other.getValue());
    }

    public BitSet32 xor(BitSet32 other) {
        return new BitSet32((this.getValue() ^ other.getValue()));
    }

    public BitSet32 not() {
        return new BitSet32(~this.getValue());
    }

    @Override
    public String toString() {
        return "0x" + Integer.toHexString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BitSet32 other = (BitSet32) o;

        return this.value == other.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }
}
