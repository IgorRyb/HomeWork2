package org.example.service;




import org.example.data.Card;

import java.math.BigDecimal;


public interface CardService {
    Card createCard(final String number, final Long accountId, final String pinCode);

    boolean cnangePin(String number, String oldPin, String newPin);

    BigDecimal getMoney(String number, String pin, BigDecimal sum);

    BigDecimal putMoney(String number, String pin, BigDecimal sum);

    BigDecimal getBalance(String number, String pin);
}