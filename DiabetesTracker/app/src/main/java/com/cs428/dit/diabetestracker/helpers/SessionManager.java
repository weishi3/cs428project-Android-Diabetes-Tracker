package com.cs428.dit.diabetestracker.helpers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.cs428.dit.diabetestracker.LoginActivity;

import java.util.HashMap;

/**
 * Created by QiZhang on 2/14/16.
 * Code cited: http://www.androidhive.info/2012/08/android-session-management-using-shared-preferences
 */
public class SessionManager {
    //Shared preferences
    private final SharedPreferences pref;

    //Editor for shared preferences
    private final Editor editor;

    //Application context
    private final Context _context;

    //sharedpref file name
    private static final String PREF_NAME = "DITPref";

    //Sharedpreferences email key
    public static final String KEY_EMAIL = "email";

    private static final String IS_LOGIN = "IsLoggedIn";

    public SessionManager(Context _context) {
        this._context = _context;
        pref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    /**
     * Create a login session. Store user email. Remember login status.
     *
     * @param email user email
     */
    public void createLoginSession(String email) {
        editor.putString(KEY_EMAIL, email);
        editor.putBoolean(IS_LOGIN, true);
        //commit changes to sharedpreferences
        editor.commit();
    }

    /**
     * Get session data of the user
     *
     * @return user, a hash map containing user data.
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        //user email
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        return user;
    }

    /**
     * Quick check for login
     *
     * @return True if logged in, otherwise, false.
     */
    public Boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false); //by default, not logged in.
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent login = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Starting Login Activity
        _context.startActivity(login);
    }

    public void eraseSharedPreference() {
        editor.clear();
        editor.commit();
    }
}
