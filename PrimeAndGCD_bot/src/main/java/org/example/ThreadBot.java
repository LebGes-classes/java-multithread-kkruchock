package org.example;

import org.example.threads.GCD;
import org.example.threads.PrimeCheck;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class ThreadBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (messageText.matches("\\d+ \\d+")) {
                String[] parts = messageText.split(" ");
                int a = Integer.parseInt(parts[0]);
                int b = Integer.parseInt(parts[1]);

                PrimeCheck task1 = new PrimeCheck(a);
                PrimeCheck task2 = new PrimeCheck(b);
                GCD gcdTask = new GCD(a, b);

                Thread thread1 = new Thread(task1);
                Thread thread2 = new Thread(task2);
                Thread thread3 = new Thread(gcdTask);

                thread1.start();
                thread2.start();
                thread3.start();

                try {

                    thread1.join();
                    thread2.join();
                    thread3.join();

                    String response = String.format(
                            "Результаты:\n" +
                                    "%d — %s\n" +
                                    "%d — %s\n" +
                                    "НОД: %d",
                            a, task1.isPrime() ? "простое" : "составное",
                            b, task2.isPrime() ? "простое" : "составное",
                            gcdTask.getGCD()
                    );

                    sendMessage(chatId, response);
                } catch (InterruptedException e) {
                    sendMessage(chatId, "Ошибка вычислений!");
                }
            } else {
                sendMessage(chatId, "Отправьте два числа через пробел, например: 15 20");
            }
        }

    }

    @Override
    public String getBotUsername() {
        return "PrimeAndGCD";
    }

    @Override
    public String getBotToken() {
        return "7875045104:AAHRYPKzvTmNjI-OaWa0RVkcgF1UOitr59g";
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
