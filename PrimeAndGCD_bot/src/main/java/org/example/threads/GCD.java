package org.example.threads;

public class GCD implements Runnable{

    private final int a;
    private final int b;
    private int gcd;

    public GCD(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public void run() {
        gcd = findGCD(a, b);
    }

    private int findGCD(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public int getGCD() {
        return gcd;
    }
}