package com.company.lifegame.screen.bookkeeping.provideritem;

import com.company.lifegame.entity.bookkeeping.Provider;
import com.company.lifegame.entity.bookkeeping.ProviderItem;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@UiController("lg_ProviderItem.browse")
@UiDescriptor("provider-item-browse.xml")
@LookupComponent("providerItemsTable")
public class ProviderItemBrowse extends StandardLookup<ProviderItem> {
    @Autowired
    private CollectionLoader<ProviderItem> providerItemsDl;

    private Provider provider;


    @Subscribe
    public void onInit(InitEvent event) {
        if (event.getOptions() instanceof MapScreenOptions mapScreenOptions) {
            Map<String, Object> objectMap = mapScreenOptions.getParams();

            if (objectMap.containsKey("provider") && objectMap.get("provider") instanceof Provider pr) {
                providerItemsDl.setParameter("provider", pr);
                provider = pr;
            }
        }
    }

    @Install(to = "providerItemsTable.create", subject = "initializer")
    private void providerItemsTableCreateInitializer(ProviderItem providerItem) {
        if (provider != null) {
            providerItem.setProvider(provider);
        }
    }


}