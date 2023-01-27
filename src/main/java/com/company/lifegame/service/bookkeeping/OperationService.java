package com.company.lifegame.service.bookkeeping;

import com.company.lifegame.entity.bookkeeping.Operation;
import com.company.lifegame.entity.bookkeeping.OperationType;
import com.company.lifegame.entity.bookkeeping.Order;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("lg_OperationService")
public class OperationService {

    @Autowired
    private BalanceService balanceService;

    @Autowired
    private DataManager dataManager;

    public boolean validate(Order order) {
        return order.getDate() != null
                && order.getAccount() != null
                && order.getCurrency() != null
                && order.getCategory() != null
                && order.getValue() != null
                && order.getValue().compareTo(BigDecimal.ZERO) != 0;
    }

    public Operation create(Order order) {
        Operation operation = dataManager.save(build(order));

        balanceService.perform(operation);

        return operation;
    }

    public Operation build(Order order) {
        Operation operation = dataManager.create(Operation.class);
        operation.setType(OperationType.EXPENSE);
        operation.setDate(order.getDate());
        operation.setAccountOne(order.getAccount());
        operation.setValueOne(order.getValue());
        operation.setCategory(order.getCategory());
        return operation;
    }
}