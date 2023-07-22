package ru.IgorRyb.Facade;

public class PushNotification implements Messages {
    @Override
    public void sendMessage() {
        System.out.println("Send a message from push notification...");
    }
}
