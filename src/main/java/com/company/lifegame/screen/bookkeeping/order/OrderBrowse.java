package com.company.lifegame.screen.bookkeeping.order;

import com.company.lifegame.entity.bookkeeping.Order;
import io.jmix.ui.component.PropertyFilter;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@UiController("lg_Order.browse")
@UiDescriptor("order-browse.xml")
@LookupComponent("ordersTable")
public class OrderBrowse extends StandardLookup<Order> {

    @Autowired
    private PropertyFilter<Integer> yearPropertyFilter;
    @Autowired
    private PropertyFilter<Integer> monthPropertyFilter;

    @Subscribe
    public void onInit(InitEvent event) {
        LocalDate current = LocalDate.now();
        yearPropertyFilter.setValue(current.getYear());
        monthPropertyFilter.setValue(current.getMonthValue());
    }


}