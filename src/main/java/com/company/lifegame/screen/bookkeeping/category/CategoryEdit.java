package com.company.lifegame.screen.bookkeeping.category;

import io.jmix.ui.screen.*;
import com.company.lifegame.entity.bookkeeping.Category;

@UiController("lg_Category.edit")
@UiDescriptor("category-edit.xml")
@EditedEntityContainer("categoryDc")
public class CategoryEdit extends StandardEditor<Category> {
}