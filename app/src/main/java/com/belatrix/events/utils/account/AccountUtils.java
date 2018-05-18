package com.belatrix.events.utils.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Build;
import android.os.Bundle;

import javax.inject.Named;

public class AccountUtils {

    private final static String ACCOUNT_USER_ID = "acccount_user_id";
    private final static String ACCOUNT_FULL_NAME = "acccount_first_name";
    private final static String ACCOUNT_IS_STAFF = "acccount_is_staff";
    private final static String ACCOUNT_IS_ACTIVE = "acccount_is_active";
    private final static String ACCOUNT_IS_PARTICIPANT = "acccount_is_participant";
    private final static String ACCOUNT_IS_MODERATOR = "account_is_moderator";
    private final static String ACCOUNT_IS_JURY = "account_is_jury";

    private final String mAccountType;

    private AccountManager mAccountManager;
    private Account mAccount;

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

    public int getUserId() {
        if (mAccount == null) {
            return -1;
        }
        return Integer.parseInt(mAccountManager.getUserData(mAccount, ACCOUNT_USER_ID));
    }

    public String getFullName() {
        if (mAccount == null) {
            return "";
        }
        return mAccountManager.getUserData(mAccount, ACCOUNT_FULL_NAME);
    }

    public String getEmail() {
        if (mAccount == null) {
            return "";
        }
        return mAccount.name;
    }

    public String getToken() {
        if (mAccount == null) {
            return "";
        }
        return mAccountManager.peekAuthToken(mAccount, mAccountType);
    }

    public boolean isStaff() {
        return mAccount != null && Boolean.parseBoolean(mAccountManager.getUserData(mAccount, ACCOUNT_IS_STAFF));
    }

    public boolean isActive() {
        return mAccount != null && Boolean.parseBoolean(mAccountManager.getUserData(mAccount, ACCOUNT_IS_ACTIVE));
    }

    public boolean isParticipant() {
        return mAccount != null && Boolean.parseBoolean(mAccountManager.getUserData(mAccount, ACCOUNT_IS_PARTICIPANT));
    }

    public boolean isModerator() {
        return mAccount != null && Boolean.parseBoolean(mAccountManager.getUserData(mAccount, ACCOUNT_IS_MODERATOR));
    }

    public boolean isJury() {
        return mAccount != null && Boolean.parseBoolean(mAccountManager.getUserData(mAccount, ACCOUNT_IS_JURY));
    }


    public void createAccount(String token, int userId, String email, String fullName, String password, boolean isModerator, boolean isStaff, boolean isActive, boolean isParticipant, boolean isJury) {
        Bundle args = new Bundle();

        args.putString(ACCOUNT_USER_ID, Integer.toString(userId));
        args.putString(ACCOUNT_FULL_NAME, fullName);
        args.putString(ACCOUNT_IS_MODERATOR, Boolean.toString(isModerator));
        args.putString(ACCOUNT_IS_STAFF, Boolean.toString(isStaff));
        args.putString(ACCOUNT_IS_ACTIVE, Boolean.toString(isActive));
        args.putString(ACCOUNT_IS_PARTICIPANT, Boolean.toString(isParticipant));
        args.putString(ACCOUNT_IS_JURY, Boolean.toString(isJury));
        Account account = new Account(email, mAccountType);
        mAccountManager.addAccountExplicitly(account, password, args);
        mAccountManager.setAuthToken(account, mAccountType, token);
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
