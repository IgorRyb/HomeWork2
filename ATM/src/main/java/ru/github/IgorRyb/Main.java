package ru.github.IgorRyb;

import ru.github.IgorRyb.service.MoneyBoxService;
import ru.github.IgorRyb.service.impl.MoneyBoxServiceImpl;

public class Main {
    public static void main(String[] args) {
        CashMachine cash = new CashMachine(1, 1, 1, 1);
        MoneyBoxService moneyBox = new MoneyBoxServiceImpl();
        moneyBox.getNotes(cash, 5865);
        moneyBox.restOfMoney(cash);
    }
}