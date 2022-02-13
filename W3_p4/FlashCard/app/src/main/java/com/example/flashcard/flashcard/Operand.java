package com.example.flashcard.flashcard;

public class Operand {
    private char operator;
    private int num1;
    private int num2;
    private int result;

    public Operand(char operator, int num1, int num2) {
        this.num1 = num1;
        this.num2 = num2;
        this.operator = operator;
        this.result = operator == '+' ? num1 + num2 : num1 - num2;
    }

    public int getResult() {
        return result;
    }

    public int getNum2() {
        return num2;
    }

    public int getNum1() {
        return num1;
    }

    public char getOperator() {
        return operator;
    }
}
