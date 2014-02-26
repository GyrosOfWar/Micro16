package micro16;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Micro16 CPU
 */
class CPU {
    private static final int REGISTER_COUNT = 16;

    private static final int MAR_REGISTER_IDX = 3;
    private static final int MBR_REGISTER_IDX = 15;

    private static final int ALU_ADD = 1;
    private static final int ALU_AND = 2;
    private static final int ALU_NOT = 3;

    private static final int SH_LEFT = 1;
    private static final int SH_RIGHT = 2;

    private static final int COND_IF_N = 1;
    private static final int COND_IF_Z = 2;
    private static final int COND_GOTO = 3;


    private final short[] registers;
    private final int[] controlStore;
    private byte instructionCounter;
    private boolean negativeFlag;
    private boolean zeroFlag;
    private Memory memory;

    private Map<String, Short> registers2;

    // todo: name registers for less confusion (map<string, short> ?)

    public CPU(int[] controlStore) {
        this.instructionCounter = 0;
        this.controlStore = controlStore;
        this.registers = new short[REGISTER_COUNT];
        registers[0] = 0;
        registers[1] = 1;
        registers[2] = -1;
        zeroFlag = false;
        negativeFlag = false;
        this.memory = new Memory();
        registers2 = new HashMap<>();


    }

    private void resetRegisters() {
        registers2.put("-1", (short) -1);
        registers2.put("0", (short) 0);
        registers2.put("1", (short) 1);
        registers2.put("R0", (short) 0);
        registers2.put("R1", (short) 0);
        registers2.put("R2", (short) 0);
        registers2.put("R3", (short) 0);
        registers2.put("R4", (short) 0);
        registers2.put("R5", (short) 0);
        registers2.put("R6", (short) 0);
        registers2.put("R7", (short) 0);
        registers2.put("R8", (short) 0);
        registers2.put("R9", (short) 0);
        registers2.put("R10", (short) 0);


    }

    private void checkFlags(short result) {
        negativeFlag = result < 0;
        zeroFlag = result == 0;
    }

    private void aluOp(byte aluFlag, byte aBus, byte bBus, byte sBus, boolean ens) {
        short result;

        switch (aluFlag) {
            case ALU_ADD:
                result = (short) (registers[aBus] + registers[bBus]);
                break;
            case ALU_AND:
                result = (short) (registers[aBus] & registers[bBus]);
                break;
            case ALU_NOT:
                result = (short) (~registers[aBus]);
                break;
            default:
                throw new IllegalArgumentException("Not a valid ALU flag.");
        }
        if (ens) {
            registers[sBus] = result;
        }
        checkFlags(result);
    }

    private void shifterOp(byte shFlag, byte sBus, boolean ens) {
        short value = registers[sBus];
        switch (shFlag) {
            case SH_LEFT:
                value <<= 1;
                break;

            case SH_RIGHT:
                value >>= 1;
                break;
            default:
                throw new IllegalArgumentException("Invalid SH command.");
        }
        if (ens) {
            registers[sBus] = value;
        }
        checkFlags(value);

    }

    private void condOp(byte condFlag, byte addr) {
        switch (condFlag) {
            case COND_IF_N:
                if (negativeFlag) {
                    instructionCounter = addr;
                }
                break;
            case COND_IF_Z:
                if (zeroFlag) {
                    instructionCounter = addr;
                }
                break;
            case COND_GOTO:
                instructionCounter = addr;
                break;
            default:
                throw new IllegalArgumentException("Invalid COND command.");
        }
    }

    private void simpleAssignment(byte aBus, byte sBus) {
        registers[sBus] = registers[aBus];
        checkFlags(registers[sBus]);
    }

    /**
     * Processes one Micro16 instruction.
     */
    public void step() {
        negativeFlag = zeroFlag = false;

        int raw = controlStore[instructionCounter++];
        Instruction instr = new Instruction(raw);
        byte aBus = instr.A_MUX() ? MAR_REGISTER_IDX : instr.A_BUS();
        byte bBus = instr.B_BUS();
        byte sBus = instr.S_BUS();
        if (instr.MAR()) {
            sBus = MAR_REGISTER_IDX;
        } else if (instr.MBR()) {
            sBus = MBR_REGISTER_IDX;
        }
        // Neither ALU nor SH are set, instruction is
        // of the form Rn <- 1, 0, -1 or another register
        if (instr.ENS() && instr.ALU() == 0 && instr.SH() == 0) {
            simpleAssignment(instr.A_BUS(), sBus);
        }

        if (instr.ALU() != 0) {
            aluOp(instr.ALU(), instr.A_BUS(), instr.B_BUS(), sBus, instr.ENS());
        }

        if (instr.SH() != 0) {
            shifterOp(instr.SH(), sBus, instr.ENS());
        }

        if (instr.COND() != 0) {
            condOp(instr.COND(), instr.ADDR());
        }
        // Memory stuff
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Register contents:\n");
        for (int i = 3; i < registers.length; i++) {
            sb.append("R").append(i - 2).append(" = ").append(registers[i]).append("\n");
        }
        sb.append("Negative flag: ").append(negativeFlag).append(" \n");
        sb.append("Zero flag: ").append(zeroFlag).append("\n");
        sb.append("Instruction counter: ").append(instructionCounter).append("\n");
        return sb.toString();
    }
}

class Memory {
    private final byte[] data;

    private final static int MEMORY_SIZE = 1 << 16;

    public Memory() {
        data = new byte[MEMORY_SIZE];
    }

    public void setData(int index, byte value) {
        data[index] = value;
    }

    public byte getData(int index) {
        return data[index];
    }

}