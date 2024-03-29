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

    private void expense(Operation operation, StringBuilder errors) {
        Balance balance = dataManager.create(Balance.class);
        balance.setDate(dateService.now());
        balance.setCurrent(true);
        balance.setAccount(operation.getAccountOne());

        Optional<Balance> currentOptional = getCurrent(operation.getAccountOne());

        if (currentOptional.isPresent()) {
            Balance current = currentOptional.get();
            current.setCurrent(false);

            BigDecimal value = current.getValue().subtract(operation.getValueOne());

            if (value.signum() != -1) {
                balance.setValue(value);
                dataManager.save(current, balance);
            } else {
                errors.append("Не достаточно средств!");
            }
        } else {
            errors.append("Не достаточно средств! У вабранного счёта нету текущего баланса.");
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

            balance.setValue(current.getValue().add(operation.getValueTwo()));

            dataManager.save(current, balance);
        } else {
            balance.setValue(BigDecimal.ZERO.add(operation.getValueTwo()));

            dataManager.save(balance);
        }
    }

    private void transfer(Operation operation, StringBuilder errors) {
        expense(operation, errors);
        income(operation);
    }

    public void perform(Operation operation, StringBuilder errors) {
        switch (operation.getType()) {
            case EXPENSE -> expense(operation, errors);
            case INCOME -> income(operation);
            case TRANSFER -> transfer(operation, errors);
        }
    }

    public Optional<Balance> getCurrent(Account account) {
        return dataManager.load(Balance.class)
                .query("e.account = :account and e.current = true and e.deletedDate is null")
                .parameter("account", account)
                .optional();
    }
}