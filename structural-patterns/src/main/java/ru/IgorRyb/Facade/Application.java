package ru.IgorRyb.Facade;

public class Application {
    public static void main(String[] args) {
        Facade facade = new Facade();
        facade.sendMessage("mail");
        facade.sendMessage("push");
        facade.sendMessage("SMS");
    }
}
