package org.example.service.impl;




import lombok.RequiredArgsConstructor;
import org.example.dao.AccountDao;
import org.example.data.Account;
import org.example.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountDao accountDao;

    @Override
    public Account createAccount(BigDecimal amount) {
        Account newAccount = new Account(0, amount);
        return accountDao.saveAccount(newAccount);
    }

    @Override
    public Account getAccount(Long id) {
        return accountDao.getAccount(id);
    }

    @Override
    public BigDecimal getMoney(Long id, BigDecimal amount) {
        Account account = accountDao.getAccount(id);
        if (account.getAmount().subtract(amount).doubleValue() < 0) {
            throw new IllegalArgumentException("Not enough money");
        }
        account.setAmount(account.getAmount().subtract(amount));
        return account.getAmount();
    }

    @Override
    public BigDecimal putMoney(Long id, BigDecimal amount) {
        Account account = accountDao.getAccount(id);
        account.setAmount(account.getAmount().add(amount));
        return account.getAmount();
    }

    @Override
    public BigDecimal checkBalance(Long id) {
        Account account = accountDao.getAccount(id);
        return account.getAmount();
    }
}