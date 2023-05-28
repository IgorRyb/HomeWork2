import java.util.ArrayList;
import java.util.List;

public class Box <T extends Fruit> {

    private List<T> box = new ArrayList<>();

    private T fruit;

    public List<T> getBox() {
        return box;
    }

    public void setBox(List<T> box) {
        this.box = box;
    }

    public double weight() {
        double n = 0;
        for (int i = 0; i < box.size(); i++) {
            n += box.get(i).getWeight();
        }
        return n;
    }

    public boolean compare(Box<?> newFruit) {
        return Math.abs(this.weight() - newFruit.weight()) < 0.001f;
    }

    public void fruitRedistribution(Box<T> newBox) {
        if ((newBox != null) && (box != newBox)) {
            newBox.getBox().addAll(box);
            box.clear();
        }
    }

    public List<T> addFruit(T fruit) {
        box.add(fruit);
        return box;
    }
}
