package com.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class lexer {

    int currentRow = 1;
    int currentColumn = 1;
    char currentCharacter;
    int currentBlock;
    String Code;
    int currentIndex;

    ArrayList<String> keywords = new ArrayList<>(
            Arrays.asList("int", "long", "register", "return", "short", "signed",	"sizeof", "static",
                    "struct", "switch",	"typedef", "union", "unsigned", "void", "volatile",	"while",
                    "double", "else", "enum", "extern", "float", "for",	"goto",	"if", "default",
                    "auto", "break", "case", "char", "do", "continue", "volatile"));

    public lexer() {
        try {
            Code = readFile("input.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentCharacter = Code.charAt(0);
        int c = Code.indexOf(currentCharacter);
    }


    // Read all text from a file into a String in Java
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

    public char nextCharacter() {
        int index = Code.indexOf(currentCharacter);
        index++;
        return Code.charAt(index);
    }

    public String readNumber(char number) {
        StringBuilder word = new StringBuilder();
        word.append(currentCharacter);
        int i = Code.indexOf(currentCharacter);
        while (true) {
            i++;
            if (Code.charAt(i) < '9' && Code.charAt(i) > '0') {
                word.append(Code.charAt(i));
//                currentCharacter = Code.charAt(i);
//                currentColumn++;
                continue;
            } else
                break;
        }
        return word.toString();
    }

    public void nextToken() {
        int i = 0;
        int Index = 0;
        while (i != Code.length()) {
            i++;
            switch (currentCharacter) {
                case '+':
                    if (nextCharacter() == '+') {
                        new Token("++", "Increment Operator", currentRow, currentColumn);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    } else if (nextCharacter() == '=') {
                        new Token("+=", "Assignment Operator", currentRow, currentColumn);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    } else {
                        new Token("+", "Arithmetic Operator", currentRow, currentColumn);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '-':
                    if (nextCharacter() == '-') {
                        new Token("--", "Decrement Operator", currentRow, currentColumn);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    } else if (nextCharacter() == '=') {
                        new Token("-=", "Assignment Operator", currentRow, currentColumn);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    } else {
                        new Token("-", "Arithmetic Operator", currentRow, currentColumn);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '<':
                    if (nextCharacter() == '=') {
                        new Token("<=", "Relational Operator", currentRow, currentColumn);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    } else if (nextCharacter() == '<') {
                        new Token("<<", "Bitwise Operator", currentRow, currentColumn);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    } else {
                        new Token("<", "Relational Operator", currentRow, currentColumn);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '>':
                    if (nextCharacter() == '=') {
                        new Token(">=", "Relational Operator", currentRow, currentColumn);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    } else if (nextCharacter() == '>') {
                        new Token(">>", "Bitwise Operator", currentRow, currentColumn);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    } else {
                        new Token(">", "Relational Operator", currentRow, currentColumn);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '*':
                    if (nextCharacter() == '=') {
                        new Token("*=", "Assignment Operator", currentRow, currentColumn);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    } else {
                        new Token("*", "Arithmetic Operator", currentRow, currentColumn);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '/':
                    if (nextCharacter() == '=') {
                        new Token("/=", "Assignment Operator", currentRow, currentColumn);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    } else {
                        new Token("/", "Arithmetic Operator", currentRow, currentColumn);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '%':
                    if (nextCharacter() == '=') {
                        new Token("%=", "Assignment Operator", currentRow, currentColumn);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    } else {
                        new Token("%", "Arithmetic Operator", currentRow, currentColumn);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '=':
                    if (nextCharacter() == '=') {
                        new Token("==", "Relational Operator", currentRow, currentColumn);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    } else {
                        new Token("=", "Assignment Operator", currentRow, currentColumn);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '&':
                    if (nextCharacter() == '&') {
                        new Token("&&", "Logical Operator", currentRow, currentColumn);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    } else {
                        new Token("&", "Bitwise Operator", currentRow, currentColumn);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '|':
                    if (nextCharacter() == '|') {
                        new Token("||", "Logical Operator", currentRow, currentColumn);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    } else {
                        new Token("|", "Bitwise Operator", currentRow, currentColumn);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '!':
                    if (nextCharacter() == '=') {
                        new Token("!=", "Relational Operator", currentRow, currentColumn);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    } else {
                        new Token("!", "Logical Operator", currentRow, currentColumn);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '?':
                    if (nextCharacter() == ':') {
                        new Token("?:", "Conditional Operator", currentRow, currentColumn);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '~':
                    new Token("~", "Bitwise Operator", currentRow, currentColumn);
                    currentCharacter = Code.charAt(++Index);
                    currentColumn++;
                    break;

                case '^':
                    new Token("^", "Bitwise Operator", currentRow, currentColumn);
                    currentCharacter = Code.charAt(++Index);
                    currentColumn++;
                    break;

                case ';':
                    new Token(";", "Delimiter", currentRow, currentColumn);
                    currentCharacter = Code.charAt(++Index);
                    currentColumn++;

                case '\n':
                    currentCharacter = Code.charAt(++Index);
                    currentColumn = 0;
                    currentRow++;

                default:
                    if (currentCharacter > '0' && currentCharacter < '9') {
                        int start = currentColumn;
                        String number = readNumber(currentCharacter);
                        new Token(number, "Number", currentRow, start);
                        currentColumn = number.length() + currentColumn;
                        Index+=number.length();
                        currentCharacter = Code.charAt(Index);
                    }

                    else {
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
            }
        }
    }

    public static void main(String[] args) {
        lexer ll = new lexer();
        ll.nextToken();
    }
}
