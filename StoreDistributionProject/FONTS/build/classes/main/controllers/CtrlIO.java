package main.controllers;

import java.util.Scanner;

public class CtrlIO {
    private static final Scanner scanner = new Scanner(System.in);

    public String readLine() {
        return scanner.nextLine();
    }
    public int readInt() {
        return scanner.nextInt();
    }
    public double readDouble() {
        return scanner.nextDouble();
    }
    public String readString() {
        return scanner.next();
    }
    public void closeScanner() {
        scanner.close();
    }
}
