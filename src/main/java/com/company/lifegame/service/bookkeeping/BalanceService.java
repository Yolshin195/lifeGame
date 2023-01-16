package com.company.lifegame.service.bookkeeping;

import com.company.lifegame.entity.bookkeeping.Account;
import com.company.lifegame.entity.bookkeeping.Balance;
import com.company.lifegame.entity.bookkeeping.Operation;
import com.company.lifegame.service.DateService;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component("lg_BalanceService")
public class BalanceService {
    @Autowired
    private DataManager dataManager;
    @Autowired
    private DateService dateService;

    private void expense(Operation operation) {
        Balance balance = dataManager.create(Balance.class);
        balance.setDate(dateService.now());
        balance.setCurrent(true);
        balance.setAccount(operation.getAccountOne());

        Optional<Balance> currentOptional = getCurrent(operation.getAccountOne());
        if (currentOptional.isPresent()) {
            Balance current = currentOptional.get();
            current.setCurrent(false);

            balance.setValue(current.getValue().subtract(operation.getValueOne()));

            dataManager.save(current, balance);
        } else {
            balance.setValue(BigDecimal.ZERO.subtract(operation.getValueOne()));

            dataManager.save(balance);
        }
    }

    private void income(Operation operation) {
        Balance balance = dataManager.create(Balance.class);
        balance.setDate(dateService.now());
        balance.setCurrent(true);
        balance.setAccount(operation.getAccountTwo());

        Optional<Balance> currentOptional = getCurrent(operation.getAccountTwo());
        if (currentOptional.isPresent()) {
            Balance current = currentOptional.get();
            current.setCurrent(false);

            balance.setValue(current.getValue().add(operation.getValueOne()));

            dataManager.save(current, balance);
        } else {
            balance.setValue(BigDecimal.ZERO.add(operation.getValueOne()));

            dataManager.save(balance);
        }
    }

    private void transfer(Operation operation) {
        expense(operation);
        income(operation);
    }

    public void perform(Operation operation) {
        switch (operation.getType()) {
            case EXPENSE -> expense(operation);
            case INCOME -> income(operation);
            case TRANSFER -> transfer(operation);
        }
    }

    public Optional<Balance> getCurrent(Account account) {
        return dataManager.load(Balance.class)
                .query("e.account = :account and e.current = true and e.deletedDate is null")
                .parameter("account", account)
                .optional();
    }
}