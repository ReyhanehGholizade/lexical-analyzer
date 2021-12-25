package com.example;

public class Token {
    String token;
    String type;
    int row;
    int column;
    int blockNumber;


    public Token(String token, String type, int row, int column, int blockNumber) {
        this.token = token;
        this.type = type;
        this.row = row;
        this.column = column;
        this.blockNumber = blockNumber;

        System.out.println(this.token + " | " + this.type + " | " + this.row + " | " + this.column + " | " + this.blockNumber);
    }
}
