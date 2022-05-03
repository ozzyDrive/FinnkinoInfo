package com.finnkinoinfo.finnkinoinfo.data;

import android.content.Context;
import android.content.SharedPreferences;

public class CredentialStorage {
    private final String CREDENTIALS_SHARED_PREFERENCES_NAME = "Credentials";

    private static volatile CredentialStorage instance;

    private final Context context;
    private final SharedPreferences sharedPreferences;

    private CredentialStorage(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(CREDENTIALS_SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static CredentialStorage getInstance(Context context) {
        if (instance == null) {
            // Pass context.getApplicationContext to avoid memory leak as described here:
            // https://stackoverflow.com/questions/37709918/warning-do-not-place-android-context-classes-in-static-fields-this-is-a-memory
            return new CredentialStorage(context.getApplicationContext());
        }
        return instance;
    }

    public boolean credentialsExist(String username) {
        return getCredentials(username) != null;
    }

    public void storeCredentials(String username, String password) {
        if (!credentialsExist(username)) {
            sharedPreferences.edit().putString(username, password).apply();
        }
    }

    public String getCredentials(String username) {
        return sharedPreferences.getString(username, null);
    }
}
