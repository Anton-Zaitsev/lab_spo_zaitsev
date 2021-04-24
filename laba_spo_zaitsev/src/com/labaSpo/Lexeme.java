package com.labaSpo;

public class Lexeme {

    private final Terminal terminal;
    private final String value;

    public Lexeme(Terminal newTerminal, String newValue) {
        this.terminal = newTerminal;
        this.value = newValue;
    }

    public Terminal getTerminal() {
        return terminal;
    }
    public String getValue() {
        return value;
    }
}