package micro16;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.*;
import org.codehaus.jparsec.*;

/**
 * User: werner
 * Date: 26.02.14
 * Time: 16:08
 */
public class Parser {
    private final List<String> lines;
    private final List<String> registerNames = Arrays.asList(
        "R0", "R1", "R2", "R3", "R4", "R5", "R6", "R7",
        "R8", "R9", "R10", "MBR", "MAR");

    public Parser(String path) throws IOException {
        lines = Files.readAllLines(Paths.get(path));
    }

    public Parser(List<String> l) {
        lines = l;
    }

    public List<Instruction> parse() {
        List<Instruction> ret = new ArrayList<>();
        
        return ret;
    }
}
