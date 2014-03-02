package micro16;

import java.util.Arrays;

/**
 * User: Martin
 * Date: 26.02.14
 * Time: 16:06
 */
class Memory {
    /*  For simplicity's sake, the memory is not byte-addressable
        but only in machine word chunks (2 bytes). */
    private final short[] data;

    private final static int MEMORY_SIZE = 1 << 16;

    private boolean ready;

    public Memory() {
        data = new short[MEMORY_SIZE];
        ready = false;
    }

    /**
     * Writes a short value to the memory.
     *
     * @param idx   Memory address to write to
     * @param value Value to put into the memory
     * @return True if the write was successful, false otherwise
     */
    public boolean write(int idx, short value) {
        if (!ready) {
            ready = true;
            return false;
        }
        ready = false;
        data[idx] = value;
        return true;
    }

    /**
     * Reads a memory value to the register.
     *
     * @param idx       Memory address to read from
     * @param registers The CPU's register array that the memory value will be stored in
     * @return True if the read was successful, false otherwise.
     */
    public boolean read(int idx, short[] registers) {
        if (!ready) {
            ready = true;
            return false;
        }
        ready = false;
        // Write to the MBR register of the CPU directly
        registers[CPU.MBR_REGISTER_IDX] = data[idx];
        return true;
    }

    /**
     * Clears the memory by overwriting each value in the
     * memory with 0.
     */
    public void reset() {
        Arrays.fill(data, (short) 0);
        ready = false;
    }

    /**
     * Outputs the first 16 memory addresses and their values of this memory.
     *
     * @return A string representation of this object.
     */
    @Override
    public String toString() {
        return toString(0, 16);
    }

    public String toString(int s, int e) {
        StringBuilder sb = new StringBuilder();
        for (int i = s; i < e; i++) {
            sb.append("0x").append(Integer.toHexString(i).toUpperCase()).append(": ").append(data[i]).append("\n");
        }

        return sb.toString();
    }

    short get(int idx) {
        return data[idx];
    }

}
