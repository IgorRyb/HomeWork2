import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Box <T extends Fruit> {

    private ArrayList<T> box = new ArrayList<>();

    public ArrayList<T> getBox() {
        return box;
    }

    public void setBox(ArrayList<T> box) {
        this.box = box;
    }

    public double weight() {
        double weightOfOneFruit = 4.5;
        return box.size() * weightOfOneFruit;
    }

    public boolean compare(Box<?> newFruit) {
        return Math.abs(this.weight() - newFruit.weight()) < 0.001f;
    }

    public Box<T> fruitRedistribution(Box<T> newBox) {
        if (box != null) {
            newBox.getBox().addAll(box);
            box.clear();
        }
        return newBox;
    }

    public List<T> addFruit(int n, T fruit) {
        for (int i = 0; i < n; i++) {
            box.add(fruit);
        }
        return box;
    }
}
