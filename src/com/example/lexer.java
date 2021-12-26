package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class lexer {

    int currentRow = 1;
    int currentColumn = 1;
    char currentCharacter;
    int currentBlockNo = 0;
    String Code;
    int Index = 0;

    ArrayList<String> keywords = new ArrayList<>(
            Arrays.asList("int", "long", "register", "return", "short", "signed",	"sizeof", "static",
                    "struct", "switch",	"typedef", "union", "unsigned", "void", "volatile",	"while",
                    "double", "else", "enum", "extern", "float", "for",	"goto",	"if", "default",
                    "auto", "break", "case", "char", "do", "continue", "volatile"));

    // Constructor
    public lexer() {
        try {
            Code = readFile("input.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert Code != null;
        currentCharacter = Code.charAt(0);
    }

    // Read all text from a file into a String
    public static String readFile(String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        FileInputStream fileStream = new FileInputStream(new File(path));
        BufferedReader br = new BufferedReader(new InputStreamReader(fileStream));
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + System.lineSeparator());
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    // Peeks the next character
    public char peekNextCharacter() {
        int index = Index;
        index++;
        return Code.charAt(index);
    }

    // Reads a number
    public String readNumber() {
        StringBuilder number = new StringBuilder();
        number.append(currentCharacter);
        int i = Index;

        while (true) {
            i++;
            if (Code.charAt(i) <= '9' && Code.charAt(i) >= '0') {
                number.append(Code.charAt(i));
            } else break;
        }
        return number.toString();
    }

    // Reads a word
    public String readWord() {
        StringBuilder word = new StringBuilder();
        word.append(currentCharacter);
        int i = Index;

        while (true) {
            i++;
            if (isLetter(i) || (isNumber(i)) || (Code.charAt(i) == '_')) {
                word.append(Code.charAt(i));
            } else break;
        }
        return word.toString();
    }

    // Checks if a character is number or not
    public boolean isNumber(int i) {
        return Code.charAt(i) >= '0' && Code.charAt(i) <= '9';
    }

    // Checks if a character is letter or not
    public boolean isLetter(int i) {
        return (Code.charAt(i) >= 'a' && Code.charAt(i) <= 'z') || (Code.charAt(i) >= 'A' && Code.charAt(i) <= 'Z');
    }

    // Checks if a word is keyword or not
    public boolean isKeyword(String word) {
        return keywords.contains(word);
    }

    //finds the next token
    public void nextToken() {
        int i = 0;
        while (i != Code.length()) {
            i++;
            switch (currentCharacter) {

                case '+':
                    if (peekNextCharacter() == '+') {
                        new Token("++", "Increment Operator", currentRow, currentColumn,currentBlockNo);
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else if (peekNextCharacter() == '=') {
                        new Token("+=", "Assignment Operator", currentRow, currentColumn,currentBlockNo);
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else {
                        new Token("+", "Arithmetic Operator", currentRow, currentColumn,currentBlockNo);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '-':
                    if (peekNextCharacter() == '-') {
                        new Token("--", "Decrement Operator", currentRow, currentColumn,currentBlockNo);
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else if (peekNextCharacter() == '=') {
                        new Token("-=", "Assignment Operator", currentRow, currentColumn,currentBlockNo);
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else {
                        new Token("-", "Arithmetic Operator", currentRow, currentColumn,currentBlockNo);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '<':
                    if (peekNextCharacter() == '=') {
                        new Token("<=", "Relational Operator", currentRow, currentColumn,currentBlockNo);
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else if (peekNextCharacter() == '<') {
                        new Token("<<", "Bitwise Operator", currentRow, currentColumn,currentBlockNo);
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else {
                        new Token("<", "Relational Operator", currentRow, currentColumn,currentBlockNo);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '>':
                    if (peekNextCharacter() == '=') {
                        new Token(">=", "Relational Operator", currentRow, currentColumn,currentBlockNo);
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else if (peekNextCharacter() == '>') {
                        new Token(">>", "Bitwise Operator", currentRow, currentColumn,currentBlockNo);
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else {
                        new Token(">", "Relational Operator", currentRow, currentColumn,currentBlockNo);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '{':
                    new Token("{", "Bracket", currentRow, currentColumn,currentBlockNo);
                    currentCharacter = Code.charAt(++Index);
                    currentColumn++;
                    currentBlockNo++;
                    break;

                case '}':
                    new Token("}", "Bracket", currentRow, currentColumn,currentBlockNo);
                    currentCharacter = Code.charAt(++Index);
                    currentColumn++;
                    currentBlockNo--;
                    break;

                case '*':
                    if (peekNextCharacter() == '=') {
                        new Token("*=", "Assignment Operator", currentRow, currentColumn,currentBlockNo);
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else {
                        new Token("*", "Arithmetic Operator", currentRow, currentColumn,currentBlockNo);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '/':
                    if (peekNextCharacter() == '=') {
                        new Token("/=", "Assignment Operator", currentRow, currentColumn,currentBlockNo);
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else if (peekNextCharacter() == '/') { //comment found so we skip this part
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;

                        while (currentCharacter != '\r') {
                            currentCharacter = Code.charAt(++Index);
                            currentColumn++;
                        }
                    } else {
                        new Token("/", "Arithmetic Operator", currentRow, currentColumn,currentBlockNo);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '%':
                    if (peekNextCharacter() == '=') {
                        new Token("%=", "Assignment Operator", currentRow, currentColumn,currentBlockNo);
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else {
                        new Token("%", "Arithmetic Operator", currentRow, currentColumn,currentBlockNo);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '=':
                    if (peekNextCharacter() == '=') {
                        new Token("==", "Relational Operator", currentRow, currentColumn,currentBlockNo);
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else {
                        new Token("=", "Assignment Operator", currentRow, currentColumn,currentBlockNo);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '&':
                    if (peekNextCharacter() == '&') {
                        new Token("&&", "Logical Operator", currentRow, currentColumn,currentBlockNo);
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else {
                        new Token("&", "Bitwise Operator", currentRow, currentColumn,currentBlockNo);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '|':
                    if (peekNextCharacter() == '|') {
                        new Token("||", "Logical Operator", currentRow, currentColumn,currentBlockNo);
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else {
                        new Token("|", "Bitwise Operator", currentRow, currentColumn,currentBlockNo);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '!':
                    if (peekNextCharacter() == '=') {
                        new Token("!=", "Relational Operator", currentRow, currentColumn,currentBlockNo);
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else {
                        new Token("!", "Logical Operator", currentRow, currentColumn,currentBlockNo);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                //if a word starts with _
                case '_':
                    if (isLetter(Code.indexOf(peekNextCharacter())) || isNumber(Code.indexOf(peekNextCharacter()))) {
                        int start = currentColumn;
                        String word = readWord();
                        new Token(word, "Identifier", currentRow, start,currentBlockNo);
                        currentColumn = word.length() + currentColumn;
                        Index+=word.length();
                        currentCharacter = Code.charAt(Index);
                    }
                    break;

                case '(':
                    new Token("(", "Bracket", currentRow, currentColumn,currentBlockNo);
                    currentCharacter = Code.charAt(++Index);
                    currentColumn++;
                    break;

                case ')':
                    new Token(")", "Bracket", currentRow, currentColumn,currentBlockNo);
                    currentCharacter = Code.charAt(++Index);
                    currentColumn++;
                    break;

                case '[':
                    new Token("[", "Bracket", currentRow, currentColumn,currentBlockNo);
                    currentCharacter = Code.charAt(++Index);
                    currentColumn++;
                    break;

                case ']':
                    new Token("]", "Bracket", currentRow, currentColumn,currentBlockNo);
                    currentCharacter = Code.charAt(++Index);
                    currentColumn++;
                    break;

                case '?':
                    if (peekNextCharacter() == ':') {
                        new Token("?:", "Conditional Operator", currentRow, currentColumn,currentBlockNo);
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    }
                    break;

                case '~':
                    new Token("~", "Bitwise Operator", currentRow, currentColumn,currentBlockNo);
                    currentCharacter = Code.charAt(++Index);
                    currentColumn++;
                    break;

                case '^':
                    new Token("^", "Bitwise Operator", currentRow, currentColumn,currentBlockNo);
                    currentCharacter = Code.charAt(++Index);
                    currentColumn++;
                    break;

                case ',':
                    new Token(",", "Delimiter", currentRow, currentColumn,currentBlockNo);
                    currentCharacter = Code.charAt(++Index);
                    currentColumn++;
                    break;

                case ':':
                    new Token(":", "Delimiter", currentRow, currentColumn,currentBlockNo);
                    currentCharacter = Code.charAt(++Index);
                    currentColumn++;
                    break;

                case ';':
                    new Token(";", "Delimiter", currentRow, currentColumn,currentBlockNo);
                    currentCharacter = Code.charAt(++Index);
                    currentColumn++;
                    break;

                case '\r':
                    currentCharacter = Code.charAt(++Index);
                    currentColumn = 1;
                    break;

                case '\n':
                    Index++;
                    if (Index == Code.length()) {
                        i = Code.length();
                        break;
                    }
                    else {
                    currentCharacter = Code.charAt(Index);
                    currentRow++;
                    break;
                    }

                case ' ':
                    currentCharacter = Code.charAt(++Index);
                    currentColumn++;
                    break;

                default:

                    if (isNumber(Code.indexOf(currentCharacter))) {
                        int start = currentColumn;
                        String number = readNumber();
                        new Token(number, "Number", currentRow, start,currentBlockNo);
                        currentColumn = number.length() + currentColumn;
                        Index+=number.length();
                        currentCharacter = Code.charAt(Index);
                    }

                    else if (isLetter(Code.indexOf(currentCharacter))) {
                        int start = currentColumn;
                        String word = readWord();
                        if (isKeyword(word)) {
                            new Token(word, "Keyword", currentRow, start,currentBlockNo);
                            currentColumn = word.length() + currentColumn;
                            Index+=word.length();
                            currentCharacter = Code.charAt(Index);
                        }
                        else {
                            new Token(word, "Identifier", currentRow, start,currentBlockNo);
                            currentColumn = word.length() + currentColumn;
                            Index+=word.length();
                            currentCharacter = Code.charAt(Index);
                        }
                    }

            }
        }
    }

    public static void main(String[] args) {
        lexer ll = new lexer();
        ll.nextToken();
    }
}
