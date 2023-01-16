package com.company.lifegame.screen.bookkeeping.account;

import io.jmix.ui.screen.*;
import com.company.lifegame.entity.bookkeeping.Account;

@UiController("lg_Account.browse")
@UiDescriptor("account-browse.xml")
@LookupComponent("accountsTable")
public class AccountBrowse extends StandardLookup<Account> {
}