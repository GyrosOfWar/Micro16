package micro16;

import java.util.Arrays;

/**
 * User: werner
 * Date: 26.02.14
 * Time: 16:06
 */
class Memory {
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
        Arrays.fill(data, (byte) 0);
        ready = true;
    }

    public boolean isReady() {
        return ready;
    }

}
