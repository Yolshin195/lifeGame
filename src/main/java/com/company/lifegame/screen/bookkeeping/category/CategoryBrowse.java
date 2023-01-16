package com.company.lifegame.screen.bookkeeping.category;

import io.jmix.ui.screen.*;
import com.company.lifegame.entity.bookkeeping.Category;

@UiController("lg_Category.browse")
@UiDescriptor("category-browse.xml")
@LookupComponent("categoriesTable")
public class CategoryBrowse extends StandardLookup<Category> {
}