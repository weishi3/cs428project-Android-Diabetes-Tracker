package com.cs428.dit.diabetestracker.helpers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.cs428.dit.diabetestracker.LoginActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by QiZhang on 2/14/16.
 * Code cited: http://www.androidhive.info/2012/08/android-session-management-using-shared-preferences
 */
public class SessionManager {
    //Sharedpreferences email key
    public static final String KEY_EMAIL = "email";
    //sharedpref file name
    private static final String PREF_NAME = "DITPref";
    private static final String IS_LOGIN = "IsLoggedIn";
    //Shared preferences
    private final SharedPreferences pref;
    //Editor for shared preferences
    private final Editor editor;
    //Application context
    private final Context _context;

    public SessionManager(Context _context) {
        this._context = _context;
        pref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    /**
     * Create a login session. Store user email. Remember login status.
     *
     * @param email user email
     * @param userDetails map containing the user's information
     */
    public void createLoginSession(String email, Map<String, Object> userDetails) {
        editor.putString(KEY_EMAIL, email);
        editor.putBoolean(IS_LOGIN, true);
        if (!userDetails.isEmpty()) {
            editor.putInt("age", (int)(long)userDetails.get("age"));
            editor.putFloat("waistline", (float)(double)userDetails.get("waistline"));
            editor.putFloat("BMI", (float) (double)userDetails.get("BMI"));
            editor.putInt("bloodPressure", (int) (long)userDetails.get("bloodPressure"));
            editor.putBoolean("familyHistory", (boolean) userDetails.get("familyHistory"));
            editor.putBoolean("gender", (boolean)userDetails.get("gender"));
        }
        //commit changes to sharedpreferences
        editor.commit();
    }

    /**
     * Update the user's information.
     *
     * @param userDetails map containing the user's information
     */
    public void updateUserDetails(Map<String, Object> userDetails){
        editor.putInt("age", (int) userDetails.get("age"));
        editor.putFloat("waistline", (float) (double) userDetails.get("waistline"));
        editor.putFloat("BMI", (float) (double)userDetails.get("BMI"));
        editor.putInt("bloodPressure", (int)userDetails.get("bloodPressure"));
        editor.putBoolean("familyHistory", (boolean) userDetails.get("familyHistory"));
        editor.putBoolean("gender", (boolean)userDetails.get("gender"));
        editor.commit();
    }

    /**
     * Get session data of the user
     *
     * @return user, a hash map containing user data.
     */
    public HashMap<String, Object> getUserDetails() {
        HashMap<String, Object> user = new HashMap<>();
        //user email
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put("age", pref.getInt("age", 0));
        user.put("waistline", pref.getFloat("waistline", 0));
        user.put("bloodPressure", pref.getInt("bloodPressure", 0));
        user.put("BMI", pref.getFloat("BMI", (float)0));
        user.put("familyHistory", pref.getBoolean("familyHistory", false));
        user.put("gender", pref.getBoolean("gender", false));
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
     * Set email in SharedPreferences
     */
    public void setEmailInSession(String email) {
        editor.putString(KEY_EMAIL, email);
        editor.commit();
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
