package com.example;

public class Token {
    String token;
    String type;
    int row;
    int column;


    public Token(String token, String type, int row, int column) {
        this.token = token;
        this.type = type;
        this.row = row;
        this.column = column;

        System.out.println(this.token + " | " + this.type + " | " + this.row + " | " + this.column);
    }
}
