package main.main;

import parser.LexicalAnalyzer;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LexicalAnalyzer parser = new LexicalAnalyzer();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Καλωσήρθατε στον Ναυαγό! Πληκτρολογήστε μια εντολή:");

        String input = scanner.nextLine();
        List<String> tokens = parser.analyze(input);

        System.out.println("Ο Parser αναγνώρισε: " + tokens);
    }
}