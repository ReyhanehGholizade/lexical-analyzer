package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class lexer {
    int currentRow;
    int currentColumn = 0;
    char currentChar;


    ArrayList<String> keywords = new ArrayList<>(
            Arrays.asList("int", "long", "register", "return", "short", "signed",	"sizeof", "static",
                    "struct", "switch",	"typedef", "union", "unsigned", "void", "volatile",	"while",
                    "double", "else", "enum", "extern", "float", "for",	"goto",	"if", "default",
                    "auto", "break", "case", "char", "do", "continue", "volatile"));

    public 

    public void nextToken() {

        File file = new File("input.txt");
        // Create the File Reader object
        FileReader fr;

            try {
                fr = new FileReader(file);

                // Create the BufferedReader object
                BufferedReader br = new BufferedReader(fr);
                int c = 0;
                // Read character by character
                while ((c = br.read()) != -1) {
                    currentColumn++;
                    // convert the integer to char
                    char ch = (char) c;

                    switch (ch) {

                        case '+':
                            char x = (char) br.read();
                            if (x == '+') {
                                new Token("++", "Increment Operator", currentRow, currentColumn);
                                currentColumn++;
                            } else if (x == '=') {
                                new Token("+=", "Assignment Operator", currentRow, currentColumn);
                                currentColumn++;
                            } else {
                                new Token("+", "Arithmetic Operator", currentRow, currentColumn);
                                currentColumn++;
                            }
                            break;

                        case '-':
                            if (br.read() == '-') {
                                new Token("--", "Decrement Operator", currentRow, currentColumn);
                                currentColumn++;
                            }
                            else if (br.read() == '=') {
                                new Token("-=", "Assignment Operator", currentRow, currentColumn);
                                currentColumn++;
                            }
                            else {
                                new Token("-", "Arithmetic Operator", currentRow, currentColumn);
                                currentColumn++;
                            }

                        case '<':
                            if (br.read() == '=') {
                                new Token("<=", "Relational Operator", currentRow, currentColumn);
                                currentColumn++;
                            }
                            else if (br.read() == '<') {
                                new Token("<<", "Bitwise Operator", currentRow, currentColumn);
                                currentColumn++;
                            }
                            else {
                                new Token("<", "Relational Operator", currentRow, currentColumn);
                                currentColumn++;
                            }

                        case '>':
                            if (br.read() == '=') {
                                new Token(">=", "Relational Operator", currentRow, currentColumn);
                                currentColumn++;
                            }
                            else if (br.read() == '>') {
                                new Token(">>", "Bitwise Operator", currentRow, currentColumn);
                                currentColumn++;
                            }
                            else {
                                new Token(">", "Relational Operator", currentRow, currentColumn);
                                currentColumn++;
                            }

                        if(ch == '*') {
                            if (br.read() == '=') {
                                new Token("*=", "Assignment Operator", currentRow, currentColumn);
                                currentColumn++;
                            }
                            else {
                                new Token("*", "Arithmetic Operator", currentRow, currentColumn);
                                currentColumn++;
                            }
                        }

                        if (ch == '/') {
                            if (br.read() == '=') {
                                new Token("/=", "Assignment Operator", currentRow, currentColumn);
                                currentColumn++;
                            }
                            else {
                                new Token("/", "Arithmetic Operator", currentRow, currentColumn);
                                currentColumn++;
                            }
                        }

                        if (ch == '%') {
                            if (br.read() == '=') {
                                new Token("%=", "Assignment Operator", currentRow, currentColumn);
                                currentColumn++;
                            }
                            else {
                                new Token("%", "Arithmetic Operator", currentRow, currentColumn);
                                currentColumn++;
                            }
                        }

                        if (ch == '=') {
                            if (br.read() == '=') {
                                new Token("==", "Relational Operator", currentRow, currentColumn);
                                currentColumn++;
                            }
                            else {
                                new Token("=", "Assignment Operator", currentRow, currentColumn);
                                currentColumn++;
                            }
                        }

                        if (ch == '&') {
                            if (br.read() == '&') {
                                new Token("&&", "Logical Operator", currentRow, currentColumn);
                                currentColumn++;
                            }
                            else {
                                new Token("&", "Bitwise Operator", currentRow, currentColumn);
                                currentColumn++;
                            }
                        }

                        if (ch =='|') {
                            if (br.read() == '|') {
                                new Token("||", "Logical Operator", currentRow, currentColumn);
                                currentColumn++;
                            }
                            else {
                                new Token("|", "Bitwise Operator", currentRow, currentColumn);
                                currentColumn++;
                            }
                        }

                        if (ch == '!') {
                            if (br.read() == '=') {
                                new Token("!=", "Relational Operator", currentRow, currentColumn);
                                currentColumn++;
                            }
                            else {
                                new Token("!", "Logical Operator", currentRow, currentColumn);
                                currentColumn++;
                            }
                        }

                        if (ch == '?') {
                            if (br.read() == ':') {
                                new Token("?:", "Conditional Operator", currentRow, currentColumn);
                                currentColumn++;
                            }
                        }

                        if (ch == '~') {
                            new Token("~", "Bitwise Operator", currentRow, currentColumn);
                            currentColumn++;
                        }

                        if (ch == '^')
                            new Token("^", "Bitwise Operator", currentRow, currentColumn);
                            currentColumn++;

                        case ';':
                            new Token(";", "Delimiter", currentRow, currentColumn);
                            currentColumn++;

                        case '\n':
                            currentColumn = 0;
                            currentRow++;


                        default:
                            if (ch > '0' && ch < '9') {
                                int start = currentColumn;
                                StringBuilder word = new StringBuilder();
                                word.append(ch);
                                char character = 0;
                                while (character != ' ') {
                                    character = (char) br.read();
                                    if (character > 0 && character < 9) {
                                        word.append(character);
                                    }
                                    else {
                                        String number = word.toString();
                                        new Token(number, "Number", currentRow, start);
                                    }
                                }

                            }

                            if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
                                int startWord = currentColumn;
                                StringBuilder word = new StringBuilder();
                                word.append(ch);
                                char character = (char) br.read();
                                if ((character >= 'a' && character <= 'z') || (character >= 'A' && character <= 'Z')) {

                                }


                            }
                    }
                }

            }

            catch (IOException e) {
                e.printStackTrace();
            }

    }


    public static void main(String[] args) {

    }


}










