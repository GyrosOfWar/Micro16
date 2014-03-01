package micro16;

import org.codehaus.jparsec.Terminals;
import org.codehaus.jparsec.functors.Binary;
import org.codehaus.jparsec.functors.Unary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: werner
 * Date: 26.02.14
 * Time: 16:08
 */
public class Micro16Parser {
    enum BinaryOperator implements Binary<Short> {
        PLUS {
            public Short map(Short a, Short b) {
                return (short) (a + b);
            }
        },
        AND {
            public Short map(Short a, Short b) {
                return (short) (a & b);
            }
        }
    }

    enum UnaryOperator implements Unary<Short> {
        NEG {
            public Short map(Short a) {
                return (short) (~a);
            }
        },

        LSH {
            public Short map(Short a) {
                return (short) (a << (short) 1);
            }
        },
        RSH {
            public Short map(Short a) {
                return (short) (a >>> (short) 1);
            }
        }
    }

    private final List<String> lines;
    private final List<String> registerNames = Arrays.asList(
        "R0", "R1", "R2", "R3", "R4", "R5", "R6", "R7",
        "R8", "R9", "R10", "MBR", "MAR");

    private final Terminals TERMINALS = Terminals.caseInsensitive(
        // Operators
        new String[]{"&", "+", "~", "<-"},
        // "Keywords"
        new String[]{
            "R0", "R1", "R2", "R3", "R4",
            "R5", "R6", "R7", "R8", "R9",
            "R10", "MBR", "MAR", "0",
            "1", "-1", "lsh", "rsh"});

    public Micro16Parser(String path) throws IOException {
        lines = Files.readAllLines(Paths.get(path));
    }

    public Micro16Parser(List<String> l) {
        lines = l;
    }

    public List<Instruction> parse() {
        List<Instruction> ret = new ArrayList<>();

        return ret;
    }
}
