package micro16;

import java.util.Arrays;

/**
 * Micro16 CPU
 */
class CPU {
    private static final int REGISTER_COUNT = 16;

    private static final int ALU_NOP = 0;
    private static final int ALU_ADD = 1;
    private static final int ALU_AND = 2;
    private static final int ALU_NOT = 3;

    private static final int SH_LEFT = 1;
    private static final int SH_RIGHT = 2;

    private static final int COND_IF_N = 1;
    private static final int COND_IF_Z = 2;
    private static final int COND_GOTO = 3;

    private static final int R0_IDX = 4;
    private static final int R10_IDX = 15;
    private static final int MAR_REGISTER_IDX = 3;
    public static final int MBR_REGISTER_IDX = 15;

    private final short[] registers;
    private final int[] controlStore;
    private byte instructionCounter;
    private boolean negativeFlag;
    private boolean zeroFlag;
    private Memory memory;

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
        byte sBus;

        if(instr.ENS()) {
            sBus = instr.S_BUS();
        } else if (instr.MBR()) {
            sBus = MBR_REGISTER_IDX;
        } else {
            throw new IllegalArgumentException("Not a valid S bus value");
        }

        if (instr.MAR()) {
            registers[MAR_REGISTER_IDX] = registers[bBus];
            return;
        } else if (instr.MBR()) {
            sBus = MBR_REGISTER_IDX;
        }

        // Always perform an ALU operation, since ALU = 00 is also
        // an operation (NOP)
        aluOp(instr.ALU(), aBus, bBus, sBus, instr.ENS());

        if (instr.SH() != 0) {
            shifterOp(instr.SH(), sBus, instr.ENS());
        }

        if (instr.COND() != 0) {
            condOp(instr.COND(), instr.ADDR());
        }

        // Memory stuff
        if(instr.MS()) {
            short MAR = registers[MAR_REGISTER_IDX];
            short MBR = registers[MBR_REGISTER_IDX];
            // Write
            if(instr.RD_WR()) {
                memory.write(MAR, MBR);
            }
            else {
                memory.read(MAR, registers);
            }
        }
    }

    /**
     * Resets this CPU to its original state.
     */
    public void reset() {
        this.instructionCounter = 0;
        Arrays.fill(registers, (short) 0);
        registers[0] = 0;
        registers[1] = 1;
        registers[2] = -1;
        zeroFlag = false;
        negativeFlag = false;
        this.memory.reset();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Register contents:\n");
        for (int i = R0_IDX; i < R10_IDX; i++) {
            sb.append("R").append(i - 4).append(" = ").append(registers[i]).append("\n");
        }
        sb.append("MAR = ").append(registers[MAR_REGISTER_IDX]).append("\n");
        sb.append("MBR = ").append(registers[MBR_REGISTER_IDX]).append("\n");
        sb.append("Negative flag: ").append(negativeFlag).append(" \n");
        sb.append("Zero flag: ").append(zeroFlag).append("\n");
        sb.append("Instruction counter: ").append(instructionCounter).append("\n");
        return sb.toString();
    }

    private void checkFlags(short result) {
        negativeFlag = result < 0;
        zeroFlag = result == 0;
    }

    private void aluOp(byte aluFlag, byte aBus, byte bBus, byte sBus, boolean ens) {
        short result;

        switch (aluFlag) {
            case ALU_NOP:
                result = (registers[aBus]);
                break;
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

}
