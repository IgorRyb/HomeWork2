package ru.IgorRyb.Decorator;

public class Application {
    public static void main(String[] args) {
        OrderBurger order = new OrderBurgerImpl();
        order.doOrder();
        order = new BurgerDecorator(order);
        order.getCutlet();
        order.getСucumbers();
        order.numberOfServings();
        order.doOrder();
    }
}
