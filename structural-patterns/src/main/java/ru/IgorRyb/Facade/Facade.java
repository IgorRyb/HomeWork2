package ru.IgorRyb.Facade;

public class Facade {

    Messages messages;

    public void sendMessage(String path) {
        switch (path) {
            case ("mail"):
                messages = new MailMessage();
                messages.sendMessage();
                break;
            case ("SMS"):
                messages = new SmsMessage();
                messages.sendMessage();
                break;
            case ("push"):
                messages = new PushNotification();
                messages.sendMessage();
        }
    }
}

