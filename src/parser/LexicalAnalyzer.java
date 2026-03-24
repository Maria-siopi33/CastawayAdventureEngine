package parser;

import java.util.Arrays;
import java.util.List;

public class LexicalAnalyzer {
    public List<String> analyze(String input) {
        return Arrays.asList(input.toLowerCase().trim().split("\\s+"));
    }
}