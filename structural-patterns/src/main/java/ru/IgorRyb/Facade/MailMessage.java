package ru.IgorRyb.Facade;

public class MailMessage implements Messages {

    @Override
    public void sendMessage() {
        System.out.println("Send message on Email...");
    }
}
