package com.company.lifegame.screen.bookkeeping.price;

import com.company.lifegame.entity.bookkeeping.*;
import io.jmix.core.common.util.ParamsMap;
import io.jmix.ui.component.EntityPicker;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@UiController("lg_Price.edit")
@UiDescriptor("price-edit.xml")
@EditedEntityContainer("priceDc")
public class PriceEdit extends StandardEditor<Price> {
    @Autowired
    private EntityPicker<Provider> providerField;

    @Autowired
    private EntityPicker<ProviderItem> providerItemField;

    @Autowired
    private EntityPicker<Currency> currencyField;

    private ScreenOptions options;

    @Subscribe
    public void onInit(InitEvent event) {
        options = event.getOptions();
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        if (options instanceof MapScreenOptions mapScreenOptions) {
            Map<String, Object> objectMap = mapScreenOptions.getParams();

            if (objectMap.containsKey("provider") && objectMap.get("provider") instanceof Provider provider) {
                providerField.setValue(provider);
            }

            if (objectMap.containsKey("providerItem") && objectMap.get("providerItem") instanceof ProviderItem providerItem) {
                providerItemField.setValue(providerItem);
            }
        }
    }

    @Subscribe("providerField")
    public void onProviderFieldValueChange(HasValue.ValueChangeEvent<Provider> event) {
        if (event.getValue() == null) return;

        currencyField.setValue(event.getValue().getCurrency());
    }

    @Install(to = "providerItemField.entityLookup", subject = "screenOptionsSupplier")
    private ScreenOptions providerItemFieldEntityLookupScreenOptionsSupplier() {
        if (providerField.getValue() != null) {
            return new MapScreenOptions(ParamsMap.of("provider", providerField.getValue()));
        }

        return new MapScreenOptions(ParamsMap.empty());
    }
}