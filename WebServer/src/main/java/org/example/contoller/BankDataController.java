package org.example.contoller;

import lombok.RequiredArgsConstructor;
import org.example.dao.AccountDao;
import org.example.dao.CardsDao;
import org.example.data.Card;
import org.example.service.AccountService;
import org.example.service.CardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@Controller
@RequiredArgsConstructor
@RequestMapping("/index")
public class BankDataController {

    private final AccountService accountService;

    private final CardsDao cardsDao;

    private final AccountDao accountDao;

    private Card card;

    @GetMapping("/index")
    public String checkBalance(Model model) {
        BigDecimal money = accountService.getAccount(card.getId()).getAmount();
        model.addAttribute("takeMoney", money);
        return "/index";
    }

    @GetMapping(params = "amount")
    public String deposit(Model model, @RequestParam(name = "amount") BigDecimal amount) {
        BigDecimal putMoney = accountService.putMoney(card.getId(), amount);
        model.addAttribute("takeMoney", putMoney);
        return "index";
    }

    @GetMapping(params = "takeOff")
    public String takeMoney(Model model, @RequestParam(name = "takeOff") BigDecimal depositMoney) {
        BigDecimal takeMoney = accountService.getMoney(card.getId(), depositMoney);
        model.addAttribute("takeMoney", takeMoney);
        return "index";
    }

    @GetMapping(params = "changePin")
    public String changePin(Model model, @RequestParam(name = "changePin") String changePin) {
        cardsDao.getCardByNumber(card.getNumber()).setPinCode(changePin);
        model.addAttribute("pinCode", changePin);
        return "index";
    }

    @PostMapping
    public String enteringCardData(Model model, @RequestParam(name = "number") String number) {
        card = cardsDao.getCardByNumber(number);
        model.addAttribute("number", number);
        model.addAttribute("pinCode", card.getPinCode());
        return "index";
    }

}
