package com.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class lexer {

    int currentRow = 1;
    int currentColumn = 1;
    char currentCharacter;
    int currentBlockNo = 0;
    String Code;
    int Index = 0;

    boolean flag;
    StringBuilder string = new StringBuilder();

    DefaultTableModel model = new DefaultTableModel();
    JTable table = new JTable(model);

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

        model.addColumn("Token");
        model.addColumn("Token_Type");
        model.addColumn("Block_Number");
        model.addColumn("Row");
        model.addColumn("Column");
    }

    public void table() {
        JFrame f = new JFrame();
        JPanel panel = new JPanel();
        table.setGridColor(Color.black);
        table.setRowSelectionAllowed(true);
        table.setShowGrid(true);
        table.setBackground(Color.darkGray);
        table.setRowHeight(24);
        table.setFont(new Font("Fox and Bower", Font.BOLD, 13));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setForeground(Color.white);
        panel.add(new JScrollPane(table));
        f.add(panel);
        f.setSize(700, 470);
        f.add(new JScrollPane(table));
        f.setVisible(true);
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
            if ((Code.charAt(i) <= '9' && Code.charAt(i) >= '0') || (Code.charAt(i) == '.')) {
                if (Code.charAt(i) == '.'){
                    flag = true;
                }
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

    //finds all tokens
    public void findTokens() {
        int i = 0;
        while (i != Code.length()) {
            i++;
            switch (currentCharacter) {

                case '+':
                    if (peekNextCharacter() == '+') {
                        new Token("++", "Increment Operator", currentRow, currentColumn,currentBlockNo);
                        model.addRow(new Object[]{"++", "Increment Operator", currentBlockNo,currentRow,currentColumn});
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else if (peekNextCharacter() == '=') {
                        new Token("+=", "Assignment Operator", currentRow, currentColumn,currentBlockNo);
                        model.addRow(new Object[]{"+=", "Assignment Operator", currentBlockNo,currentRow,currentColumn});
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else {
                        new Token("+", "Arithmetic Operator", currentRow, currentColumn,currentBlockNo);
                        model.addRow(new Object[]{"+", "Arithmetic Operator", currentBlockNo,currentRow,currentColumn});
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '-':
                    if (peekNextCharacter() == '-') {
                        new Token("--", "Decrement Operator", currentRow, currentColumn,currentBlockNo);
                        model.addRow(new Object[]{"--", "Decrement Operator", currentBlockNo,currentRow,currentColumn});
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else if (peekNextCharacter() == '=') {
                        new Token("-=", "Assignment Operator", currentRow, currentColumn,currentBlockNo);
                        model.addRow(new Object[]{"-=", "Assignment Operator", currentBlockNo,currentRow,currentColumn});
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else {
                        new Token("-", "Arithmetic Operator", currentRow, currentColumn,currentBlockNo);
                        model.addRow(new Object[]{"-", "Arithmetic Operator", currentBlockNo,currentRow,currentColumn});
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '<':
                    if (peekNextCharacter() == '=') {
                        new Token("<=", "Relational Operator", currentRow, currentColumn,currentBlockNo);
                        model.addRow(new Object[]{"<=", "Relational Operator", currentBlockNo,currentRow,currentColumn});
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else if (peekNextCharacter() == '<') {
                        new Token("<<", "Bitwise Operator", currentRow, currentColumn,currentBlockNo);
                        model.addRow(new Object[]{"<<", "Bitwise Operator", currentBlockNo,currentRow,currentColumn});
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else {
                        new Token("<", "Relational Operator", currentRow, currentColumn,currentBlockNo);
                        model.addRow(new Object[]{"<", "Relational Operator", currentBlockNo,currentRow,currentColumn});
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '>':
                    if (peekNextCharacter() == '=') {
                        new Token(">=", "Relational Operator", currentRow, currentColumn,currentBlockNo);
                        model.addRow(new Object[]{">=", "Relational Operator", currentBlockNo,currentRow,currentColumn});
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else if (peekNextCharacter() == '>') {
                        new Token(">>", "Bitwise Operator", currentRow, currentColumn,currentBlockNo);
                        model.addRow(new Object[]{">>", "Relational Operator", currentBlockNo,currentRow,currentColumn});
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else {
                        new Token(">", "Relational Operator", currentRow, currentColumn,currentBlockNo);
                        model.addRow(new Object[]{">", "Relational Operator", currentBlockNo,currentRow,currentColumn});
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '{':
                    new Token("{", "Bracket", currentRow, currentColumn,currentBlockNo);
                    model.addRow(new Object[]{"{", "Bracket", currentBlockNo,currentRow,currentColumn});
                    currentCharacter = Code.charAt(++Index);
                    currentColumn++;
                    currentBlockNo++;
                    break;

                case '}':
                    new Token("}", "Bracket", currentRow, currentColumn,currentBlockNo);
                    model.addRow(new Object[]{"}", "Bracket", currentBlockNo,currentRow,currentColumn});
                    currentCharacter = Code.charAt(++Index);
                    currentColumn++;
                    currentBlockNo--;
                    break;

                case '*':
                    if (peekNextCharacter() == '=') {
                        new Token("*=", "Assignment Operator", currentRow, currentColumn,currentBlockNo);
                        model.addRow(new Object[]{"*=", "Assignment Operator", currentBlockNo,currentRow,currentColumn});
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else {
                        new Token("*", "Arithmetic Operator", currentRow, currentColumn,currentBlockNo);
                        model.addRow(new Object[]{"*", "Arithmetic Operator", currentBlockNo,currentRow,currentColumn});
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '/':
                    if (peekNextCharacter() == '=') {
                        new Token("/=", "Assignment Operator", currentRow, currentColumn,currentBlockNo);
                        model.addRow(new Object[]{"/=", "Assignment Operator", currentBlockNo,currentRow,currentColumn});
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
                        model.addRow(new Object[]{"/", "Arithmetic Operator", currentBlockNo,currentRow,currentColumn});
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '%':
                    if (peekNextCharacter() == '=') {
                        new Token("%=", "Assignment Operator", currentRow, currentColumn,currentBlockNo);
                        model.addRow(new Object[]{"%=", "Assignment Operator", currentBlockNo,currentRow,currentColumn});
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else {
                        new Token("%", "Arithmetic Operator", currentRow, currentColumn,currentBlockNo);
                        model.addRow(new Object[]{"%", "Arithmetic Operator", currentBlockNo,currentRow,currentColumn});
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '=':
                    if (peekNextCharacter() == '=') {
                        new Token("==", "Relational Operator", currentRow, currentColumn,currentBlockNo);
                        model.addRow(new Object[]{"==", "Relational Operator", currentBlockNo,currentRow,currentColumn});
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else {
                        new Token("=", "Assignment Operator", currentRow, currentColumn,currentBlockNo);
                        model.addRow(new Object[]{"=", "Assignment Operator", currentBlockNo,currentRow,currentColumn});
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '&':
                    if (peekNextCharacter() == '&') {
                        new Token("&&", "Logical Operator", currentRow, currentColumn,currentBlockNo);
                        model.addRow(new Object[]{"&&", "Logical Operator", currentBlockNo,currentRow,currentColumn});
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else {
                        new Token("&", "Bitwise Operator", currentRow, currentColumn,currentBlockNo);
                        model.addRow(new Object[]{"&", "Bitwise Operator", currentBlockNo,currentRow,currentColumn});
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '|':
                    if (peekNextCharacter() == '|') {
                        new Token("||", "Logical Operator", currentRow, currentColumn,currentBlockNo);
                        model.addRow(new Object[]{"||", "Logical Operator", currentBlockNo,currentRow,currentColumn});
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else {
                        new Token("|", "Bitwise Operator", currentRow, currentColumn,currentBlockNo);
                        model.addRow(new Object[]{"|", "Bitwise Operator", currentBlockNo,currentRow,currentColumn});
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                case '!':
                    if (peekNextCharacter() == '=') {
                        new Token("!=", "Relational Operator", currentRow, currentColumn,currentBlockNo);
                        model.addRow(new Object[]{"!=", "Relational Operator", currentBlockNo,currentRow,currentColumn});
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    } else {
                        new Token("!", "Logical Operator", currentRow, currentColumn,currentBlockNo);
                        model.addRow(new Object[]{"!", "Logical Operator", currentBlockNo,currentRow,currentColumn});
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    break;

                //if a identifier starts with _
                case '_':
                    if (isLetter(Code.indexOf(peekNextCharacter())) || isNumber(Code.indexOf(peekNextCharacter()))) {
                        int start = currentColumn;
                        String word = readWord();
                        new Token(word, "Identifier", currentRow, start,currentBlockNo);
                        model.addRow(new Object[]{word, "Identifier", currentBlockNo,currentRow,start});
                        currentColumn = word.length() + currentColumn;
                        Index+=word.length();
                        currentCharacter = Code.charAt(Index);
                    }
                    break;

                case '"':
                    currentCharacter = Code.charAt(++Index);
                    currentColumn++;
                    int Start = currentColumn;
                    while (currentCharacter != '"') {
                        string.append(currentCharacter);
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }
                    currentCharacter = Code.charAt(++Index);
                    currentColumn++;
                    new Token(string.toString(), "String", currentRow, Start,currentBlockNo);
                    model.addRow(new Object[]{string.toString(), "String", currentBlockNo,currentRow,Start});
                    break;

                case '(':
                    new Token("(", "Bracket", currentRow, currentColumn,currentBlockNo);
                    model.addRow(new Object[]{"(", "Bracket", currentBlockNo,currentRow,currentColumn});
                    currentCharacter = Code.charAt(++Index);
                    currentColumn++;
                    break;

                case ')':
                    new Token(")", "Bracket", currentRow, currentColumn,currentBlockNo);
                    model.addRow(new Object[]{")", "Bracket", currentBlockNo,currentRow,currentColumn});
                    currentCharacter = Code.charAt(++Index);
                    currentColumn++;
                    break;

                case '[':
                    new Token("[", "Bracket", currentRow, currentColumn,currentBlockNo);
                    model.addRow(new Object[]{"[", "Bracket", currentBlockNo,currentRow,currentColumn});
                    currentCharacter = Code.charAt(++Index);
                    currentColumn++;
                    break;

                case ']':
                    new Token("]", "Bracket", currentRow, currentColumn,currentBlockNo);
                    model.addRow(new Object[]{"]", "Bracket", currentBlockNo,currentRow,currentColumn});
                    currentCharacter = Code.charAt(++Index);
                    currentColumn++;
                    break;

                case '?':
                    if (peekNextCharacter() == ':') {
                        new Token("?:", "Conditional Operator", currentRow, currentColumn,currentBlockNo);
                        model.addRow(new Object[]{"?:", "Conditional Operator", currentBlockNo,currentRow,currentColumn});
                        Index+=2;
                        currentCharacter = Code.charAt(Index);
                        currentColumn+=2;
                    }
                    break;

                case '~':
                    new Token("~", "Bitwise Operator", currentRow, currentColumn,currentBlockNo);
                    model.addRow(new Object[]{"~", "Bitwise Operator", currentBlockNo,currentRow,currentColumn});
                    currentCharacter = Code.charAt(++Index);
                    currentColumn++;
                    break;

                case '^':
                    new Token("^", "Bitwise Operator", currentRow, currentColumn,currentBlockNo);
                    model.addRow(new Object[]{"^", "Bitwise Operator", currentBlockNo,currentRow,currentColumn});
                    currentCharacter = Code.charAt(++Index);
                    currentColumn++;
                    break;

                case ',':
                    new Token(",", "Delimiter", currentRow, currentColumn,currentBlockNo);
                    model.addRow(new Object[]{",", "Delimiter", currentBlockNo,currentRow,currentColumn});
                    currentCharacter = Code.charAt(++Index);
                    currentColumn++;
                    break;

                case ':':
                    new Token(":", "Delimiter", currentRow, currentColumn,currentBlockNo);
                    model.addRow(new Object[]{":", "Delimiter", currentBlockNo,currentRow,currentColumn});
                    currentCharacter = Code.charAt(++Index);
                    currentColumn++;
                    break;

                case ';':
                    new Token(";", "Delimiter", currentRow, currentColumn,currentBlockNo);
                    model.addRow(new Object[]{";", "Delimiter", currentBlockNo,currentRow,currentColumn});
                    currentCharacter = Code.charAt(++Index);
                    currentColumn++;
                    break;

                case '#':
                    currentCharacter = Code.charAt(++Index);
                    currentColumn++;
                    while (currentCharacter != '\r') {
                        currentCharacter = Code.charAt(++Index);
                        currentColumn++;
                    }

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
                        if (flag)
                            model.addRow(new Object[]{number, "Decimal Number", currentBlockNo,currentRow,start});
                        else model.addRow(new Object[]{number, "Number", currentBlockNo,currentRow,start});

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
                            model.addRow(new Object[]{word, "Keyword", currentBlockNo,currentRow,start});
                            currentColumn = word.length() + currentColumn;
                            Index+=word.length();
                            currentCharacter = Code.charAt(Index);
                        }
                        else {
                            new Token(word, "Identifier", currentRow, start,currentBlockNo);
                            model.addRow(new Object[]{word, "Identifier", currentBlockNo,currentRow,start});
                            currentColumn = word.length() + currentColumn;
                            Index+=word.length();
                            currentCharacter = Code.charAt(Index);
                        }
                    }

            }
        }
    }
}
