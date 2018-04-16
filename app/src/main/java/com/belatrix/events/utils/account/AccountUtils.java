package com.belatrix.events.utils.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Build;
import android.os.Bundle;

import javax.inject.Inject;
import javax.inject.Named;

public class AccountUtils {

    private final static String ACCOUNT_NAME = "acccount_name";

    private final String mAccountType;

    private AccountManager mAccountManager;
    private Account mAccount;

    @Inject
    public AccountUtils(AccountManager accountManager, @Named("account_type") String accountType) {
        this.mAccountManager = accountManager;
        this.mAccountType = accountType;
    }

    private Account getAccount() {
        if (mAccount == null) {
            Account[] accounts = mAccountManager.getAccountsByType(mAccountType);
            if (accounts.length > 0) {
                mAccount = accounts[0];
                return mAccount;
            }
            return null;
        } else {
            return mAccount;
        }
    }

    public boolean existsAccount() {
        return getAccount() != null;
    }

    public String getName() {
        if (mAccount == null) {
            return "";
        }
        return mAccountManager.getUserData(mAccount, ACCOUNT_NAME);
    }

    public String getEmail() {
        if (mAccount == null) {
            return "";
        }
        return mAccount.name;
    }

    public void createAccount(String name, String email, String password) {
        Bundle args = new Bundle();
        args.putString(ACCOUNT_NAME, name);
        Account account = new Account(email, mAccountType);
        mAccountManager.addAccountExplicitly(account, password, args);
    }

    public void signOut() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            mAccountManager.removeAccountExplicitly(mAccount);
        } else {
            mAccountManager.removeAccount(mAccount, null, null);
        }
        mAccount = null;
    }
}
