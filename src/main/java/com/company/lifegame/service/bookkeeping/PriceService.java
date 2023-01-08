package com.company.lifegame.service.bookkeeping;

import com.company.lifegame.entity.bookkeeping.Price;
import com.company.lifegame.entity.bookkeeping.ProviderItem;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("lg_PriceService")
public class PriceService {
    @Autowired
    private DataManager dataManager;

    public Optional<Price> findPrice(ProviderItem providerItem) {
        return dataManager.load(Price.class)
                .query("e.deletedDate is null and e.current = true and e.providerItem = :providerItem")
                .parameter("providerItem", providerItem)
                .optional();
    }
}