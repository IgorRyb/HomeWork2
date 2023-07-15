package ru.igorryb.fruitbox;

import ru.igorryb.fruitbox.fruits.Apple;
import ru.igorryb.fruitbox.fruits.Orange;

public class Main {

    public static void main(String[] args) {
        Box<Apple> firstAppleBox = new Box<>();
        for (int i = 0; i < 4; i++) {
            firstAppleBox.addFruit(new Apple(4));
        }
        Box<Orange> orangeBox = new Box<>();
        for (int i = 0; i < 2; i++) {
            orangeBox.addFruit(new Orange(8));
        }
        System.out.println(firstAppleBox.weight());
        System.out.println(firstAppleBox.compare(orangeBox));
        Box<Apple> secondAppleBox = new Box<>();
        for (int i = 0; i < 6; i++) {
            secondAppleBox.addFruit(new Apple(6));
        }
        firstAppleBox.fruitRedistribution(secondAppleBox);
        System.out.println(secondAppleBox.getBox().size());
    }
}
