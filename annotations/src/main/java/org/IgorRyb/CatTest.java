package org.IgorRyb;

import org.IgorRyb.Annotations.After;
import org.IgorRyb.Annotations.Before;
import org.IgorRyb.Annotations.Test;

public class CatTest {
    private int numberOfSuccessfulTests = 0;
    private int numberOfFailedTests = 0;

    @Before
    public Cat init() {
        Cat cat = new Cat(0, "");
        return cat;
    }

    @After
    public void clearResources() {
        init().setAge(0);
        init().setName("");
    }

    @Test
    public void validationOfMethodOutputVoice() throws RuntimeException{
        String scenario = "Проверка на вывод требуемого значения метода voice";
        try {
            String expected = "Мяу";
            if (expected == init().voice()) {
                System.out.println("passed: " + scenario);
                numberOfSuccessfulTests += 1;
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            e.printStackTrace();
            numberOfFailedTests += 1;
        }
    }

    @Test
    public void ageIncreaseCheck() {
        String scenario = "Проверка увеличения возраста на один";
        try {
            int expected = 1;
            if (expected == init().increaseAgeByOne()) {
                System.out.println("passed: " + scenario);
                numberOfSuccessfulTests += 1;
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            e.printStackTrace();
            numberOfFailedTests += 1;
        }
    }

}
