package ru.IgorRyb.Decorator;

public class OrderBurgerImpl implements OrderBurger {

    public void doOrder() {
        System.out.println("burger serving \nIngredients: cutlet: " + getCutlet() + ", cucumbers: " + getСucumbers() + ", number of servings: " + numberOfServings());
    }

    @Override
    public int getCutlet() {
        return 1;
    }

    @Override
    public int getСucumbers() {
        return 1;
    }

    @Override
    public int numberOfServings() {
        return 1;
    }
}
