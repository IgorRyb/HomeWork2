import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.github.IgorRyb.CashMachine;
import ru.github.IgorRyb.service.MoneyBoxService;
import ru.github.IgorRyb.service.impl.MoneyBoxServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MoneyBoxServiceTest {

    private MoneyBoxService moneyBoxService;
    private CashMachine cash;

    @BeforeEach
    void init() {
        cash = new CashMachine(0, 0, 0, 0);
        moneyBoxService = new MoneyBoxServiceImpl();
    }

    @Test
    void testAddNotes() {
        int expected = 5000 + 1000 + 500 + 100;
        moneyBoxService.putCash(cash, 1,1,1,1);
        Assertions.assertEquals(expected, moneyBoxService.checkSum(cash));
    }

    @Test
    void testCheckSum() {
        int expected = 0;
        int actual = moneyBoxService.checkSum(cash);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testSumIsMoreThanBalance() {
        Exception thrown = assertThrows(IllegalStateException.class, () -> {
            moneyBoxService.getNotes(cash, 6600);
        });
        Assertions.assertEquals("Too big request", thrown.getMessage());
    }

    @Test
    void testGetMoneyAndRemainder() {
        cash = new CashMachine(100, 100, 100, 100);
        List<Integer> expected = moneyBoxService.getNotes(cash, 6600);
        Assertions.assertEquals(6600, expected.get(0) * 5000 + expected.get(1) * 1000
                + expected.get(2) * 500 + expected.get(3) * 100);
    }


    @Test
    void testRemainder() {
        moneyBoxService.putCash(cash, 1,1,1,1);
        moneyBoxService = new MoneyBoxServiceImpl();
        moneyBoxService.getNotes(cash, 6500);
        Assertions.assertEquals(100, moneyBoxService.restOfMoney(cash));
    }
}
