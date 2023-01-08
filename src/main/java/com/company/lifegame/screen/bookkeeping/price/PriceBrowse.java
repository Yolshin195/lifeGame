package com.company.lifegame.screen.bookkeeping.price;

import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import com.company.lifegame.entity.bookkeeping.Price;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@UiController("lg_Price.browse")
@UiDescriptor("price-browse.xml")
@LookupComponent("pricesTable")
public class PriceBrowse extends StandardLookup<Price> {
    @Autowired
    private CollectionLoader<Price> pricesDl;

    private ScreenOptions options;

    @Subscribe
    public void onInit(InitEvent event) {
        options = event.getOptions();

        if (event.getOptions() instanceof MapScreenOptions mapScreenOptions) {
            Map<String, Object> objectMap = mapScreenOptions.getParams();

            if (objectMap.containsKey("provider")) {
                pricesDl.setParameter("provider", objectMap.get("provider"));
            }

            if (objectMap.containsKey("providerItem")) {
                pricesDl.setParameter("providerItem", objectMap.get("providerItem"));
            }
        }
    }

    @Install(to = "pricesTable.create", subject = "screenOptionsSupplier")
    private ScreenOptions pricesTableCreateScreenOptionsSupplier() {
        return options;
    }

}