import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Box<Apple> firstAppleBox = new Box<>();
        firstAppleBox.addFruit(5, new Apple());
        Box<Orange> orangeBox = new Box<>();
        orangeBox.addFruit(4, new Orange());
        System.out.println(firstAppleBox.weight());
        System.out.println(firstAppleBox.compare(orangeBox));
        Box<Apple> secondAppleBox = new Box<>();
        secondAppleBox.addFruit(6, new Apple());
        System.out.println(firstAppleBox.fruitRedistribution(secondAppleBox).getBox().size());
        System.out.println(firstAppleBox.getBox().size());
    }
}
