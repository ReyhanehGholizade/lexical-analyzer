package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class lexer {

    int currentRow = 1;
    int currentColumn = 1;
    char currentCharacter;
    int currentBlock;
    String Code;
    int Index = 0;

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

    public char nextCharacter() {
        int index = Code.indexOf(currentCharacter);
        index++;
        return Code.charAt(index);
    }

    public String readNumber(char number) {
        StringBuilder word = new StringBuilder();
        word.append(currentCharacter);
        int i = Index;
        while (true) {
            i++;
            if (Code.charAt(i) <= '9' && Code.charAt(i) >= '0') {
                word.append(Code.charAt(i));
                continue;
            } else
                break;
        }
        return word.toString();
    }

    public void nextToken() {
        int i = 0;
        while (i != Code.length()) {
            i++;
            switch (currentCharacter) {
                case '+':
                    if (nextCharacter() == '+') {
                        new Token("++", "Increment Operator", currentRow, currentColumn);
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else if (nextCharacter() == '=') {
                        new Token("+=", "Assignment Operator", currentRow, currentColumn);
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else {
                        new Token("+", "Arithmetic Operator", currentRow, currentColumn);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '-':
                    if (nextCharacter() == '-') {
                        new Token("--", "Decrement Operator", currentRow, currentColumn);
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else if (nextCharacter() == '=') {
                        new Token("-=", "Assignment Operator", currentRow, currentColumn);
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else {
                        new Token("-", "Arithmetic Operator", currentRow, currentColumn);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '<':
                    if (nextCharacter() == '=') {
                        new Token("<=", "Relational Operator", currentRow, currentColumn);
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else if (nextCharacter() == '<') {
                        new Token("<<", "Bitwise Operator", currentRow, currentColumn);
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else {
                        new Token("<", "Relational Operator", currentRow, currentColumn);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '>':
                    if (nextCharacter() == '=') {
                        new Token(">=", "Relational Operator", currentRow, currentColumn);
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else if (nextCharacter() == '>') {
                        new Token(">>", "Bitwise Operator", currentRow, currentColumn);
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else {
                        new Token(">", "Relational Operator", currentRow, currentColumn);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '*':
                    if (nextCharacter() == '=') {
                        new Token("*=", "Assignment Operator", currentRow, currentColumn);
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else {
                        new Token("*", "Arithmetic Operator", currentRow, currentColumn);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '/':
                    if (nextCharacter() == '=') {
                        new Token("/=", "Assignment Operator", currentRow, currentColumn);
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else {
                        new Token("/", "Arithmetic Operator", currentRow, currentColumn);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '%':
                    if (nextCharacter() == '=') {
                        new Token("%=", "Assignment Operator", currentRow, currentColumn);
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else {
                        new Token("%", "Arithmetic Operator", currentRow, currentColumn);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '=':
                    if (nextCharacter() == '=') {
                        new Token("==", "Relational Operator", currentRow, currentColumn);
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else {
                        new Token("=", "Assignment Operator", currentRow, currentColumn);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '&':
                    if (nextCharacter() == '&') {
                        new Token("&&", "Logical Operator", currentRow, currentColumn);
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else {
                        new Token("&", "Bitwise Operator", currentRow, currentColumn);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '|':
                    if (nextCharacter() == '|') {
                        new Token("||", "Logical Operator", currentRow, currentColumn);
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else {
                        new Token("|", "Bitwise Operator", currentRow, currentColumn);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '!':
                    if (nextCharacter() == '=') {
                        new Token("!=", "Relational Operator", currentRow, currentColumn);
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else {
                        new Token("!", "Logical Operator", currentRow, currentColumn);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '?':
                    if (nextCharacter() == ':') {
                        new Token("?:", "Conditional Operator", currentRow, currentColumn);
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
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
                    break;

                case '\r':
                    currentCharacter = Code.charAt(++Index);
                    currentColumn++;
                    break;

                case '\n':
                    Index++;
                    if (Index == Code.length()) {
                        i = Code.length();
                        break;
                    }
                    else {
                    currentCharacter = Code.charAt(Index);
                    currentColumn = 1;
                    currentRow++;
                    break;
                    }

                case ' ':
                    currentCharacter = Code.charAt(++Index);
                    currentColumn++;
                    break;

                default:
                    if (currentCharacter >= '0' && currentCharacter <= '9') {
                        int start = currentColumn;
                        String number = readNumber(currentCharacter);
                        new Token(number, "Number", currentRow, start);
                        currentColumn = number.length() + currentColumn;
                        Index+=number.length();
                        currentCharacter = Code.charAt(Index);
                    }
            }
        }
    }

    public static void main(String[] args) {
        lexer ll = new lexer();
        ll.nextToken();
    }
}
