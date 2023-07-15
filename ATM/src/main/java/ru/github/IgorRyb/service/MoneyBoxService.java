package ru.github.IgorRyb.service;

import ru.github.IgorRyb.CashMachine;

import java.util.List;

public interface MoneyBoxService {

    int checkSum(CashMachine cash);

    List<Integer> getNotes(CashMachine cash, int sum);

    int restOfMoney(CashMachine cash);

    void putCash(CashMachine cash, int note100, int note500, int note1000, int note5000);
}
