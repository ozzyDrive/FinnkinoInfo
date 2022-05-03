package com.finnkinoinfo.finnkinoinfo.data;

import android.content.Context;

import com.finnkinoinfo.finnkinoinfo.data.model.LoggedInUser;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {
    private static volatile LoginRepository instance;

    private final Context context;
    private LoginDataSource dataSource;
    private CredentialStorage credentialStorage;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource, Context context) {
        this.dataSource = dataSource;
        this.context = context;
        this.credentialStorage = CredentialStorage.getInstance(context);
    }

    public static LoginRepository getInstance(LoginDataSource dataSource, Context context) {
        if (instance == null) {
            // Pass context.getApplicationContext to avoid memory leak as described here:
            // https://stackoverflow.com/questions/37709918/warning-do-not-place-android-context-classes-in-static-fields-this-is-a-memory
            instance = new LoginRepository(dataSource, context.getApplicationContext());
        }
        return instance;
    }



    public boolean isLoggedIn() {
        return user != null;
    }

    public LoggedInUser getUser() {
        return user;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
    }

    public Result<LoggedInUser> login(String username, String password) {
        // handle login
        Result<LoggedInUser> result = dataSource.login(username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
            credentialStorage.storeCredentials(username, password);
        }
        return result;
    }
}