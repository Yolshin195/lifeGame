package com.company.lifegame.screen.bookkeeping.provider;

import com.google.common.base.Strings;
import io.jmix.ui.Dialogs;
import io.jmix.ui.component.*;
import io.jmix.ui.model.InstanceLoader;
import io.jmix.ui.screen.*;
import com.company.lifegame.entity.bookkeeping.Provider;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("lg_Provider.edit")
@UiDescriptor("provider-edit.xml")
@EditedEntityContainer("providerDc")
public class ProviderEdit extends StandardEditor<Provider> {

    @Autowired
    private HtmlBoxLayout mapHtmlBox;

    @Autowired
    private TextField<String> mapField;
    @Autowired
    private InstanceLoader<Provider> providerDl;
    @Autowired
    private Dialogs dialogs;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        providerDl.load();

        if (mapField.getValue() == null) {
            mapHtmlBox.setTemplateContents("<p>Заполните поле 'map' из здесь будет расположение обьекта</p>");
        } else {
            mapHtmlBox.setTemplateContents(mapField.getValue());
        }
    }

    @Install(to = "mapField", subject = "contextHelpIconClickHandler")
    private void mapFieldContextHelpIconClickHandler(HasContextHelp.ContextHelpIconClickEvent contextHelpIconClickEvent) {
        dialogs.createMessageDialog()
                .withCaption("Как перкрепить карту")
                .withMessage(Strings.nullToEmpty(contextHelpIconClickEvent.getSource().getContextHelpText()))
                .withContentMode(ContentMode.HTML)
                .show();
    }
    
    
}