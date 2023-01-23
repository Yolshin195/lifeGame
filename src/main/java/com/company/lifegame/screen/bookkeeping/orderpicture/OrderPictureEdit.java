package com.company.lifegame.screen.bookkeeping.orderpicture;

import com.company.lifegame.entity.bookkeeping.OrderPicture;
import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("lg_OrderPicture.edit")
@UiDescriptor("order-picture-edit.xml")
@EditedEntityContainer("orderPictureDc")
public class OrderPictureEdit extends StandardEditor<OrderPicture> {
}