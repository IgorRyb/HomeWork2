package org.example.contoller;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.example.dao.CardsDao;
import org.example.data.Account;
import org.example.data.Card;
import org.example.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;


@Controller
@RequiredArgsConstructor
@RequestMapping("/index")
public class BankDataController {

    private final AccountService accountService;

    private final CardsDao cardsDao;

    @Autowired
    private Card card;

    @GetMapping("/index")
    public String checkBalance(Model model) {
        BigDecimal money = accountService.getAccount(card.getId()).getAmount();
        model.addAttribute("takeMoney", money);
        return "/index";
    }

    @GetMapping
    public String deposit(Model model, @RequestParam(name = "amount", required = false) BigDecimal amount) {
        BigDecimal putMoney = accountService.putMoney(card.getId(), amount);
        model.addAttribute("takeMoney", putMoney);
        return "index";
    }

    @GetMapping(params = "takeOff")
    public String takeMoney(Model model, @RequestParam(name = "takeOff", required = false) BigDecimal depositMoney) {
        BigDecimal takeMoney = accountService.getMoney(card.getId(), depositMoney);
        model.addAttribute("takeMoney", takeMoney);
        return "index";
    }

    @GetMapping(params = "changePin")
    public String changePin(Model model, @RequestParam(name = "changePin", required = false) String changePin) {
        card.setPinCode(changePin);
        model.addAttribute("pinCode", changePin);
        model.addAttribute("number", card.getNumber());
        return "index";
    }

    @PostMapping
    public String enteringCardData(Model model, @RequestParam(name = "number") String number
                                       , @RequestParam(name = "pinCode") String pinCode) {
        if (cardsDao.getCardByNumber(number) == null) {
            Account account = accountService.createAccount(BigDecimal.ZERO);
            card = cardsDao.createCard(number, account.getId(), pinCode);
            model.addAttribute("number", number);
            model.addAttribute("pinCode", pinCode);
        } else {
            card = cardsDao.getCardByNumber(number);
            model.addAttribute("number", card.getNumber());
            model.addAttribute("pinCode", card.getPinCode());
        }
        return "index";
    }

}
