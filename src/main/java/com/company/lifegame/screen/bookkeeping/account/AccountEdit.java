package com.company.lifegame.screen.bookkeeping.account;

import io.jmix.ui.screen.*;
import com.company.lifegame.entity.bookkeeping.Account;

@UiController("lg_Account.edit")
@UiDescriptor("account-edit.xml")
@EditedEntityContainer("accountDc")
public class AccountEdit extends StandardEditor<Account> {
}