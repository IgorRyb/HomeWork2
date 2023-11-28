package org.example.machine.service.impl;


import lombok.RequiredArgsConstructor;
import org.example.machine.data.CashMachine;
import org.example.machine.service.CashMachineService;
import org.example.machine.service.MoneyBoxService;
import org.example.service.AccountService;
import org.example.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CashMachineServiceImpl implements CashMachineService {

    private final CardService cardService;

    private final AccountService accountService;

    private final MoneyBoxService moneyBoxService;

    @Override
    public List<Integer> getMoney(CashMachine machine, String cardNum, String pin, BigDecimal amount) {
        try {
            BigDecimal sum = cardService.getMoney(cardNum, pin, amount);
            return moneyBoxService.getMoney(machine.getMoneyBox(), amount.intValue());
        } catch (Exception e) {
            cardService.putMoney(cardNum, pin, amount);
            throw new RuntimeException(e);
        }
    }

    @Override
    public BigDecimal putMoney(CashMachine machine, String cardNum, String pin, List<Integer> notes) {
        cardService.getBalance(cardNum, pin);

        List<Integer> arrangedNotes = new ArrayList<>(notes);
        for (int i = 0; i < 4 - arrangedNotes.size(); i ++) {
            arrangedNotes.add(0);
        }

        moneyBoxService.putMoney(machine.getMoneyBox(), arrangedNotes.get(3), arrangedNotes.get(2), arrangedNotes.get(1), arrangedNotes.get(0));
        return cardService.putMoney(cardNum, pin, new BigDecimal(
                arrangedNotes.get(3) * 100 +
                    arrangedNotes.get(2) * 500 +
                    arrangedNotes.get(1) * 1000 +
                    arrangedNotes.get(0) * 5000
        ));
    }

    @Override
    public BigDecimal checkBalance(CashMachine machine, String cardNum, String pin) {
        return cardService.getBalance(cardNum, pin);
    }

    @Override
    public boolean changePin(String cardNum, String oldPin, String newPin) {
        return cardService.cnangePin(cardNum, oldPin, newPin);
    }
}