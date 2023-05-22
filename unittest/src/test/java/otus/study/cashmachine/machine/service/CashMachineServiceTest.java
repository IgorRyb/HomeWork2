package otus.study.cashmachine.machine.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import otus.study.cashmachine.TestUtil;
import otus.study.cashmachine.bank.dao.CardsDao;
import otus.study.cashmachine.bank.data.Card;
import otus.study.cashmachine.bank.service.AccountService;
import otus.study.cashmachine.bank.service.CardService;
import otus.study.cashmachine.bank.service.impl.CardServiceImpl;
import otus.study.cashmachine.machine.data.CashMachine;
import otus.study.cashmachine.machine.data.MoneyBox;
import otus.study.cashmachine.machine.service.impl.CashMachineServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CashMachineServiceTest {

    @Spy
    @InjectMocks
    private CardServiceImpl cardService;

    @Mock
    private CardsDao cardsDao;

    @Mock
    private AccountService accountService;

    @Mock
    private MoneyBoxService moneyBoxService;

    private CashMachineServiceImpl cashMachineService;

    private CashMachine cashMachine = new CashMachine(new MoneyBox());

    @BeforeEach
    void init() {
        cashMachineService = new CashMachineServiceImpl(cardService, accountService, moneyBoxService);
    }


    @Test
    void getMoney() {
        Mockito.doReturn(BigDecimal.ONE).when(cardService).getMoney("1111", "2222", BigDecimal.ONE);
        when(moneyBoxService.getMoney(any(), anyInt())).thenReturn(List.of(1,1,1,1));
        List<Integer> result = cashMachineService.getMoney(new CashMachine(new MoneyBox()), "1111", "2222", BigDecimal.ONE);
        Assertions.assertEquals(List.of(1,1,1,1), result);
    }

    @Test
    void putMoney() {
        String numberCard = "2222";
        String pin = "0000";
        Mockito.doReturn(BigDecimal.TEN).when(cardService).getBalance(numberCard, pin);
        doReturn(BigDecimal.valueOf(6600)).when(cardService).putMoney(numberCard, pin, BigDecimal.valueOf(6600));
        CashMachine machine = new CashMachine(new MoneyBox());
        List<Integer> testArray = new ArrayList<>();
        int i = 0;
        while(i < 4) {
            testArray.add(1);
            i++;
        }
        BigDecimal actual = cashMachineService.putMoney(machine, numberCard, pin, testArray);
        Assertions.assertEquals(BigDecimal.valueOf(6600), actual);
    }

    @Test
    void checkBalance() {
        when(cardsDao.getCardByNumber("1111")).
                thenReturn(new Card(1, "1111", 2L,TestUtil.getHash("0001")));
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        when(accountService.checkBalance(captor.capture())).thenReturn(BigDecimal.ONE);
        CashMachine machine = new CashMachine(new MoneyBox());
        cashMachineService.checkBalance(machine, "1111", "0001");
        Assertions.assertEquals(1, captor.getValue());
    }

    @Test
    void changePin() {
        Card testCard = new Card(1, "2222",2L, TestUtil.getHash("1111"));
        when(cardsDao.getCardByNumber("2222")).thenReturn(testCard) ;
        ArgumentCaptor<Card> captor = ArgumentCaptor.forClass(Card.class);
        when(cardsDao.saveCard(captor.capture())).thenReturn(testCard);
        cashMachineService.changePin("2222", "1111", "3333");
        Assertions.assertEquals(TestUtil.getHash("3333"), captor.getValue().getPinCode());
    }

    @Test
    void changePinWithAnswer() {
        when(cardsDao.getCardByNumber("2222")).thenReturn(new Card(1, "2222",2L, TestUtil.getHash("1111")));
        List<Card> cards = new ArrayList<>();
        when(cardsDao.saveCard(any())).thenAnswer(new Answer<Card>() {
            @Override
            public Card answer(InvocationOnMock invocation) throws Throwable {
                cards.add(invocation.getArgument(0));
                return invocation.getArgument(0);
            }
        });
        cashMachineService.changePin("2222", "1111", "3333");
        Assertions.assertEquals(cards.get(0).getId(), 1);
    }
}