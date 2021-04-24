package com.labaSpo;

import java.util.ArrayList;
import java.util.List;
public class Lexer {

    public static void main(String[] args) {
        StringBuilder input = new StringBuilder(InputArguments(args));
        List<Lexeme> lexemes = new ArrayList<>();
        while (input.charAt(0) != '$') {
            Lexeme lexeme = NextLexeme(input);
            lexemes.add(lexeme);
            input.delete(0, lexeme.getValue().length());
        }
        printLexem(lexemes);
    }


    static void printLexem(List<Lexeme> lexemes) {
        for (Lexeme lexeme : lexemes) {
            System.out.printf("[%s, %s]%n",
                    lexeme.getTerminal().getIdentifier(),
                    lexeme.getValue());
        }
    }
    static String InputArguments(String[] arrays) {
        if (arrays.length == 0) {
            throw new IllegalArgumentException("Input string not found");
        }

        return arrays[0];
    }
    static final List<Terminal> TERMINALS = List.of(
            new Terminal("VAR", "[a-zA-Z][a-zA-Z0-9]*"),
            new Terminal("WHILE_KEYWORD", "while", 1),
            new Terminal("IF_KEYWORD", "if", 1),
            new Terminal("ELSE_KEYWORD", "else", 1),
            new Terminal("ASSIGN", "="),
            new Terminal("SC", ";"),
            new Terminal("NUMBER", "[0-9]+"),
            new Terminal("L_S_BR", "\\{"),
            new Terminal("R_S_BR", "\\}"),
            new Terminal("OP", "[+-/*]"),
            new Terminal("WS", "\\s+"),
            new Terminal("LBR", "\\("),
            new Terminal("RBR", "\\)"),
            new Terminal("LOGICAL_OP", "<|>|==|<=|>="),
            new Terminal("DO_KEYWORD", "do", 1)
    );

    static Lexeme NextLexeme(StringBuilder input) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(input.charAt(0));

        if (lookupTerminals(buffer).size() != 0) {
            while (lookupTerminals(buffer).size() != 0 && buffer.length() != input.length()) {
                buffer.append(input.charAt(buffer.length()));
            }

            buffer.deleteCharAt(buffer.length() - 1);

            List<Terminal> terminals = lookupTerminals(buffer);

            return new Lexeme(getPriotiryTerminal(terminals), buffer.toString());
        } else {
            throw new RuntimeException("Unexpected symbol: " + buffer);
        }
    }

    static Terminal getPriotiryTerminal(List<Terminal> terminals) {
        Terminal priorityTerminal = terminals.get(0);

        for (Terminal newTerminals : terminals) {
            if (newTerminals.getPriority() > priorityTerminal.getPriority()) {
                priorityTerminal = newTerminals;
            }
        }
        return priorityTerminal;
    }


    static List<Terminal> lookupTerminals(StringBuilder buffer) {
        List<Terminal> terminals = new ArrayList<>();

        for (Terminal terminal : TERMINALS) {
            if (terminal.matches(buffer)) {
                terminals.add(terminal);
            }
        }
        return terminals;
    }
}