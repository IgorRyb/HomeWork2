package otus.study.cashmachine.bank.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import otus.study.cashmachine.bank.dao.AccountDao;
import otus.study.cashmachine.bank.data.Account;
import otus.study.cashmachine.bank.service.impl.AccountServiceImpl;

import java.math.BigDecimal;
import java.util.regex.Matcher;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    AccountDao accountDao;
    @InjectMocks
    AccountServiceImpl accountServiceImpl;


    @Test
    void createAccountMock() {
        Account expectedAccount = new Account(3, BigDecimal.ONE);
        ArgumentMatcher<Account> matcher = argument -> argument.getAmount().compareTo(expectedAccount.getAmount()) == 0;
        when(accountDao.saveAccount(argThat(matcher))).thenReturn(new Account(-2, BigDecimal.ONE));
        Account account = accountServiceImpl.createAccount(BigDecimal.ONE);
        Assertions.assertEquals(expectedAccount.getAmount(), account.getAmount());
    }

    @Test
    void createAccountCaptor() {
        Account expectedAccount = new Account(3, BigDecimal.ONE);
        ArgumentCaptor<Account> argumentCaptor = ArgumentCaptor.forClass(Account.class);
        when(accountDao.saveAccount(argumentCaptor.capture())).thenReturn(new Account(2, BigDecimal.TEN));
        accountServiceImpl.createAccount(BigDecimal.ONE);
        Assertions.assertEquals(expectedAccount.getAmount(), argumentCaptor.getValue().getAmount());
    }

    @Test
    void addSum() {
        Account testAccount = new Account(1L, BigDecimal.TEN);
          when(accountDao.getAccount(1L)).thenReturn(new Account(1L, BigDecimal.ZERO));
          BigDecimal sum = accountServiceImpl.putMoney(1L, BigDecimal.TEN);
        Assertions.assertEquals(testAccount.getAmount(), sum);
    }

    @Test
    void getSum() {
        Account testAccount = new Account(1L, BigDecimal.TEN);
        when(accountDao.getAccount(1L)).thenReturn(testAccount);
        BigDecimal sum = accountServiceImpl.getMoney(1L, BigDecimal.TEN);
        Assertions.assertEquals(BigDecimal.ZERO, sum);
    }

    @Test
    void getAccount() {
        Account testAccount = new Account(2L, BigDecimal.ONE);
        when(accountDao.getAccount(2L)).thenReturn(testAccount);
        accountServiceImpl.getAccount(2L);
        Assertions.assertEquals(2L, testAccount.getId());
    }

    @Test
    void checkBalance() {
        when(accountDao.getAccount(1L)).thenReturn(new Account(2L, BigDecimal.TEN));
        BigDecimal balance = accountServiceImpl.checkBalance(1L);
        Assertions.assertEquals(BigDecimal.TEN, balance);
    }
}
