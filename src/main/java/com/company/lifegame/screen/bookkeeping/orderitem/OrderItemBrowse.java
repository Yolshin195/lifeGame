package com.company.lifegame.screen.bookkeeping.orderitem;

import io.jmix.ui.screen.*;
import com.company.lifegame.entity.bookkeeping.OrderItem;

@UiController("lg_OrderItem.browse")
@UiDescriptor("order-item-browse.xml")
@LookupComponent("orderItemsTable")
public class OrderItemBrowse extends StandardLookup<OrderItem> {
}