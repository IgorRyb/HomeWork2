package ru.github.IgorRyb.service.impl;

import ru.github.IgorRyb.CashMachine;
import ru.github.IgorRyb.service.MoneyBoxService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MoneyBoxServiceImpl implements MoneyBoxService {

    private int amountAfterDeduction;
    List<Integer> result = new ArrayList<>(Arrays.asList(0, 0, 0, 0));

    @Override
    public int checkSum(CashMachine cash) {
        return cash.getNote100() * 100 + cash.getNote500() * 500 + cash.getNote1000() * 1000 + cash.getNote5000() * 5000;
    }

    @Override
    public void putCash(CashMachine cash, int note100, int note500, int note1000, int note5000) {
        if (cash != null) {
            cash.setNote100(note100 + cash.getNote100());
            cash.setNote500(note500 + cash.getNote500());
            cash.setNote1000(note1000 + cash.getNote1000());
            cash.setNote5000(note5000 + cash.getNote5000());
        } else {
            throw new IllegalStateException("Cash machine is empty");
        }
    }

    @Override
    public List<Integer> getNotes(CashMachine cash, int sum) {

        if (sum > checkSum(cash)) {
            throw new IllegalStateException("Too big request");
        }

        int chargedNotes = 0;
        int required = 0;

        if (sum >= 5000) {
            required = sum / 5000;
            if (required <= cash.getNote5000()) {
                chargedNotes = required;
            }
            sum -= chargedNotes * 5000;
            result.set(0, chargedNotes);
        }

        if (sum >= 1000) {
            required = sum / 1000;
            if (required <= cash.getNote1000()) {
                chargedNotes = required;
            }
            sum -= chargedNotes * 1000;
            result.set(1, chargedNotes);
        }

        if (sum >= 500) {
            required = sum / 500;
            if (required <= cash.getNote500()) {
                chargedNotes = required;
            }
            sum -= chargedNotes * 500;
            result.set(2, chargedNotes);
        }

        if (sum >= 100) {
            required = sum / 100;
            if (required <= cash.getNote100()) {
                chargedNotes = required;
            }
            sum -= chargedNotes * 100;
            result.set(3, chargedNotes);
        }

        amountAfterDeduction = sum;

        cash.setNote5000(cash.getNote5000() - result.get(0));
        cash.setNote1000(cash.getNote1000() - result.get(1));
        cash.setNote500(cash.getNote500() - result.get(2));
        cash.setNote100(cash.getNote100() - result.get(3));

        return result;
    }

    @Override
    public int restOfMoney(CashMachine cash) {
        int remainder = checkSum(cash) - amountAfterDeduction;
        return remainder;
    }


}
