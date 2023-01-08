package com.company.lifegame.screen.bookkeeping.order;

import io.jmix.ui.screen.*;
import com.company.lifegame.entity.bookkeeping.Order;

@UiController("lg_Order.browse")
@UiDescriptor("order-browse.xml")
@LookupComponent("ordersTable")
public class OrderBrowse extends StandardLookup<Order> {
}