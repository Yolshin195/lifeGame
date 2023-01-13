package com.company.lifegame.screen.bookkeeping.rate;

import com.company.lifegame.service.bookkeeping.RateService;
import io.jmix.ui.component.Button;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import com.company.lifegame.entity.bookkeeping.Rate;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("lg_Rate.browse")
@UiDescriptor("rate-browse.xml")
@LookupComponent("ratesTable")
public class RateBrowse extends StandardLookup<Rate> {
    @Autowired
    private RateService rateService;
    @Autowired
    private CollectionLoader<Rate> ratesDl;

    @Subscribe("uploadCurrentRateBtn")
    public void onUploadCurrentRateBtnClick(Button.ClickEvent event) {
        rateService.createCurrentRUBVND();
        ratesDl.load();
    }

}