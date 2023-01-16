package com.company.lifegame.service.bookkeeping;

import com.company.lifegame.entity.bookkeeping.Account;
import com.company.lifegame.entity.bookkeeping.Currency;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("lg_AccountService")
public class AccountService {
    @Autowired
    private DataManager dataManager;

    public Optional<Account> getDefaultValue(Currency currency) {
        return dataManager.load(Account.class)
                .query("e.currency = :currency and e.defaultValue = true and e.deletedDate is null")
                .parameter("currency", currency)
                .optional();
    }
}