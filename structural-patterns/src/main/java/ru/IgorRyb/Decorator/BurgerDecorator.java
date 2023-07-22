package ru.IgorRyb.Decorator;

public class BurgerDecorator implements OrderBurger {

    protected OrderBurger orderBurger;

    public BurgerDecorator(OrderBurger orderBurger) {
        this.orderBurger = orderBurger;
    }

    @Override
    public int getCutlet() {
        return orderBurger.getCutlet() + 1;
    }

    @Override
    public int getСucumbers() {
        return orderBurger.getСucumbers() + 1;
    }

    @Override
    public int numberOfServings() {
        return orderBurger.numberOfServings() + 1;
    }

    @Override
    public void doOrder() {
        System.out.println("burger serving \nIngredients: cutlet: " + getCutlet() + ", cucumbers: " + getСucumbers() + ", number of servings: " + numberOfServings());
    }

}
