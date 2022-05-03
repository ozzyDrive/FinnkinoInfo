package com.finnkinoinfo.finnkinoinfo.data;

import android.content.Context;

import com.finnkinoinfo.finnkinoinfo.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private final CredentialStorage credentialStorage;

    public LoginDataSource(Context context) {
        credentialStorage = CredentialStorage.getInstance(context);
    }

    public Result<LoggedInUser> login(String username, String password) {

        try {
            if (!credentialStorage.credentialsExist(username) || credentialStorage.getCredentials(username).equals(password)) {
                LoggedInUser user =
                        new LoggedInUser(
                                java.util.UUID.randomUUID().toString(),
                                username);
                return new Result.Success<>(user);
            }
            return new Result.Error(new WrongPasswordException());
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}