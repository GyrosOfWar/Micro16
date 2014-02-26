package micro16;

import java.util.Arrays;

/**
 * User: Martin
 * Date: 26.02.14
 * Time: 16:06
 */
class Memory {
    /*  For simplicity's sake, the memory is not byte-addressable
        but only in machine word chunks (2 bytes).
    */
    private final short[] data;

    private final static int MEMORY_SIZE = 1 << 16;

    private boolean ready;

    public Memory() {
        data = new short[MEMORY_SIZE];
        ready = true;
    }

    public boolean write(int idx, short value) {
        if (!ready) {
            ready = true;
            return false;
        }
        ready = false;
        data[idx] = value;
        return true;
    }

    public boolean read(int idx, short[] registers) {
        if (!ready) {
            ready = true;
            return false;
        }

        registers[CPU.MBR_REGISTER_IDX] = data[idx];
        return true;
    }


    public void reset() {
        Arrays.fill(data, (short) 0);
        ready = true;
    }

    public boolean isReady() {
        return ready;
    }

}
