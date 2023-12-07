package org.example.db;



import org.example.data.Account;
import org.example.data.Card;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashMap;
import java.util.Map;

@Repository
public class Cards {
    public static Map<String, Card> cards = new HashMap<>();

    static {
        Account a1 = Accounts.accounts.get(1L);
        Account a2 = Accounts.accounts.get(2L);
        Account a3 = Accounts.accounts.get(3L);

        cards.put("1111", new Card(1, "1111", 1L, "39dfa55283318d31afe5a3ff4a0e3253e2045e43"));
        cards.put("2222", new Card(2, "2222", 2L, "39dfa55283318d31afe5a3ff4a0e3253e2045e43"));
        cards.put("3333", new Card(3, "3333", 3L, "39dfa55283318d31afe5a3ff4a0e3253e2045e43"));
    }

    static Long idSequence = 10L;

    public static Long getNextId() {
        idSequence ++;
        return idSequence;
    }

}