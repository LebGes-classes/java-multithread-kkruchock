package org.example.threads;

public class PrimeCheck implements Runnable {

    private final int number;
    private boolean isPrime;

    public PrimeCheck(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        primeCheck(number);
    }

    private void primeCheck(int number) {
        isPrime = number > 1;
        for (int i = 2; i <= Math.sqrt(number + 1) && isPrime; i++) {
            if (number % i == 0) {
                isPrime = false;
            }
        }
    }

    public boolean isPrime() {
        return isPrime;
    }
}
