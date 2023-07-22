package ru.IgorRyb.Facade;

public class SmsMessage implements Messages {

    @Override
    public void sendMessage() {
        System.out.println("Send SMS message...");
    }
}
