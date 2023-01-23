package com.company.lifegame.screen.bookkeeping.orderpicture;

import com.company.lifegame.entity.bookkeeping.OrderPicture;
import io.jmix.ui.screen.LookupComponent;
import io.jmix.ui.screen.StandardLookup;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("lg_OrderPicture.browse")
@UiDescriptor("order-picture-browse.xml")
@LookupComponent("orderPicturesTable")
public class OrderPictureBrowse extends StandardLookup<OrderPicture> {
}