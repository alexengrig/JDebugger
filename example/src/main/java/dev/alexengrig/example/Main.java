package dev.alexengrig.example;

import dev.alexengrig.example.exception.CaughtExampleException;
import dev.alexengrig.example.exception.UncaughtExampleException;

public class Main {
    public static void main(String[] args) {
        int one = 1;
        int two = 2;
        int three = 3;
        int four = 4;
        int fife = 5;
        int size = 6;
        int seven = 7;
        int eight = 8;
        int nine = 9;
        int ten = 10;
        try {
            throw new CaughtExampleException();
        } catch (CaughtExampleException ignore) {
        }
        throwUncaughtException();
        System.out.println("END!");
    }

    private static void throwUncaughtException() {
        throw new UncaughtExampleException();
    }
}
