package com.belatrix.events.utils.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Build;
import android.os.Bundle;

import javax.inject.Named;

public class AccountUtils {

    private final static String ACCOUNT_USER_ID = "acccount_user_id";
    private final static String ACCOUNT_FULL_NAME = "acccount_full_name";
    private final static String ACCOUNT_PHONE_NUMBER = "account_phone_number";
    private final static String ACCOUNT_ROLE_ID = "account_role_id";
    private final static String ACCOUNT_ROLE_NAME = "account_role_name";
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

    public String getToken() {
        if (mAccount == null) {
            return "";
        }
        return mAccountManager.peekAuthToken(mAccount, mAccountType);
    }

    public int getUserId() {
        if (mAccount == null) {
            return -1;
        }
        return Integer.parseInt(mAccountManager.getUserData(mAccount, ACCOUNT_USER_ID));
    }

    public String getEmail() {
        if (mAccount == null) {
            return "";
        }
        return mAccount.name;
    }

    public String getFullName() {
        if (mAccount == null) {
            return "";
        }
        return mAccountManager.getUserData(mAccount, ACCOUNT_FULL_NAME);
    }

    public void setFullName(String fullName) {
        mAccountManager.setUserData(mAccount, ACCOUNT_FULL_NAME, fullName);
    }

    public String getPhoneNumber() {
        if (mAccount == null) {
            return "";
        }
        return mAccountManager.getUserData(mAccount, ACCOUNT_PHONE_NUMBER);
    }

    public void setPhoneNumber(String phoneNumber) {
        mAccountManager.setUserData(mAccount, ACCOUNT_PHONE_NUMBER, phoneNumber);
    }

    public String getRoleId() {
        if (mAccount == null) {
            return "";
        }
        return mAccountManager.getUserData(mAccount, ACCOUNT_ROLE_ID);
    }

    public void setRoleId(String roleId) {
        mAccountManager.setUserData(mAccount, ACCOUNT_ROLE_ID, roleId);
    }

    public String getRoleName() {
        if (mAccount == null) {
            return "";
        }
        return mAccountManager.getUserData(mAccount, ACCOUNT_ROLE_NAME);
    }

    public void setRoleName(String roleName) {
        mAccountManager.setUserData(mAccount, ACCOUNT_ROLE_NAME, roleName);
    }

    public boolean isModerator() {
        return mAccount != null && Boolean.parseBoolean(mAccountManager.getUserData(mAccount, ACCOUNT_IS_MODERATOR));
    }


    public void setIsModerator(boolean isModerator) {
        mAccountManager.setUserData(mAccount, ACCOUNT_IS_MODERATOR, Boolean.toString(isModerator));
    }

    public boolean isStaff() {
        return mAccount != null && Boolean.parseBoolean(mAccountManager.getUserData(mAccount, ACCOUNT_IS_STAFF));
    }

    public void setIsStaff(boolean isStaff) {
        mAccountManager.setUserData(mAccount, ACCOUNT_IS_STAFF, Boolean.toString(isStaff));
    }

    public boolean isActive() {
        return mAccount != null && Boolean.parseBoolean(mAccountManager.getUserData(mAccount, ACCOUNT_IS_ACTIVE));
    }

    public void setIsActive(boolean isActive) {
        mAccountManager.setUserData(mAccount, ACCOUNT_IS_ACTIVE, Boolean.toString(isActive));
    }

    public boolean isParticipant() {
        return mAccount != null && Boolean.parseBoolean(mAccountManager.getUserData(mAccount, ACCOUNT_IS_PARTICIPANT));
    }

    public void setIsParticipant(boolean isParticipant) {
        mAccountManager.setUserData(mAccount, ACCOUNT_IS_PARTICIPANT, Boolean.toString(isParticipant));
    }

    public boolean isJury() {
        return mAccount != null && Boolean.parseBoolean(mAccountManager.getUserData(mAccount, ACCOUNT_IS_JURY));
    }

    public void setIsJury(boolean isJury) {
        mAccountManager.setUserData(mAccount, ACCOUNT_IS_JURY, Boolean.toString(isJury));
    }


    public void createAccount(String token, int userId, String email, String fullName, String phoneNumber, String password, String roleId, String roleName, boolean isModerator, boolean isStaff, boolean isActive, boolean isParticipant, boolean isJury) {
        Bundle args = new Bundle();

        args.putString(ACCOUNT_USER_ID, Integer.toString(userId));
        args.putString(ACCOUNT_FULL_NAME, fullName);
        args.putString(ACCOUNT_PHONE_NUMBER, phoneNumber);
        args.putString(ACCOUNT_ROLE_ID, roleId);
        args.putString(ACCOUNT_ROLE_NAME, roleName);
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
